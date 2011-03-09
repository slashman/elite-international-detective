package net.slashware.eid.entity.player;

public enum Lethality {
	INTIMIDATE,
	DISABLE,
	KILL;
	
	public Lethality getNext(){
		switch (this){
		case INTIMIDATE:
			return DISABLE;
		case DISABLE:
			return KILL;
		case KILL:
			return INTIMIDATE;
		}
		return null;
	}
	
	public String getDescription(){
		switch (this){
		case DISABLE:
			return "Disable";
		case INTIMIDATE:
			return "Intimidate";
		case KILL:
			return "Kill!";
		}
		return null;
	}
}
