package net.slashware.eid.entity;

import java.util.List;
import java.util.Map;

import net.slashie.serf.baseDomain.AbstractItem;
import net.slashie.serf.game.Player;

public class DetectiveActor extends Player {
	public DetectiveActor() {
		super();
		setAppearanceId("DETECTIVE");
	}

	private static final long serialVersionUID = 1L;
	private Rank rank;
	private List<Gadget> gadgetsInventory;
	private Map<EquipmentSlot, Gadget> readiedGadgets;
	
	@Override
	public String getDescription() {
		return rank.getDescription() + " " + getName();
	}

	@Override
	public boolean canCarry(AbstractItem item, int quantity) {
		return false;
	}

	@Override
	public int getDarkSightRange() {
		return 5;
	}

	@Override
	public List<? extends AbstractItem> getEquippedItems() {
		return null;
	}

	@Override
	public String getSaveFilename() {
		return getName();
	}

	@Override
	public int getSightRange() {
		return 5;
	}

	@Override
	public String getClassifierID() {
		return "PLAYER";
	}

	public int getWalkCost() {
		return 50;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
}
