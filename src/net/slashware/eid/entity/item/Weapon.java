package net.slashware.eid.entity.item;

import net.slashie.utils.roll.Roll;

public class Weapon extends EIDItem{
	private static final long serialVersionUID = 1L;

	public Weapon(String itemId, String description, Roll attack, int range, int spread, int shoots, String effectId, String sfx) {
		super(itemId, description, ItemType.WEAPON);
		super.attack = attack;
		super.range = range;
		super.spread = spread;
		super.effectId = effectId;
		super.sfx = sfx;
		super.shoots = shoots;
	}
	
}
