package net.slashware.eid.entity.mission;

public class CityLocation {
	public enum CityLocationType {
		AMBUSH ("Assault criminal safehaven"),
		INFORMANT ("Informant"),
		HIDEOUT ("The criminal hideout"),
		LAST_SEEN ("Criminal was seen here"),
		SUSPECTS ("Interrogate Suspects"),
		WITNESS ("Visit witness"),
		CRIME_SCENE ("The Crime Schene");

		private String description;

		public String getDescription() {
			return description;
		}

		private CityLocationType(String description) {
			this.description = description;
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
