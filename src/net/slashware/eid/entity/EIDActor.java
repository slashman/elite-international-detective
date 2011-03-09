package net.slashware.eid.entity;

import net.slashware.eid.entity.item.EIDItem;

public interface EIDActor {
	public EIDItem getWeapon();
	public EIDItem getClothing();
	public boolean wasSeen();
	public void damageWithWeapon(int attack);
	public String getDescription();
}
