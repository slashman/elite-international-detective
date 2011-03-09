package net.slashware.eid.entity.mission;

import net.slashware.eid.entity.NPC;

public class Criminal extends NPC{
	private static final long serialVersionUID = 1L;
	
	public Criminal(String typeId, String description,String weaponId, String armorId,int luckyPoints, int profile, CriminalOrganization criminalOrganization, String... talkLines) {
		super(typeId, description, true, false, weaponId, armorId,luckyPoints, talkLines);
		this.profile = profile;
		this.criminalOrganization = criminalOrganization;
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
