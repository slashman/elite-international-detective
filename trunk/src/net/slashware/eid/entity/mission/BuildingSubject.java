package net.slashware.eid.entity.mission;

import net.slashware.eid.entity.level.Landmark;

public class BuildingSubject extends CrimeSubject{
	private Landmark landmark;
	
	public BuildingSubject(Landmark landmark) {
		super();
		this.landmark = landmark;
	}
	
	public String getDetailedDescription(){
		if (landmark.getBackground() != null){
			return landmark.getName()+", "+landmark.getBackground()+",";
		} else {
			return landmark.getName();
		}
		
	}
}
