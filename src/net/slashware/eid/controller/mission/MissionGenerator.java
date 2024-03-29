package net.slashware.eid.controller.mission;

import java.util.Calendar;
import java.util.Date;

import net.slashie.serf.text.SimpleEnglishNameGenerator;
import net.slashie.utils.Util;
import net.slashware.eid.controller.level.LocationManager;
import net.slashware.eid.entity.level.Landmark;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.mission.BuildingSubject;
import net.slashware.eid.entity.mission.Crime;
import net.slashware.eid.entity.mission.CrimeLocation;
import net.slashware.eid.entity.mission.CrimeSubject;
import net.slashware.eid.entity.mission.CrimeType;
import net.slashware.eid.entity.mission.Criminal;
import net.slashware.eid.entity.mission.CriminalOrganization;
import net.slashware.eid.entity.mission.Mission;
import net.slashware.eid.entity.mission.PersonSubject;
import net.slashware.eid.entity.player.DetectiveActor;

public class MissionGenerator {
	public static Mission generateMission(Date currentDate, int difficulty, DetectiveActor detective){
		CrimeType crimeType = selectCrimeType(difficulty);
		Location location = selectCrimeLocation(difficulty);
		CrimeSubject subject = selectSubject(crimeType, difficulty, location);
		String detailedLocation = selectDetailedLocation(crimeType, location, subject);
		Criminal criminal = createCriminal(crimeType, difficulty);
		Crime crime = new Crime(crimeType, subject, criminal, location, detailedLocation);
		int deadTimeDays = defineDeadTime(difficulty);
		Date missionStart = getMissionStart(currentDate, deadTimeDays);
		Date deadline = defineDeadline(missionStart, difficulty);
		Mission mission = new Mission(crime, missionStart, deadline, difficulty, detective);
		if (Location.getHQLocation() != crime.getLocation())
			mission.addSuspiciousLocation(Location.getHQLocation(), crime.getLocation());
		return mission;
	}

	private static Date getMissionStart(Date currentDate, int deadTimeDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, deadTimeDays);
		return c.getTime();
	}
	
	public final static int MAX_DIFFICULTY = 9;

	private static Date defineDeadline(Date missionStart, int difficulty) {
		int missionDays = MAX_DIFFICULTY * 10 - Util.rand(0, 8) - difficulty * MAX_DIFFICULTY;
		Calendar c = Calendar.getInstance();
		c.setTime(missionStart);
		c.add(Calendar.DAY_OF_YEAR, missionDays);
		return c.getTime();
	}

	private static int defineDeadTime(int difficulty) {
		return Util.rand(2, 60);
	}

	private static String selectDetailedLocation(
			CrimeType type,
			Location location,
			CrimeSubject subject) {
		if (subject instanceof PersonSubject){
			CrimeLocation crimeLocation = (CrimeLocation) Util.randomElementOf(CrimeLocation.values());
			String ret = "";
			if (crimeLocation.isRequiresHisHer()){
				if (((PersonSubject)subject).isMale())
					ret = "his ";
				else
					ret = "her ";
			}
			return ret + crimeLocation.getDetailedLocation() + " " +location.getFullCityName();
		} else {
			return location.getFullCityName();
		}
	}

	private static Location selectCrimeLocation(int difficulty) {
		// Select a random location
		return LocationManager.getRandomLocation();
	}

	private static CrimeSubject selectSubject(CrimeType crimeType, int difficulty, Location location) {
		switch (crimeType){
		case MURDER: case KIDNAPPING:
			// Define a profile
			PeopleProfile profile = (PeopleProfile) Util.randomElementOf(PeopleProfile.values());
			boolean male = Util.chance(50);
			// Create a name
			String name = createName (location, male);
			// Create a background
			String background = profile.getRole(male, location);
			return new PersonSubject(name,background,male);
		case TERRORIST_BOMBING:
			Landmark landmark = location.getALandmark();
			return new BuildingSubject(landmark);
		}
		return null;
	}

	private static String createName(Location location, boolean male) {
		return new SimpleEnglishNameGenerator().generateFullName(male);
	}
	
	private static Criminal createCriminal(CrimeType crimeType, int difficulty) {
		switch (difficulty){
		case 0: case 1: 
			return new Criminal("Ethan Black","Ethan Black",10,CriminalOrganization.IRON_FIST);
		case 2:	case 3: case 4: 
			return new Criminal("Adrianeth Avil","Adrianeth Avil",10,CriminalOrganization.YAHARAI_LALANA);
		case 5:	case 6: case 7:
			return new Criminal("Slash","Slash",10,CriminalOrganization.BLUE_HOPE);
		case 8: case 9:
			return new Criminal("KORNEL_SANDIEGO","Kornel Sandiego",10,CriminalOrganization.BLACK_KNIGHTS);	
		}
		return new Criminal("KORNEL_SANDIEGO","Kornel Sandiego",10,CriminalOrganization.BLACK_KNIGHTS);
		
	}

	private static CrimeType selectCrimeType(int difficulty) {
		return (CrimeType) Util.randomElementOf(CrimeType.values());
	}
}
