package net.slashware.eid.action;

import net.slashie.serf.action.Action;
import net.slashie.serf.action.Actor;
import net.slashie.serf.action.AwareActor;
import net.slashie.serf.ui.ActionCancelException;
import net.slashie.utils.Position;
import net.slashware.eid.entity.DetectiveActor;
import net.slashware.eid.entity.SimpleLevelCell;

public class Walk extends Action{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean actionCancelled = false;
	
	@Override
	public boolean canPerform(Actor a) {
        Position var = directionToVariation(targetDirection);
        Position destinationPoint = Position.add(a.getPosition(), var);
    	/*Actor actor = a.getLevel().getActorAt(destinationPoint);
    	if (actor != null){
    		invalidationMessage = "You can't walk there";
    		return false;
    	}*/
        SimpleLevelCell cell = (SimpleLevelCell) a.getLevel().getMapCell(destinationPoint);
        
        if (cell == null){
        	invalidationMessage = "You can't walk there";
        	return false;
        }
        
        if (cell.isSolid()){
        	invalidationMessage = "You can't walk there";
        	return false;
        }
		return true;
	}

	
	@Override
	public void execute() {
		actionCancelled = false;
		
		if (targetDirection == Action.SELF){
			youMessage("You stand alert.");
			return;
		}
		
		Position var = directionToVariation(targetDirection);
        Position destinationPoint = Position.add(performer.getPosition(), var);
        
        Actor actor = performer.getLevel().getActorAt(destinationPoint);
		if (actor != null){
			if (performer == performer.getLevel().getPlayer()){
				actor.onPlayerBump();
				actionCancelled = true;
	        	return;
			} else {
				return;
			}
		}
		
	    try {
	    	((AwareActor)performer).landOn(destinationPoint);
		} catch (ActionCancelException e) {
			actionCancelled = true;
		}
	}
	

	@Override
	public String getID() {
		return "WALK";
	}
	
	
	@Override
	public int getCost() {
		if (actionCancelled){
			actionCancelled = false;
			return 0;
		}
		if (performer instanceof DetectiveActor){
			return( (DetectiveActor) performer).getWalkCost();
		} else
			return 50;
		
	}
	
	

}
