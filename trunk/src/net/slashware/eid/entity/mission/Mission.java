package net.slashware.eid.entity.mission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.slashie.serf.game.SworeGame;
import net.slashie.serf.ui.UserInterface;
import net.slashie.util.Pair;
import net.slashie.utils.Util;
import net.slashware.eid.EIDGame;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.controller.LocationManager;
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
	
	public Mission(Crime crime, Date missionStart, Date deadline, int difficulty, DetectiveActor detective) {
		super();
		this.crime = crime;
		this.deadline = deadline;
		this.missionStart = missionStart;
		this.detective = detective;
		generateLocationPath(difficulty);
		chainPieceStack.push(firstChainPiece);
	}
	
	
	private void generateLocationPath(int difficulty) {
		int baseFalseNextLocations = difficulty + 2;
		int locationsSteps = (int)Math.round((double)(difficulty + 3) * 1.5d);
		locationsSteps = locationsSteps + Util.rand(-2, 2);

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
			List<Location> falseNextLocations = getPossibleNextLocations(currentChainPiece.getLocation(), numberOfFalseNextLocations+1);
			if (falseNextLocations.size() == 0){
				// Unable to decide next locations, we give this time.
				break;
			}
			Location nextLocation = (Location) Util.randomElementOf(falseNextLocations);
			falseNextLocations.remove(nextLocation);
			currentChainPiece.setNextLocation(new LocationChainpiece(currentChainPiece.getLocation(), nextLocation));
			currentChainPiece = currentChainPiece.getNextLocation();
		}
	}
	
	private List<Location> getPossibleNextLocations(Location location, int numberOfLocations) {
		List<Location> ret = new ArrayList<Location>();
		int giveup = 100;
		for (int i = 0; i < numberOfLocations; i++){
			Location randomLocation = LocationManager.getRandomLocation(); // TODO: Pick nearby ones
			if (randomLocation == location || ret.contains(randomLocation)){
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
		List<Location> ret = suspiciousNextLocations.get(location.getId());
		if (ret == null){
			ret = new ArrayList<Location>();
			suspiciousNextLocations.put(location.getId(), ret);
		}
		return ret;
	}
	
	public void addSuspiciousLocation(Location from, Location to){
		getSuspiciousLocations(from).add(to);
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
		if (isSleuthworkDone(location)){
			return;
		}
		List<CityLocation> newSuspiciousPlaces = new ArrayList<CityLocation>();
		LocationChainpiece currentChainPiece = chainPieceStack.peek();
		
		// are we on HQ?
		if (currentChainPiece == firstChainPiece && crime.getLocation() != location){
			((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("There is nothing to investigate here, fly to the crime scene!");

		}
		
		// Are we on the crime location?
		if (location == crime.getLocation()){
			newSuspiciousPlaces.add(new CityLocation(crime.getSubject().getSimpleDescription(), location.getId()+"_"+crime.getCrimeType().getLevelCodeSuffix(), CityLocationType.CRIME_SCENE));
		}
		
		// Is this the end of the chain?
		if (currentChainPiece.getNextLocation() == null){
			newSuspiciousPlaces.add(new CityLocation("Criminal hideout", location.getId()+"_HIDEOUT", CityLocationType.HIDEOUT));
			((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You have trustworthy info confirming the location of the criminal!");
		} else {		
			// Are we on the right track?
			if (location == currentChainPiece.getLocation()){ 
				int locations = Util.rand(2, 6);
				for (int i = 0; i < locations; i++){
					CityLocationType cityLocationType = (CityLocationType) Util.randomElementOf(CityLocationType.values());
					if (cityLocationType == CityLocationType.CRIME_SCENE || cityLocationType == CityLocationType.HIDEOUT)
						cityLocationType = CityLocationType.LAST_SEEN;
					Pair<String,String> detailedLocation = getRandomDescriptionFor(cityLocationType);
					newSuspiciousPlaces.add(new CityLocation(detailedLocation.getB(), location.getId()+"_"+detailedLocation.getA(), cityLocationType));
				}
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You find about "+locations+" places to look for clues leading to the criminal!");
			} else {
				// Waste of time...
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You were unable to find anything suspicious... may be you are on the wrong track?");
			}
		}
		suspiciousLookingMap.put(location.getId(), newSuspiciousPlaces);
		((EIDGame)detective.getGame()).elapseHours(getSleuthCostHours());
		setSleuthworkDone(location);
	}
	
	private int getSleuthCostHours() {
		return 4; // TODO: Fancy Things
	}


	private Pair<String,String> getRandomDescriptionFor(CityLocationType cityLocationType) {
		switch (cityLocationType){
		case AMBUSH:
			return new Pair<String, String>("WAREHOUSE","Street 4, Warehouse");
		case CRIME_SCENE:
			throw new RuntimeException("No Random Description Available for CRIME_SCENE");
		case HIDEOUT:
			throw new RuntimeException("No Random Description Available for HIDEOUT");
		case INFORMANT:
			return new Pair<String, String>("MALL", "Shopping Mall");
		case LAST_SEEN:
			return new Pair<String, String>("LIBRARY", "Public Library");
		case SUSPECTS:
			return new Pair<String, String>("POLICE", "Police Station");
		case WITNESS:
			return new Pair<String, String>("HOUSE", "House of Mr. McCoy");
		}
		return null;
	}


	/**
	 * Checks if we are on the right track to find the criminal
	 * @param l
	 */
	public void gotoLocation(Location l){
		LocationChainpiece currentChainPiece = chainPieceStack.peek();
		// Check if we are tracking back
		if (l == currentChainPiece.getPreviousLocation()){
			chainPieceStack.pop();
		} 
		// Check if we are on the right track
		if (l == currentChainPiece.getNextLocation().getLocation()){
			chainPieceStack.push(chainPieceStack.peek().getNextLocation());
		}
		
		doSleuthwork(l);
		
	}
	
	private Map<String, Boolean> sleuthworkDoneMap = new HashMap<String, Boolean>();
	private boolean isSleuthworkDone(Location location) {
		return sleuthworkDoneMap.get(location.getId()) != null;
	}
	private void setSleuthworkDone(Location location) {
		sleuthworkDoneMap.put(location.getId(), Boolean.TRUE);
	}
	
}
