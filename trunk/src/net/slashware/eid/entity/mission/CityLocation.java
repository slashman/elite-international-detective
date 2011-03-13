package net.slashware.eid.entity.mission;

import net.slashie.serf.levelGeneration.bsp.BSPRoom;
import net.slashie.util.Pair;
import net.slashie.utils.Util;
import net.slashware.eid.controller.level.UrbanZoneGenerator;
import net.slashware.eid.entity.level.Location;

public class CityLocation {
	public enum CityLocationType {
		CRIME_SCENE ("The Crime Scene"),
		AMBUSH ("Criminal safehaven"), 
		INFORMANT ("Meet Informant"), 
		HIDEOUT ("The Criminal Hideout"),
		LAST_SEEN ("Criminal spotted"),
		SUSPECTS ("Interrogate Suspects"), 
		WITNESS ("Visit witness"),
		;

		private String description;

		public String getDescription() {
			return description;
		}

		private CityLocationType(String description) {
			this.description = description;
		}
		
		public Pair<String,String> getRandomDescription() {
			switch (this){
			case AMBUSH:
				return new Pair<String, String>("WAREHOUSE","Street 4, Warehouse");
			case CRIME_SCENE:
				throw new RuntimeException("No Random Description Available for CRIME_SCENE");
			case HIDEOUT:
				throw new RuntimeException("No Random Description Available for HIDEOUT");
			case LAST_SEEN:
				return new Pair<String, String>("LIBRARY", "Public Library");
			case INFORMANT:
				return new Pair<String, String>("MALL", "Shopping Mall");
			case SUSPECTS:
				return new Pair<String, String>("POLICE", "Police Station");
			case WITNESS:
				return new Pair<String, String>("HOUSE", "House of Mr. McCoy");
			}
			return null;
		}
		
	}
	private String name;
	private String levelCode;
	private CityLocationType cityLocationType;
	private Location location;
	private int difficulty;
	
	public CityLocation(String name, String levelCode, CityLocationType cityLocationType, Location location, int difficulty) {
		super();
		this.name = name;
		this.levelCode = levelCode;
		this.cityLocationType = cityLocationType;
		this.location = location;
		this.difficulty = difficulty;
	}
	public String getName() {
		return name;
	}
	public CityLocationType getCityLocationType() {
		return cityLocationType;
	}
	public String getLevelCode() {
		return levelCode;
	}
	
	public Location getLocation() {
		return location;
	}
	public void drawBlock(char[][] charBuffer, BSPRoom room) {
		// Draw streets
		UrbanZoneGenerator.drawStreetsFrame(charBuffer, room);
		
		// Draw Simple Room with entrance
		UrbanZoneGenerator.drawSimpleRoomWithEntrance(charBuffer, room);
		
		switch(cityLocationType){
		case AMBUSH:
			//Add lots of criminals
			setupCriminalBuilding(charBuffer, room, difficulty);
			break;
		case CRIME_SCENE:
			// Add evidence
			setupCrimeScene(charBuffer, room);
			break;
		case HIDEOUT:
			// Add LOTS of criminals and the ringleader
			setupHideout(charBuffer, room, difficulty);
			break;
		case INFORMANT:
			// Add informant NPC
			setupInformant(charBuffer, room);
			break;
		case LAST_SEEN:
			// Add evidence
			setupEvidence(charBuffer, room);
			break;
		case SUSPECTS:
			// Add NPCs to interrogate, and some cops
			setupPoliceStation(charBuffer, room);
			break;
		case WITNESS:
			// Add NPCs to interrogate
			setupWitnessHouse(charBuffer, room);
			break;
		}
	}
	private void setupWitnessHouse(char[][] charBuffer, BSPRoom room) {
		int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
		int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
		charBuffer[yrand][xrand] = 'O';
		
		xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
		yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
		charBuffer[yrand][xrand] = 'W';
	}
	
	private void setupPoliceStation(char[][] charBuffer, BSPRoom room) {
		int cops = Util.rand(4, 8);
		for (int i = 0; i < cops; i++){
			int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
			int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
			charBuffer[yrand][xrand] = 'O';
		}
		int suspects = Util.rand(2, 3);
		for (int i = 0; i < suspects; i++){
			int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
			int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
			charBuffer[yrand][xrand] = 'U';
		}
		
	}
	private void setupEvidence(char[][] charBuffer, BSPRoom room) {
		int evidence = Util.rand(1, 2);
		for (int i = 0; i < evidence; i++){
			int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
			int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
			charBuffer[yrand][xrand] = 'E';
		}
	}
	private void setupInformant(char[][] charBuffer, BSPRoom room) {
		int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
		int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
		charBuffer[yrand][xrand] = 'I';
	}
	private void setupHideout(char[][] charBuffer, BSPRoom room, int difficulty2) {
		/*int criminals = Util.rand(5, (int)Math.round((room.getHeight()*room.getWidth())));
		for (int i = 0; i < criminals; i++){
			int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
			int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
			charBuffer[yrand][xrand] = 'C';
		}*/
		
		int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
		int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
		charBuffer[yrand][xrand] = 'L';
	}
	private void setupCrimeScene(char[][] charBuffer, BSPRoom room) {
		int evidence = Util.rand(2, 3);
		for (int i = 0; i < evidence; i++){
			int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
			int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
			charBuffer[yrand][xrand] = 'E';
		}
	}
	private void setupCriminalBuilding(char[][] charBuffer, BSPRoom room,
			int difficulty) {
		int criminals = Util.rand(5, (int)Math.round((room.getHeight()*room.getWidth())/2.0d));
		for (int i = 0; i < criminals; i++){
			int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
			int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
			charBuffer[yrand][xrand] = 'C';
		}
		int clues = Util.rand(3, 5);
		for (int i = 0; i < clues; i++){
			int xrand = Util.rand(room.getXpos()+4, room.getXpos()+room.getWidth()-4);
			int yrand = Util.rand(room.getYpos()+4, room.getYpos()+room.getHeight()-4);
			charBuffer[yrand][xrand] = 'E';
		}
	}
	
	
}
