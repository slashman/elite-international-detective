package net.slashware.eid.controller.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slashie.serf.level.LevelMetaData;
import net.slashie.utils.Util;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.mission.CityLocation;
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

	private static Map<String, UrbanLevelMetadata> levelMetaDataMap = new HashMap<String, UrbanLevelMetadata>(); 
	
	public static void setLevelMetadata(CityLocation location, DetectiveActor player) {
		UrbanLevelMetadata ret = levelMetaDataMap.get(location.getLevelCode());
		if (ret == null){
			ret = new UrbanLevelMetadata(location.getLevelCode());
			ret.setTarget(location);
			levelMetaDataMap.put(location.getLevelCode(), ret);
			player.getGame().addMetaData(location.getLevelCode(), ret);
		}
	}
	
}
