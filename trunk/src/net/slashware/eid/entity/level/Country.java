package net.slashware.eid.entity.level;

public class Country {
	private String id;
	private String longname;
	private String shortname;
	private String adjective;
	private String[] flagClues;
	private String[] insigniaClues;
	public Country(String id, String longname, String shortname,
			String adjective, String[] flagClues, String[] insigniaClues) {
		super();
		this.id = id;
		this.longname = longname;
		this.shortname = shortname;
		this.adjective = adjective;
		this.flagClues = flagClues;
		this.insigniaClues = insigniaClues;
	}
	public String getId() {
		return id;
	}
	
	public String getLongname() {
		return longname;
	}
	
	public String getShortname() {
		return shortname;
	}
	
	public String getAdjective() {
		return adjective;
	}
	
	public String[] getFlagClues() {
		return flagClues;
	}
	
	public String[] getInsigniaClues() {
		return insigniaClues;
	}
	
}
