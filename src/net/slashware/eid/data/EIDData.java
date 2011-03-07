package net.slashware.eid.data;

import net.slashie.serf.level.AbstractCell;
import net.slashie.serf.ui.AppearanceFactory;
import net.slashware.eid.entity.EIDItem;
import net.slashware.eid.entity.Gadget;
import net.slashware.eid.entity.NPC;
import net.slashware.eid.entity.SimpleLevelCell;

public class EIDData {
	public static AbstractCell[] getCellDefinitions (){
		return new AbstractCell[]{
			new SimpleLevelCell("FLOOR", "Floor", false, false),
			new SimpleLevelCell("WALL", "Wall", true, true)
		};
	}
	
	public static EIDItem[] getItemDefinitions(){
		return new EIDItem[]{
			new Gadget("BERGMANN_MP18", "Bergmann MP18")
		};
		
	}

	public static NPC[] getNPCs() {
		return new NPC[]{
			new NPC("JEFF","Boss Jeff",true,true,"Remember, we are working on the honor basis", "This gun is my welcome gift. Good luck!", "Kill only as a last resource"),
			new NPC("AGENT","Detective",false,false,"They have pulled another caper!", "Where's the coffee machine?", "Welcome to the agency, rookie", "I need some tickets...")
		};
	}
}
