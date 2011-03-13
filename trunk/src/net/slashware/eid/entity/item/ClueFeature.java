package net.slashware.eid.entity.item;

import net.slashie.serf.action.Actor;
import net.slashie.serf.level.AbstractFeature;
import net.slashie.serf.ui.UserInterface;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.entity.player.DetectiveActor;

public class ClueFeature extends AbstractFeature{
	public ClueFeature() {
		super();
		setAppearanceId("CLUE");
	}
	@Override
	public String getClassifierID() {
		return "CLUE";
	}

	@Override
	public String getDescription() {
		return "Clue";
	}
	
	@Override
	public void onStep(Actor a) {
		super.onStep(a);
		((DetectiveActor)getLevel().getPlayer()).getCurrentMission().doInternationalIntelligence(((DetectiveActor)getLevel().getPlayer()).getLocation());
		String clue = ((DetectiveActor)getLevel().getPlayer()).getCurrentMission().getClue();
		((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You examine the evidence, revealing the following clue for the location of the criminal: XXX "+clue);
		((DetectiveActor)getLevel().getPlayer()).addClue(clue);
		getLevel().scheduleFeatureDestruction(this);
		
	}

}
