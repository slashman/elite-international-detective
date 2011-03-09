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
			 
			new CharAppearance("BERGMANN_MP18", '/', ConsoleSystemInterface.BROWN),
			new CharAppearance("THOMPSON_M1921", '/', ConsoleSystemInterface.GRAY),
			new CharAppearance("WINCHESTHER_M12", '\\', ConsoleSystemInterface.BROWN),
			
			new CharAppearance("GREEN_RAINCOAT", 'T', ConsoleSystemInterface.GREEN),
			new CharAppearance("DARK_BLUE_SUIT", 'T', ConsoleSystemInterface.BLUE),
			new CharAppearance("BLACK_RAINCOAT", 'T', ConsoleSystemInterface.GRAY),
			new CharAppearance("DARK_BLACK_SUIT", 'T', ConsoleSystemInterface.GRAY),
			
			new CharAppearance("REMOTE_COM", '?', ConsoleSystemInterface.GRAY),
			
			new CharAppearance("JEFF", '@', ConsoleSystemInterface.LEMON),
			new CharAppearance("AGENT", '@', ConsoleSystemInterface.GRAY)
		};
	};

	
}
