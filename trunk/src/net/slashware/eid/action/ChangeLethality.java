package net.slashware.eid.action;

import net.slashie.serf.action.Action;
import net.slashware.eid.entity.player.DetectiveActor;

public class ChangeLethality extends Action{

	@Override
	public void execute() {
		((DetectiveActor)performer).setLethality(((DetectiveActor)performer).getLethality().getNext());
	}
	
	@Override
	public int getCost() {
		return 0;
	}

	@Override
	public String getID() {
		return null;
	}
	
}
