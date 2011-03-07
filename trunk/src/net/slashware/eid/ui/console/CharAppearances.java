package net.slashware.eid.ui.console;

import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.serf.ui.consoleUI.CharAppearance;

public class CharAppearances {
	public static CharAppearance[] getCharAppearances(){
		return new CharAppearance[]{
			//Expeditions
			new CharAppearance("DETECTIVE", '@', ConsoleSystemInterface.RED),
			new CharAppearance("FLOOR", '.', ConsoleSystemInterface.CYAN),
			new CharAppearance("WALL", '#', ConsoleSystemInterface.BLUE),
			new CharAppearance("BERGMANN_MP18", '/', ConsoleSystemInterface.BROWN),
			new CharAppearance("JEFF", '@', ConsoleSystemInterface.LEMON),
			new CharAppearance("AGENT", '@', ConsoleSystemInterface.GRAY)
		};
	};
	
}
