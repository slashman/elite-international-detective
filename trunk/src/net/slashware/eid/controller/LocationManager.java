package net.slashware.eid.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slashie.utils.Util;
import net.slashware.eid.entity.level.Location;

public class LocationManager {
	private static Map<String, Location> locationsMap = new HashMap<String, Location>();
	private static List<Location> locationsList = new ArrayList<Location>();
	
	public static Location getRandomLocation(){
		return (Location) Util.randomElementOf(locationsList);
	}
	
	public static void addLocation(Location l){
		locationsMap.put(l.getId(), l);
		locationsList.add(l);
	}
	
}