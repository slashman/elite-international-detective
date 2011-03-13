package net.slashware.eid.entity.mission;

public class Criminal {
	private static final long serialVersionUID = 1L;
	private String description;
	private String NPCID;
	
	public Criminal(String NPCID, String description,int profile, CriminalOrganization criminalOrganization) {
		this.NPCID = NPCID;
		this.profile = profile;
		this.criminalOrganization = criminalOrganization;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getNPCID() {
		return NPCID;
	}
	
	private CriminalOrganization criminalOrganization;
	private int profile;
	public CriminalOrganization getCriminalOrganization() {
		return criminalOrganization;
	}
	public int getProfile() {
		return profile;
	}
	
}
