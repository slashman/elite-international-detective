package net.slashware.eid.entity.player;

public enum Rank {
	ROOKIE("Rookie",1,20,100),
	OFFICER("Officer",2,30,110),
	DETECTIVE("Detective",3,40,120),
	SERGEANT("Sergeant",4,55,130),
	LIUTENANT("Liutenant",5,70,140),
	CAPTAIN("Captain",6,85,150),
	ACE("Ace Captain",7,105,160),
	COLONEL("Colonel",8,120,170),
	COMMISIONER("Commisioner",9,140,180)
	;
	
	private String description;
	private int difficulty;
	private int luck, stamina;

	private Rank(String description, int difficulty, int luck, int stamina) {
		this.description = description;
		this.difficulty = difficulty;
		this.stamina = stamina;
		this.luck = luck;
	}

	public String getDescription() {
		return description;
	}

	public static Rank getRankForCompletedMissions(int completedMissionQuantity) {
		if (completedMissionQuantity > 30)
			return COMMISIONER;
		else if (completedMissionQuantity > 25)
			return COLONEL;
		else if (completedMissionQuantity > 20)
			return ACE;
		else if (completedMissionQuantity > 16)
			return CAPTAIN;
		else if (completedMissionQuantity > 12)
			return LIUTENANT;
		else if (completedMissionQuantity > 8)
			return SERGEANT;
		else if (completedMissionQuantity >= 4)
			return DETECTIVE;
		else if (completedMissionQuantity >= 1)
			return OFFICER;
		else 
			return ROOKIE;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public int getLuck() {
		return luck;
	}

	public int getStamina() {
		return stamina;
	}
}
