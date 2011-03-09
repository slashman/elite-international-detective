package net.slashware.eid.ui.console;

import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.serf.ui.consoleUI.effects.CharBeamMissileEffect;
import net.slashie.serf.ui.consoleUI.effects.CharDirectionalMissileEffect;
import net.slashie.serf.ui.consoleUI.effects.CharEffect;

public class CharEffects {
	private CharEffect [] effects = new CharEffect[]{
		new CharDirectionalMissileEffect("MACHINE_GUN", "\\|/--/|\\", ConsoleSystemInterface.WHITE, 10),
		new CharBeamMissileEffect("SHOTGUN", "Oo.o", ConsoleSystemInterface.GRAY, 20),

	};

	public CharEffect[] getEffects() {
		return effects;
	}
}
