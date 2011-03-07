package net.slashware.eid.entity;

import net.slashie.serf.baseDomain.AbstractItem;

public class EIDItem extends AbstractItem implements Cloneable{
	private static final long serialVersionUID = 1L;

	private String itemId;
	private String description;
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getFullID() {
		return itemId;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public EIDItem(String itemId, String description) {
		super(itemId);
		this.itemId = itemId;
		this.description = description;
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}
}
