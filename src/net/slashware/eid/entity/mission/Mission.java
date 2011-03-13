package net.slashware.eid.entity.mission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.sun.net.httpserver.Filter.Chain;

import net.slashie.serf.game.SworeGame;
import net.slashie.serf.ui.UserInterface;
import net.slashie.util.Pair;
import net.slashie.utils.Util;
import net.slashware.eid.EIDGame;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.controller.level.LocationManager;
import net.slashware.eid.controller.mission.MissionGenerator;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.mission.CityLocation.CityLocationType;
import net.slashware.eid.entity.player.DetectiveActor;

public class Mission {
	private class LocationChainpiece {
		private Location previousLocation;
		private Location location;
		private List<Location> falseNextLocations;
		private LocationChainpiece nextLocation;
		private List<String> clues = new ArrayList<String>();
		public void addClue(String clue){
			clues.add(clue);
		}
		public List<String> getClues() {
			return clues;
		}
		private LocationChainpiece(Location previousLocation, Location location) {
			super();
			this.location = location;
			this.previousLocation = previousLocation;
		}
		
		public Location getPreviousLocation() {
			return previousLocation;
		}

		public Location getLocation() {
			return location;
		}
		public List<Location> getFalseNextLocations() {
			return falseNextLocations;
		}
		public LocationChainpiece getNextLocation() {
			return nextLocation;
		}
		public void setNextLocation(LocationChainpiece nextLocation) {
			this.nextLocation = nextLocation;
		}
		public void setFalseNextLocations(List<Location> falseNextLocations) {
			this.falseNextLocations = falseNextLocations;
		}
	}
	
	private Crime crime;
	private Date deadline;
	private Date missionStart;
	private LocationChainpiece firstChainPiece;
	private Stack<LocationChainpiece> chainPieceStack = new Stack<LocationChainpiece>();
	private DetectiveActor detective;
	private int difficulty;
	
	public Mission(Crime crime, Date missionStart, Date deadline, int difficulty, DetectiveActor detective) {
		super();
		this.crime = crime;
		this.deadline = deadline;
		this.missionStart = missionStart;
		this.detective = detective;
		generateLocationPath(difficulty);
		this.difficulty = difficulty;
		chainPieceStack.push(firstChainPiece);
	}
	
	
	private void generateLocationPath(int difficulty) {
		int baseFalseNextLocations = difficulty + 2;
		int locationsSteps = (int)Math.round((double)(difficulty + 1) * 1.5d);
		locationsSteps = locationsSteps + Util.rand(-1, 1);
		
		LocationChainpiece currentChainPiece = null;
		firstChainPiece = new LocationChainpiece(null, Location.getHQLocation());
		if (Location.getHQLocation() != crime.getLocation()){
			firstChainPiece.setNextLocation(new LocationChainpiece(Location.getHQLocation(), crime.getLocation()));
			firstChainPiece.setFalseNextLocations(new ArrayList<Location>());
			currentChainPiece = firstChainPiece.getNextLocation();
		} else {
			currentChainPiece = firstChainPiece;
		}
		 
		for (int i = 0; i < locationsSteps; i++){
			int numberOfFalseNextLocations = baseFalseNextLocations + Util.rand(-1, 1);
			List<Location> falseNextLocations = getPossibleNextLocations(currentChainPiece.getLocation(), numberOfFalseNextLocations+1, currentChainPiece.getPreviousLocation());
			if (falseNextLocations.size() == 0){
				// Unable to decide next locations, we give this time.
				break;
			}
			Location nextLocation = (Location) Util.randomElementOf(falseNextLocations);
			falseNextLocations.remove(nextLocation);
			currentChainPiece.setNextLocation(new LocationChainpiece(currentChainPiece.getLocation(), nextLocation));
			currentChainPiece.setFalseNextLocations(falseNextLocations);
			currentChainPiece = currentChainPiece.getNextLocation();
		}
	}
	
	private List<Location> getPossibleNextLocations(Location location, int numberOfLocations, Location excludeLocation) {
		List<Location> ret = new ArrayList<Location>();
		int giveup = 100;
		for (int i = 0; i < numberOfLocations; i++){
			Location randomLocation = LocationManager.getRandomLocation(); // TODO: Pick nearby ones
			if (randomLocation == location || ret.contains(randomLocation) || randomLocation == excludeLocation){
				giveup--;
				if (giveup > 0)
					continue;
				else
					break;
			}
			ret.add(randomLocation);
		}
		return ret;
	}

/*
	public static void main(String[] args){
		LocationManager.
		MissionGenerator.generateMission(new Date(), 5);
		
	}*/


	public Crime getCrime() {
		return crime;
	}
	public Date getDeadline() {
		return deadline;
	}
	public Date getMissionStart() {
		return missionStart;
	}
	
	private Map<String, List<CityLocation>> suspiciousLookingMap = new HashMap<String, List<CityLocation>>(); // Filled with Sleuthwork
	public List<CityLocation> getSuspiciousPlaces(Location location) {
		return suspiciousLookingMap.get(location.getId());
	}
	
	private Map<String, List<Location>> suspiciousNextLocations = new HashMap<String, List<Location>>(); // Filled with evidence and interrogation
	public List<Location> getSuspiciousLocations(Location location) {
		String key = location.getId()+" in "+getCurrentChainPiece().hashCode();
		List<Location> ret = suspiciousNextLocations.get(key);
		if (ret == null){
			ret = new ArrayList<Location>();
			suspiciousNextLocations.put(key, ret);
		}
		return ret;
	}
	
	public void addSuspiciousLocation(Location from, Location to){
		getSuspiciousLocations(from).add(to);
	}
	
	private LocationChainpiece getCurrentChainPiece(){
		return chainPieceStack.peek();
	}
	
	/**
	 * Investigate and find suspicious places to visit in this location.
	 * 
	 * If this place is the crime location, the crime scene is always available.
	 * 
	 * Else, the following random locations could be found
	 *  * Place where the suspect was seen
	 *  * Meeting with informant
	 *  * Meeting with witnesses
	 *  * Meeting with suspects
	 * @param location
	 */
	public void doSleuthwork(Location location) {
		LocationChainpiece currentChainPiece = getCurrentChainPiece();
		if (isSleuthworkDone(location, currentChainPiece)){
			return;
		}
		List<CityLocation> newSuspiciousPlaces = new ArrayList<CityLocation>();
		
		
		// are we on HQ?
		if (currentChainPiece == firstChainPiece && crime.getLocation() != location){
			if (!isCriminalKilled())
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("There is nothing to investigate here, fly to the crime scene!");
			return;
		}
		
		// Are we on the crime location?
		if (location == crime.getLocation()){
			newSuspiciousPlaces.add(new CityLocation(
					crime.getSubject().getSimpleDescription(), 
					location.getId()+"_"+crime.getCrimeType().getLevelCodeSuffix(), 
					CityLocationType.CRIME_SCENE, 
					location,
					difficulty));
		}
		
		// Is this the end of the chain?
		if (currentChainPiece.getNextLocation() == null){
			newSuspiciousPlaces.add(new CityLocation(
					"Criminal hideout", 
					location.getId()+"_HIDEOUT", 
					CityLocationType.HIDEOUT, 
					location,
					difficulty));
			((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You have trustworthy info confirming the location of the criminal!");
		} else {		
			// Are we on the right track?
			if (location == currentChainPiece.getLocation()){ 
				int locations = Util.rand(2, 4);
				for (int i = 0; i < locations; i++){
					CityLocationType cityLocationType = (CityLocationType) Util.randomElementOf(CityLocationType.values());
					if (cityLocationType == CityLocationType.CRIME_SCENE || cityLocationType == CityLocationType.HIDEOUT)
						cityLocationType = CityLocationType.AMBUSH;
					Pair<String,String> detailedLocation = cityLocationType.getRandomDescription();
					newSuspiciousPlaces.add(new CityLocation(
							detailedLocation.getB(), 
							location.getId()+"_"+Math.random(), 
							cityLocationType, 
							location,
							difficulty));
				}
				// This is done when the first objective at the place is completed. doInternationalIntelligence(location);
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("There are "+locations+" places in your list to look for clues leading to the criminal!");
			} else {
				// Waste of time...
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You were unable to find anything suspicious... may be you are on the wrong track?");
			}
		}
		suspiciousLookingMap.put(location.getId(), newSuspiciousPlaces);
		((EIDGame)detective.getGame()).elapseHours(getSleuthCostHours());
		setSleuthworkDone(location, currentChainPiece);
		
		
	}
	
	public void doInternationalIntelligence(Location location) {
		LocationChainpiece currentChainPiece = getCurrentChainPiece();
		
		if (isInternationalIntlDone(currentChainPiece)){
			return;
		}
		if (currentChainPiece.getFalseNextLocations() == null || currentChainPiece.getFalseNextLocations().size() == 0)
			return;
		List<Location> nextLocations = new ArrayList<Location>(currentChainPiece.getFalseNextLocations());
		if (currentChainPiece.getNextLocation() != null)
			nextLocations.add(currentChainPiece.getNextLocation().getLocation());
		Collections.shuffle(nextLocations);
		for (Location nextLocation: nextLocations){
			addSuspiciousLocation(location, nextLocation);
		}
		
		if (nextLocations.size()>0){
			((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You research around and find out that the criminal was in "+location.getCityName()+", but has already fled "+location.getCountryName()+". XXX XXX Further cooperation with the "+
					"InterSleuth Networks provides "+nextLocations.size()+" potential locations.");
		} else {
			((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("Your information network points out the criminal is on "+location.getCountryName());
		}
		setInternationalIntlDone(currentChainPiece); 
	}

	private Map<String, Boolean> internationalIntDoneMap = new HashMap<String, Boolean>();

	private boolean isInternationalIntlDone(LocationChainpiece currentChainPiece) {
		return internationalIntDoneMap.get(""+currentChainPiece.hashCode()) != null;
	}
	
	private void setInternationalIntlDone(LocationChainpiece currentChainPiece) {
		internationalIntDoneMap.put(""+currentChainPiece.hashCode(), Boolean.TRUE);
	}

	private int getSleuthCostHours() {
		return 4; // TODO: Fancy Things
	}

	/**
	 * Checks if we are on the right track to find the criminal
	 * @param l
	 */
	public void gotoLocation(Location l){
		LocationChainpiece currentChainPiece = getCurrentChainPiece();
		// Check if we are tracking back
		if (l == currentChainPiece.getPreviousLocation()){
			chainPieceStack.pop();
		} 
		// Check if we are on the right track
		if (currentChainPiece.getNextLocation() != null && l == currentChainPiece.getNextLocation().getLocation()){
			chainPieceStack.push(currentChainPiece.getNextLocation());
		}
		
		doSleuthwork(l);
		
	}
	
	private Map<String, Boolean> sleuthworkDoneMap = new HashMap<String, Boolean>();
	private boolean isSleuthworkDone(Location location, LocationChainpiece currentChainPiece) {
		return sleuthworkDoneMap.get(location.getId()+" for "+currentChainPiece.hashCode()) != null;
	}
	private void setSleuthworkDone(Location location, LocationChainpiece currentChainPiece) {
		sleuthworkDoneMap.put(location.getId()+" for "+currentChainPiece.hashCode(), Boolean.TRUE);
	}


	
	public Location getPreviousLocation(Location from) {
		LocationChainpiece currentChainPiece = getCurrentChainPiece();
		// Are we on the right track?
		if (from == currentChainPiece.getLocation()){ 
			return currentChainPiece.getPreviousLocation();
		} else {
			return currentChainPiece.getLocation();
		}
	}


	
	public String getClue() {
		return getCurrentChainPiece().getNextLocation().getLocation().getAClue();
	}

	public void addClueForCurrentLocation(String clue) {
		getCurrentChainPiece().addClue(clue);
	}
	
	public List<String> getClues(){
		return getCurrentChainPiece().getClues();
	}


	public void criminalKilled() {
		((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You have killed "+getCrime().getCriminal().getDescription()+"! Return to the HQ to complete your mission.");
		if (!getSuspiciousLocations(getCurrentChainPiece().getLocation()).contains(Location.getHQLocation())){
			addSuspiciousLocation(getCurrentChainPiece().getLocation(), Location.getHQLocation());
		}
		criminalKilled = true;
	}
	
	private boolean criminalKilled = false;
	
	public boolean isCriminalKilled() {
		return criminalKilled;
	}


	public int getDifficulty() {
		return difficulty;
	}
	
}
