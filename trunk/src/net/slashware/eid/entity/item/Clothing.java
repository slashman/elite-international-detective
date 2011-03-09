package net.slashware.eid.entity.item;

import net.slashie.utils.roll.Roll;

public class Clothing extends EIDItem {

	public Clothing(String itemId, String description, Roll armor, int coolness, int disguise) {
		super(itemId, description, ItemType.CLOTHING);
		super.armor = armor;
		super.coolness = coolness;
		super.disguise = disguise;
	}

}
