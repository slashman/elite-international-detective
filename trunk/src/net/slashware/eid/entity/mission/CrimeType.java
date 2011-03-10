package net.slashware.eid.entity.mission;

public enum CrimeType {
	MURDER,
	KIDNAPPING,
	TERRORIST_BOMBING;
	
	public String getCriminalDescription(){
		switch(this){
		case MURDER:
			return "murderer";
		case KIDNAPPING:
			return "kidnapper";
		case TERRORIST_BOMBING:
			return "terrorist";
		}
		return null;
	}

	public String getCrimeDescription() {
		switch(this){
		case MURDER:
			return "murderered";
		case KIDNAPPING:
			return "kidnapped";
		case TERRORIST_BOMBING:
			return "bombed";
		}
		return null;
	}
	
	public String getCrimeTitle() {
		switch(this){
		case MURDER:
			return "murder";
		case KIDNAPPING:
			return "abduction";
		case TERRORIST_BOMBING:
			return "bombing";
		}
		return null;
	}
}
