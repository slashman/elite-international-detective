package net.slashware.eid.entity.player;

public enum WalkingMode {
	WALKING_COOL ("Walking", 0, 100),
	RUNNING("Running",10,25);
	
	private String description;
	private int staminaCost;
	private int timeCostPerStep;
	
	public String getDescription() {
		return description;
	}

	public int getStaminaCost() {
		return staminaCost;
	}

	public int getTimeCostPerStep() {
		return timeCostPerStep;
	}

	private WalkingMode(String description, int staminaCost, int timeCostPerStep) {
		this.description = description;
		this.staminaCost = staminaCost;
		this.timeCostPerStep = timeCostPerStep;
	}

	public WalkingMode getNext() {
		switch (this){
		case RUNNING:
			return WALKING_COOL;
		case WALKING_COOL:
			return RUNNING;
		}
		return null;
	}
}
