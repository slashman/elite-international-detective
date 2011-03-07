package net.slashware.eid.entity;

public class Gadget extends EIDItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Gadget(String itemId, String description) {
		super(itemId, description);
	}
	
	private EquipmentSlot validEquipmentSlot;

	public EquipmentSlot getValidEquipmentSlot() {
		return validEquipmentSlot;
	}

	public void setValidEquipmentSlot(EquipmentSlot validEquipmentSlot) {
		this.validEquipmentSlot = validEquipmentSlot;
	}

	@Override
	public Object clone() {
		return super.clone();
	}
}
