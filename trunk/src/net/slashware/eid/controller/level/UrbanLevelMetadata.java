package net.slashware.eid.controller.level;

import net.slashie.serf.level.LevelMetaData;
import net.slashware.eid.entity.mission.CityLocation;

public class UrbanLevelMetadata extends LevelMetaData{
	private CityLocation target;
	public CityLocation getTarget() {
		return target;
	}
	public void setTarget(CityLocation target) {
		this.target = target;
	}
	public UrbanLevelMetadata(String levelID) {
		super(levelID);
	}

}
