package net.slashware.eid.entity.level;

import net.slashie.utils.Util;

public class Location {
	private static final Landmark[] DEFAULT_LANDMARKS = new Landmark[]{
		new Landmark("The bank"),
		new Landmark("A marketplace"),
		new Landmark("A shopping mall"),
		new Landmark("A theme park")
	};
	
	private String id;
	private String countryName;
	private String cityName;
	private String description;
	private String gentilice;
	private Landmark[] landmarks;
	
	public Location(String id, String countryName, String cityName,
			String description, String gentilice, Landmark[] landmarks) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.cityName = cityName;
		this.description = description;
		this.gentilice = gentilice;
		this.landmarks = landmarks;
	}
	
	public String getId() {
		return id;
	}
	public String getCountryName() {
		return countryName;
	}
	public String getCityName() {
		return cityName;
	}
	public String getDescription() {
		return description;
	}
	public String getGentilice() {
		return gentilice;
	}
	
	public String getFullCityName(){
		return cityName+", "+countryName;
	}
	public Landmark getALandmark() {
		if (Util.chance(20)){
			// Use a famous landmark
			return (Landmark) Util.randomElementOf(landmarks);
		} else {
			return (Landmark) Util.randomElementOf(DEFAULT_LANDMARKS);
		}
	}
	
	
}
