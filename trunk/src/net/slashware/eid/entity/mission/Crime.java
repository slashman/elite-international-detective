package net.slashware.eid.entity.mission;

import net.slashie.utils.Util;
import net.slashware.eid.entity.level.Location;

public class Crime {
	private CrimeType crimeType;
	private CrimeSubject subject;
	private Criminal criminal;
	private Location location;
	private String detailedLocation;
	
	public Crime(CrimeType crimeType, CrimeSubject subject, Criminal criminal, 
			Location location, String detailedLocation) {
		super();
		this.crimeType = crimeType;
		this.subject = subject;
		this.criminal = criminal;
		this.location = location;
		this.detailedLocation = detailedLocation;
	}

	public CrimeType getCrimeType() {
		return crimeType;
	}

	public CrimeSubject getSubject() {
		return subject;
	}

	public Criminal getCriminal() {
		return criminal;
	}

	public String getDetailedLocation() {
		return detailedLocation;
	}

	public Location getLocation() {
		return location;
	} 
	
	
}
