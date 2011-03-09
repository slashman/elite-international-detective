package net.slashware.eid.data;

import net.slashie.serf.level.AbstractCell;
import net.slashie.utils.roll.Roll;
import net.slashware.eid.entity.NPC;
import net.slashware.eid.entity.item.Clothing;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.item.Gadget;
import net.slashware.eid.entity.item.Weapon;
import net.slashware.eid.entity.level.Landmark;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.level.SimpleLevelCell;

public class EIDData {
	public static AbstractCell[] getCellDefinitions (){
		return new AbstractCell[]{
			new SimpleLevelCell("FLOOR", "Floor", false, false),
			new SimpleLevelCell("WALL", "Wall", true, true)
		};
	}
	
	public static EIDItem[] getItemDefinitions(){
		return new EIDItem[]{
			new Weapon("BERGMANN_MP18", "Bergmann MP18", new Roll(2,4), 5, 3, 5, "MACHINE_GUN","wav/Gun_Shot2.wav"),
			new Weapon("THOMPSON_M1921", "Thompson M1921", new Roll(3,6), 5, 3, 6, "MACHINE_GUN","wav/Gun_Shot2.wav"),
			new Weapon("WINCHESTHER_M12", "Winchester M12", new Roll(3,7), 5, 3, 1, "SHOTGUN","wav/Shotgun1.wav"),
			new Clothing("GREEN_RAINCOAT", "Cheap Green Raincoat",  new Roll(1,1), 3, 1),
			new Clothing("BLACK_RAINCOAT", "Black Raincoat",  new Roll(1,1), 3, 1),
			new Clothing("DARK_BLUE_SUIT", "Dark Blue Suit",  new Roll(1,1), 2, 3),
			new Clothing("DARK_BLACK_SUIT", "Dark Black Suit",  new Roll(1,1), 2, 3),
			new Gadget("REMOTE_COM", "Remote Communication Device")
		};
		
	}

	public static NPC[] getNPCs() {
		return new NPC[]{
			new NPC("JEFF","Boss Jeff",true,true,"THOMPSON_M1921","DARK_BLACK_SUIT",80,"Remember, we are working on the honor basis", "This gun is my welcome gift. Good luck!", "Kill only as a last resource"),
			new NPC("AGENT","Detective",false,false,"WINCHESTHER_M12", "BLACK_RAINCOAT",40,"They have pulled another caper!", "Where's the coffee machine?", "Welcome to the agency, rookie", "I need some tickets...")
		};
	}

	public static Location[] getLocations(){
		return new Location[]{
			new Location("SF-US", "United States", "San Francisco", "San Francisco is the city of... etc", "american",
				new Landmark [] {
					new Landmark("The Golden Gate Bridge", "one of the most heavily used bridges in the world")
				}
			),
			new Location("SF-CA", "Canada", "Vancouver", "Vancouver, widely known for being Vancouver... etc", "canadian",
				new Landmark [] {
					new Landmark("The Canada Place", "the main cruise ship terminal for the region")
				}	
			)
		};
	}
}
