package net.slashware.eid.entity;

import net.slashie.serf.action.AwareActor;
import net.slashie.serf.action.NullSelector;
import net.slashie.serf.ai.SimpleAI;
import net.slashie.serf.ui.UserInterface;
import net.slashie.utils.Util;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.action.Walk;

public class NPC extends AwareActor implements Cloneable{
	private static final long serialVersionUID = 1L;
	
	private String description;
	private String[] talkLines;
	private boolean isUnique;
	private String typeId;
	
	@Override
	public String getClassifierID() {
		return super.toString();
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public NPC(String typeId, String description, boolean isUnique, boolean nullSelector, String... talkLines) {
		super();
		this.typeId = typeId;
		setAppearanceId(typeId);
		this.description = description;
		this.isUnique = isUnique;
		if (nullSelector){
			selector = new NullSelector();
		} else {
			selector = new SimpleAI(null, new Walk());
		}
		this.talkLines = talkLines;
	}

	@Override
	public int getSightRange() {
		return 5;
	}
	
	@Override
	public void onPlayerBump() {
		if (talkLines.length > 0){
			m(says()+Util.randomElementOf(talkLines));
		}
	}
	
	private String says() {
		if (isUnique())
			return getDescription()+" says: XXX ";
		else
			return "The "+getDescription()+" says: XXX ";
	}

	private boolean isUnique() {
		return isUnique;
	}

	private void m(String string) {
		((EIDUserInterface)UserInterface.getUI()).showBlockingMessage(string);		
	}

	public String getTypeId() {
		return typeId;
	}
	
}
