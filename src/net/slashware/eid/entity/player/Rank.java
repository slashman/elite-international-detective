package net.slashware.eid.entity.player;

public enum Rank {
	ROOKIE("Rookie"),
	OFFICER("Officer"),
	DETECTIVE("Detective"),
	SERGEANT("Sergeant"),
	LIUTENANT("Liutenant"),
	CAPTAIN("Captain"),
	ACE("Ace Captain"),
	COLONEL("Colonel"),
	COMMISIONER("Commisioner")
	;
	
	private String description;

	private Rank(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
