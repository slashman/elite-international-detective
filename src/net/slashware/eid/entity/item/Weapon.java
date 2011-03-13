package net.slashware.eid.entity.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slashie.utils.Util;
import net.slashie.utils.roll.Roll;

public class Weapon extends EIDItem{
	private static final long serialVersionUID = 1L;
	private static Map<Integer, List<Weapon>> tierMap = new HashMap<Integer, List<Weapon>>(); 

	public Weapon(int tier, String itemId, String description, Roll attack, int range, int spread, int shoots, String effectId, String sfx) {
		super(itemId, description, ItemType.WEAPON);
		super.attack = attack;
		super.range = range;
		super.spread = spread;
		super.effectId = effectId;
		super.sfx = sfx;
		super.shoots = shoots;
		addToTierRegistry(tier, this);
	}

	private static void addToTierRegistry(int tier, Weapon weapon) {
		List<Weapon> tierList = tierMap.get(tier);
		if (tierList == null){
			tierList = new ArrayList<Weapon>();
			tierMap.put(tier, tierList);
		}
		tierList.add(weapon);
	}
	
	public static Weapon getWeaponByTier(int tier){
		return (Weapon) Util.randomElementOf(tierMap.get(tier));
	}
	
}
