package net.slashware.eid.entity;

import net.slashie.serf.level.AbstractCell;
import net.slashie.serf.ui.AppearanceFactory;

public class SimpleLevelCell extends AbstractCell{
	private static final long serialVersionUID = 1L;
	
	public SimpleLevelCell(String pID, String sdes, boolean isSolid, boolean isOpaque){
		super(pID, sdes, sdes, AppearanceFactory.getAppearanceFactory().getAppearance(pID), isSolid, isOpaque);
	}

}
