package net.slashware.eid.ui.console;

import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.serf.ui.consoleUI.CharAppearance;

public class CharAppearances {
	public static CharAppearance[] getCharAppearances(){
		return new CharAppearance[]{
			//Expeditions
			new CharAppearance("DETECTIVE", '@', ConsoleSystemInterface.GREEN),
			new CharAppearance("FLOOR", '.', ConsoleSystemInterface.GRAY),
			new CharAppearance("WALL", '#', ConsoleSystemInterface.RED),
			new CharAppearance("BLUE_WALL", '#', ConsoleSystemInterface.TEAL),
			new CharAppearance("V_GLASS", '|', ConsoleSystemInterface.CYAN),
			new CharAppearance("H_GLASS", '-', ConsoleSystemInterface.CYAN),
			new CharAppearance("DESK", '=', ConsoleSystemInterface.BROWN),
			new CharAppearance("DOOR", '/', ConsoleSystemInterface.BROWN),
			new CharAppearance("BAND", '>', ConsoleSystemInterface.GRAY),
			
			new CharAppearance("STREET", ' ', ConsoleSystemInterface.GRAY),
			new CharAppearance("WALKWAY", '.', ConsoleSystemInterface.GRAY),
			new CharAppearance("STREET_V_BAR", '|', ConsoleSystemInterface.YELLOW),
			new CharAppearance("STREET_H_BAR", '-', ConsoleSystemInterface.YELLOW),
			new CharAppearance("BUILDING_WALL", '#', ConsoleSystemInterface.TEAL),
			new CharAppearance("BLIND_DOOR", '/', ConsoleSystemInterface.BROWN),
			 
			new CharAppearance("BERGMANN_MP18", '/', ConsoleSystemInterface.BROWN),
			new CharAppearance("THOMPSON_M1921", '/', ConsoleSystemInterface.GRAY),
			new CharAppearance("WINCHESTHER_M12", '\\', ConsoleSystemInterface.BROWN),
			
			new CharAppearance("GREEN_RAINCOAT", 'T', ConsoleSystemInterface.GREEN),
			new CharAppearance("DARK_BLUE_SUIT", 'T', ConsoleSystemInterface.BLUE),
			new CharAppearance("BLACK_RAINCOAT", 'T', ConsoleSystemInterface.GRAY),
			new CharAppearance("DARK_BLACK_SUIT", 'T', ConsoleSystemInterface.GRAY),
			new CharAppearance("RED_DRESS", 'X', ConsoleSystemInterface.RED),
			
			new CharAppearance("REMOTE_COM", '?', ConsoleSystemInterface.GRAY),
			
			new CharAppearance("JEFF", '@', ConsoleSystemInterface.LEMON),
			new CharAppearance("AGENT", '@', ConsoleSystemInterface.GRAY),
			new CharAppearance("LALI", '@', ConsoleSystemInterface.RED),
			new CharAppearance("AIRPORT_LADY", 't', ConsoleSystemInterface.PURPLE),
			new CharAppearance("AIRPORT_GUY", 't', ConsoleSystemInterface.GREEN),
			new CharAppearance("INFORMANT", '@', ConsoleSystemInterface.GREEN),
			new CharAppearance("CRIMINAL", '@', ConsoleSystemInterface.RED),
			new CharAppearance("SUSPECT", '@', ConsoleSystemInterface.WHITE),
			new CharAppearance("COP", '@', ConsoleSystemInterface.BLUE),
			new CharAppearance("LEADER", '@', ConsoleSystemInterface.GRAY),
			new CharAppearance("WITNESS", 't', ConsoleSystemInterface.PURPLE),
			new CharAppearance("CIVILIAN", 't', ConsoleSystemInterface.GREEN),
			
			new CharAppearance("KORNEL_SANDIEGO", 'K', ConsoleSystemInterface.RED),
			
			new CharAppearance("CLUE", '?', ConsoleSystemInterface.WHITE),

		};
	};

	
}
