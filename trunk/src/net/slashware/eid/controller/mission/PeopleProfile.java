package net.slashware.eid.controller.mission;

import net.slashie.utils.Util;
import net.slashware.eid.entity.level.Location;

public enum PeopleProfile {
	POLITICIAN,
	SPORTSMAN,
	SCIENTICIST,
	RELIGIOUS_LEADER;
	
	private final static String[] POLITICIAN_MALE_ROLES = new String [] {
		"President",
		"Emperor",
		"Prime Minister",
		"Senate Representative",
		"Congressman"
	};
	
	private final static String[] POLITICIAN_FEMALE_ROLES = new String [] {
		"President",
		"Empress",
		"Prime Minister",
		"Senate Representative",
		"Congresswoman"
	};
	
	private final static String[] RELIGIOUS_LEADER_MALE_ROLES = new String [] {
		"Grand Priest",
		"Priest"
	};
	
	private final static String[] RELIGIOUS_LEADER_FEMALE_ROLES = new String [] {
		"Grand Priestess",
		"Priestess"
	};
	
	private final static String[] SPORTS = new String [] {
		"soccer",
		"basketball",
		"racketball",
		"bowling",
		"videogame",
		"roguelike",
	};
	
	private final static String[] SCIENCES = new String [] {
		"atomic",
		"bacterial",
		"chemical",
		"genetical"
	};
	
	public String getRole(boolean male, Location location){
		switch (this){
		case POLITICIAN:
			if (male)
				return Util.randomElementOf(POLITICIAN_MALE_ROLES)+" of "+location.getCountryName();
			else
				return Util.randomElementOf(POLITICIAN_FEMALE_ROLES)+" of "+location.getCountryName();
		case RELIGIOUS_LEADER:
			if (male)
				return Util.randomElementOf(RELIGIOUS_LEADER_MALE_ROLES)+" of "+location.getCountryName();
			else
				return Util.randomElementOf(RELIGIOUS_LEADER_FEMALE_ROLES)+" of "+location.getCountryName();
		case SCIENTICIST:
			return location.getGentilice()+" "+Util.randomElementOf(SCIENCES)+" scienticist";
		case SPORTSMAN:
			return location.getGentilice()+" "+Util.randomElementOf(SPORTS)+" player";
		}
		return null;
	}
	
	
}
