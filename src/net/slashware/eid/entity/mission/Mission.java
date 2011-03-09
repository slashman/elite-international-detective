package net.slashware.eid.entity.mission;

import java.util.Date;

public class Mission {
	private Crime crime;
	private Date deadline;
	private Date missionStart;
	
	public Mission(Crime crime, Date missionStart, Date deadline) {
		super();
		this.crime = crime;
		this.deadline = deadline;
		this.missionStart = missionStart;
	}
	public Crime getCrime() {
		return crime;
	}
	public Date getDeadline() {
		return deadline;
	}
	public Date getMissionStart() {
		return missionStart;
	}
}
