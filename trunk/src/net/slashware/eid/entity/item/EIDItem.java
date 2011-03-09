package net.slashware.eid.entity.item;

import net.slashie.serf.baseDomain.AbstractItem;
import net.slashie.utils.roll.Roll;
import net.slashware.eid.entity.player.EquipmentSlot;

public abstract class EIDItem extends AbstractItem implements Cloneable{
	private static final long serialVersionUID = 1L;

	private String itemId;
	private String description;
	private ItemType itemType;
	
	protected Roll attack;
	protected Roll armor;
	protected int range;
	protected int spread;
	protected int coolness;
	protected int disguise;
	protected String effectId;
	protected String sfx;
	protected int shoots;
	
	private EquipmentSlot validEquipmentSlot;

	
	public EquipmentSlot getValidEquipmentSlot() {
		return validEquipmentSlot;
	}

	public ItemType getItemType() {
		return itemType;
	}

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

	public EIDItem(String itemId, String description, ItemType itemType) {
		super(itemId);
		this.itemId = itemId;
		this.description = description;
		this.itemType = itemType;
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}

	public Roll getAttack() {
		return attack;
	}

	public Roll getArmor() {
		return armor;
	}

	public int getRange() {
		return range;
	}

	public int getSpread() {
		return spread;
	}

	public int getCoolness() {
		return coolness;
	}

	public int getDisguise() {
		return disguise;
	}

	public String getEffectId() {
		return effectId;
	}

	public String getSfx() {
		return sfx;
	}

	public int getShoots() {
		return shoots;
	}
}
