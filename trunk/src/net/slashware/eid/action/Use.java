package net.slashware.eid.action;

import net.slashie.serf.action.Action;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.player.DetectiveActor;

public class Use extends Action{

	private static final long serialVersionUID = 1L;

	public boolean needsItem(){
		return true;
    }

    public String getPromptItem(){
    	return "What do you want to use?";
	}
    
	public void execute(){
		DetectiveActor aPlayer = (DetectiveActor) performer;
		EIDItem item = (EIDItem) targetItem;
		switch (item.getItemType()){
		case WEAPON:
			if (aPlayer.getWeapon() != null){
				youMessage("You remove your "+aPlayer.getWeapon().getDescription()+" and wear your "+item.getDescription());
				aPlayer.addItem(aPlayer.getWeapon(), 1);
			} else {
				youMessage("You wear your "+item.getDescription());
			}
			aPlayer.reduceQuantityOf(item);
			aPlayer.setWeapon(item);
			break;
		case CLOTHING:
			if (aPlayer.getClothing() != null){
				youMessage("You remove your "+aPlayer.getClothing().getDescription()+" and wear your "+item.getDescription());
				aPlayer.addItem(aPlayer.getClothing(), 1);
			} else {
				youMessage("You wear your "+item.getDescription());
			}
			aPlayer.reduceQuantityOf(item);
			aPlayer.setClothing(item);
			break;
		case GADGET:
			break;
		case MISC:
			youMessage("You don't find a use for the "+item.getDescription());
			break;
		}
	}

	@Override
	public String getID() {
		return "Use";
	}
}
