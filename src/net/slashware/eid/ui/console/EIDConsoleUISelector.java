package net.slashware.eid.ui.console;

import net.slashie.serf.ui.consoleUI.ConsoleUISelector;
import net.slashie.serf.action.Actor;

public class EIDConsoleUISelector extends ConsoleUISelector{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int onActorStumble(Actor actor) {
		return 0;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
