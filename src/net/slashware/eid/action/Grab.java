package net.slashware.eid.action;

import net.slashie.serf.action.Action;
import net.slashie.serf.baseDomain.AbstractItem;
import net.slashie.serf.level.AbstractLevel;
import net.slashware.eid.entity.player.DetectiveActor;

public class Grab extends Action{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute() {
		DetectiveActor aPlayer = (DetectiveActor) performer;
		AbstractLevel aLevel = aPlayer.getLevel();
		AbstractItem destinationItem = targetItem;
		if (destinationItem != null){
			if (aPlayer.canCarry(destinationItem, 1)){
				aLevel.addMessage("You pick up the "+destinationItem.getDescription()+".");
				aPlayer.addItem(destinationItem, 1);
				aLevel.removeItemFrom(destinationItem, performer.getPosition());
			} else {
				aLevel.addMessage("You have too many things already to pick up the "+destinationItem.getDescription()+"!.");
			}
		} else {
			aLevel.addMessage("There is nothing to pick up here!");
		}
		
	}

	@Override
	public String getID() {
		return "Grab";
	}
	
	public boolean needsUnderlyingItem(){return true;}
	public String getPrompUnderlyingItem(){return "What will you grab?";}
	

}
