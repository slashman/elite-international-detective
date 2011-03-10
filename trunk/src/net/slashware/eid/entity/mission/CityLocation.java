package net.slashware.eid.entity.mission;

public class CityLocation {
	public enum CityLocationType {
		AMBUSH,
		INFORMANT,
		HIDEOUT,
		LAST_SEEN,
		CRIME_SCENE;

		public String getDescription() {
			switch (this){
			case AMBUSH:
				return "Assault criminal safeplace";
			case INFORMANT:
				return "Meet with informant";
			case HIDEOUT:
				return "Criminal hideout";
			case LAST_SEEN:
				return "Criminal last seen";
			case CRIME_SCENE:
				return "Crime Scene";
			}
			return null;
		}
	}
	private String name;
	private String levelCode;
	private CityLocationType cityLocationType;
	
	public CityLocation(String name, String levelCode, CityLocationType cityLocationType) {
		super();
		this.name = name;
		this.levelCode = levelCode;
		this.cityLocationType = cityLocationType;
	}
	public String getName() {
		return name;
	}
	public CityLocationType getCityLocationType() {
		return cityLocationType;
	}
	public String getLevelCode() {
		return levelCode;
	}
	
	
}
