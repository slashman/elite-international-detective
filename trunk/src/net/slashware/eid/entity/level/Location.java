package net.slashware.eid.entity.level;

import java.util.ArrayList;
import java.util.List;

import net.slashie.utils.Util;
import net.slashware.eid.controller.level.LocationManager;

public class Location {
	public static final Landmark[] DEFAULT_LANDMARKS = new Landmark[]{
		new Landmark("The bank"),
		new Landmark("A marketplace"),
		new Landmark("A shopping mall"),
		new Landmark("A theme park")
	};
	
	private Country country;
	
	private String id;
	private String cityName;
	private String description;
	private String history;
	private String[] insigniaClues;
	private Landmark[] landmarks;
	
	private List<String> clues = new ArrayList<String>();  
	
	public Location(String id, Country country, String cityName,
			String description, String history, String[] insigniaClues, Landmark[] landmarks) {
		super();
		this.id = id;
		if (country == null)
			throw new RuntimeException("Invalid country for city "+id);
		this.country = country;
		this.cityName = cityName;
		this.description = description;
		this.history = history;
		this.landmarks = landmarks;
		this.insigniaClues = insigniaClues;
		// Create clues
		for (Landmark landmark: landmarks){
			clues.add(landmark.getName());
		}
		for (String insigniaClue: insigniaClues){
			clues.add(insigniaClue);
		}
		for (String flagClue: country.getFlagClues()){
			clues.add(flagClue);
		}
		for (String insigniaClue: country.getInsigniaClues()){
			clues.add(insigniaClue);
		}
	}
	
	public String getId() {
		return id;
	}
	public String getCountryName() {
		return country.getShortname();
	}
	public String getCityName() {
		return cityName;
	}
	public String getDescription() {
		return description;
	}
	public String getGentilice() {
		return country.getAdjective();
	}
	
	public String getFullCityName(){
		return cityName+", "+getCountryName();
	}
	public Landmark getALandmark() {
		if (Util.chance(80)){
			// Use a famous landmark
			return (Landmark) Util.randomElementOf(landmarks);
		} else {
			return (Landmark) Util.randomElementOf(DEFAULT_LANDMARKS);
		}
	}

	public static Location getHQLocation() {
		return LocationManager.getLocation("CO1");
	}

	public String getHistory() {
		return history;
	}

	public String[] getInsigniaClues() {
		return insigniaClues;
	}

	
	public String getAClue() {
		return (String) Util.randomElementOf(clues);
	}
	
	
}
