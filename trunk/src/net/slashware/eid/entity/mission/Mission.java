package net.slashware.eid.entity.mission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slashware.eid.entity.level.Location;

public class Mission {
	private Crime crime;
	private Date deadline;
	private Date missionStart;
	
	public Mission(Crime crime, Date missionStart, Date deadline) {
		super();
		this.crime = crime;
		this.deadline = deadline;
		this.missionStart = missionStart;
	}
	public Crime getCrime() {
		return crime;
	}
	public Date getDeadline() {
		return deadline;
	}
	public Date getMissionStart() {
		return missionStart;
	}
	
	private Map<String, List<CityLocation>> suspiciousLookingMap = new HashMap<String, List<CityLocation>>();
	public List<CityLocation> getSuspiciousPlaces(Location location) {
		return suspiciousLookingMap.get(location.getId());
	}
}
