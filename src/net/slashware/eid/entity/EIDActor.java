package net.slashware.eid.entity;

import net.slashware.eid.entity.item.EIDItem;

public interface EIDActor {
	public EIDItem getWeapon();
	public EIDItem getClothing();
	public boolean wasSeen();
	public void damageWithWeapon(EIDActor damager, int attack);
	public String getDescription();
}
