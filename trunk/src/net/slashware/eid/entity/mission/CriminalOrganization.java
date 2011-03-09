package net.slashware.eid.entity.mission;

public enum CriminalOrganization {
	BLACK_KNIGHTS ("Black Knights"),
	BLUE_HOPE ("Blue Hope"),
	YAHARAI_LALANA ("Yaharai Lalana"),
	IRON_FIST ("Iron Fist"),
	FREEDOM_CRUSADERS ("Freedom Crusaders");
	
	private String name;

	public String getName() {
		return name;
	}

	private CriminalOrganization(String name) {
		this.name = name;
	}
}
