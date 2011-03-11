package net.slashware.eid.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slashie.serf.level.LevelMetaData;
import net.slashie.utils.Util;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.player.DetectiveActor;

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

	public static Location getLocation(String locationId) {
		return locationsMap.get(locationId);
	}

	private static Map<String, LevelMetaData> levelMetaDataMap = new HashMap<String, LevelMetaData>(); 
	public static void setLevelMetadata(String levelCode, DetectiveActor player) {
		LevelMetaData ret = levelMetaDataMap.get(levelCode);
		if (ret == null){
			ret = new LevelMetaData(levelCode);
			levelMetaDataMap.put(levelCode, ret);
			player.getGame().addMetaData(levelCode, ret);
		}
	}
	
}
