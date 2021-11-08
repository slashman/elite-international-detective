package net.slashware.eid.action;

import java.util.List;

import net.slashie.serf.action.Action;
import net.slashie.serf.action.Actor;
import net.slashie.serf.level.AbstractCell;
import net.slashie.serf.level.AbstractFeature;
import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.sound.SFXManager;
import net.slashie.serf.ui.Effect;
import net.slashie.serf.ui.EffectFactory;
import net.slashie.serf.ui.UserInterface;
import net.slashie.utils.Line;
import net.slashie.utils.Position;
import net.slashware.eid.entity.EIDActor;
import net.slashware.eid.entity.NPC;
import net.slashware.eid.entity.level.SimpleLevelCell;
import net.slashware.eid.entity.player.DetectiveActor;

public class Fire extends Action {
	private EIDActor player;
	public boolean needsPosition(){
		return true;
	}

	private Position resolvePositionFromDirection(){
		return Position.add(performer.getPosition(), Action.directionToVariation(targetDirection));
	}
	
	
	
	public void execute(){
		if (performer == performer.getLevel().getPlayer())
			UserInterface.getUI().resetMessages();
		player = null;
		try {
			player = (EIDActor) performer;
		} catch (ClassCastException cce){
			return;
		}
		
		if (player.getWeapon() == null){
			youMessage("You have got no gun");
			return;
		}
		
		AbstractLevel aLevel = performer.getLevel();
		
		if (targetPosition == null)
			targetPosition = resolvePositionFromDirection();
		
		if (targetPosition.equals(performer.getPosition())){
			aLevel.addMessage("You shot upside");
			return;	
        }
		youMessage("You shoot your "+player.getWeapon().getDescription());
		theyMessage("The "+player.getDescription()+" shoots his "+player.getWeapon().getDescription());
		for (int i = 0; i < player.getWeapon().getShoots(); i++){
			shoot();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {

			}
		}
	}
	
	private void shoot() {
		AbstractLevel aLevel = performer.getLevel();
		SFXManager.play(player.getWeapon().getSfx());
		// Determine shoot depth
		int i = 0;
		Line path = new Line(performer.getPosition(), targetPosition);
		path.next();
		for (i=0; i<20; i++){
			Position destinationPoint = path.next();
			AbstractCell destinationCell = aLevel.getMapCell(destinationPoint);
			if (destinationCell == null || destinationCell.isSolid())
				break;
	        List<AbstractFeature> destinationFeature = aLevel.getFeaturesAt(destinationPoint);
 			if (destinationFeature != null && destinationFeature.size()>0 && destinationFeature.get(0).isDestroyable())
 				break;
			Actor targetMonster = performer.getLevel().getActorAt(destinationPoint);
			if (targetMonster != null)
				break;
		}
		Effect me = EffectFactory.getSingleton().createDirectedEffect(performer.getPosition(), targetPosition, player.getWeapon().getEffectId(), i);
		aLevel.addEffect(me);
				
		boolean hitsSomebody = false;
		path = new Line(performer.getPosition(), targetPosition);
		path.next();
		for (i=0; i<20; i++){
			Position destinationPoint = path.next();
        	List<AbstractFeature> destinationFeature = aLevel.getFeaturesAt(destinationPoint);
        	if (destinationFeature != null && destinationFeature.size()>0 && destinationFeature.get(0).isDestroyable()) {
        		hitsSomebody = true;
	        	youMessage("You hit the "+destinationFeature.get(0).getDescription());
			}

			EIDActor targetMonster = (EIDActor) performer.getLevel().getActorAt(destinationPoint);

			if (targetMonster != null){
				hitsSomebody = true;
				int attack = player.getWeapon().getAttack().roll();
				if (attack < 1)
					attack = 1;
				targetMonster.damageWithWeapon(attack);
			}
			
			SimpleLevelCell targetMapCell = (SimpleLevelCell) aLevel.getMapCell(destinationPoint);
			if (targetMapCell != null && targetMapCell.isSolid()){
				
				youMessage("You hit the "+targetMapCell.getShortDescription());
				hitsSomebody = true;
         	}
			
			if (hitsSomebody)
				break;
		}
		if (!hitsSomebody)
			youMessage("Your shoot goes wild");
	}

	public String getPromptPosition(){
		return "Aim your "+((EIDActor)performer).getWeapon().getDescription();
	}

	public Position getPosition(){
		return targetPosition;
	}


	public String getSFX(){
		return null;
	}

	public int getCost(){
		return 50;
	}
	
	public boolean canPerform(Actor a){
        return ((EIDActor)a).getWeapon() != null;
	}

	@Override
	public String getID() {
		return "Fire";
	}
}