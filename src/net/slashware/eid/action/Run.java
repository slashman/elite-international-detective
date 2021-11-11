package net.slashware.eid.action;

import net.slashie.serf.action.Action;
import net.slashware.eid.entity.player.DetectiveActor;

//TODO: Transform into a Command when SERF implements the Command pattern (same as action but doesn't consume turns) 
public class Run extends Action{

	@Override
	public void execute() {
		((DetectiveActor)performer).setWalkingMode(((DetectiveActor)performer).getWalkingMode().getNext());
	}
	
	@Override
	public int getCost() {
		return 50;
	}

	@Override
	public String getID() {
		return null;
	}
	
}
