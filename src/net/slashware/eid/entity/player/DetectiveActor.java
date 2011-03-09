package net.slashware.eid.entity.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.slashie.serf.baseDomain.AbstractItem;
import net.slashie.serf.game.Equipment;
import net.slashie.serf.game.Player;
import net.slashie.serf.ui.UserInterface;
import net.slashie.utils.Util;
import net.slashware.eid.entity.EIDActor;
import net.slashware.eid.entity.item.Ammo;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.item.ItemType;
import net.slashware.eid.entity.level.EIDLevel;

public class DetectiveActor extends Player implements EIDActor {
	public DetectiveActor() {
		super();
		setAppearanceId("DETECTIVE");
		setFlag("KEEPMESSAGES", true);
		lethality = Lethality.INTIMIDATE;
		walkingMode = WalkingMode.WALKING_COOL;
	}

	private static final long serialVersionUID = 1L;
	private Rank rank;
	private EIDItem weapon;
	private EIDItem clothing;
	private int luckyPoints;
	private int luckyPointsMax;
	private int stamina;
	private int staminaMax;
	private Lethality lethality;
	private WalkingMode walkingMode;
	
	public int getLuckyPoints() {
		return luckyPoints;
	}

	public void setLuckyPoints(int luckyPoints) {
		this.luckyPoints = luckyPoints;
	}

	public int getLuckyPointsMax() {
		return luckyPointsMax;
	}

	public void setLuckyPointsMax(int luckyPointsMax) {
		this.luckyPointsMax = luckyPointsMax;
	}

	@Override
	public String getDescription() {
		return rank.getDescription() + " " + getName();
	}

	@Override
	public boolean canCarry(AbstractItem item, int quantity) {
		if (item instanceof Ammo){
			return false;
		} else if (item instanceof EIDItem){
			return getInventory().size() + quantity < getCarryCapacity();
		} else {
			return false;
		}
	}

	private int getCarryCapacity() {
		return 20;
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
		return walkingMode.getTimeCostPerStep();
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public List<Equipment> getInventory(ItemType itemType) {
		List<Equipment> allEquipment = getInventory();
		List<Equipment> ret = new ArrayList<Equipment>();
		for (Equipment e: allEquipment){
			if (((EIDItem)e.getItem()).getItemType() == itemType){
				ret.add(e);
			}
		}
		return ret;
	}

	public EIDItem getWeapon() {
		return weapon;
	}

	public void setWeapon(EIDItem weapon) {
		this.weapon = weapon;
	}

	public EIDItem getClothing() {
		return clothing;
	}

	public void setClothing(EIDItem clothing) {
		this.clothing = clothing;
	}
	
	private String [] MISS_MESSAGES= new String []{
		"You are almost hit!",
		"The bullet passes near you!",
		"The bullet hits just behind you!",
		"You can hear the bullet fly pass your ears!"
	};
	
	private String [] HIT_MESSAGES= new String []{
			"Your are hit right in your chest",
			"You are bathed in your own blood!",
			"Your arm is hit!",
			"Your leg is severed"
		};

	@Override
	public void damageWithWeapon(int attack) {
		luckyPoints -= attack;
		if (luckyPoints < 0){
			luckyPoints = 0;
		}
		if (luckyPoints > 0) {
			getLevel().addMessage(Util.randomElementOf(MISS_MESSAGES), getPosition());
		} else {
			getLevel().addMessage(Util.randomElementOf(HIT_MESSAGES), getPosition());
			((EIDLevel)getLevel()).addBlood(getPosition());
			if (Util.chance(50)){
				getLevel().addMessage("You drop dead.", getPosition());
				UserInterface.getUI().refresh();
				informPlayerEvent (DEATH);
			}
		}	
	}

	public Lethality getLethality() {
		return lethality;
	}

	public void setLethality(Lethality lethality) {
		this.lethality = lethality;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getStaminaMax() {
		return staminaMax;
	}

	public void setStaminaMax(int staminaMax) {
		this.staminaMax = staminaMax;
	}

	public WalkingMode getWalkingMode() {
		return walkingMode;
	}

	public void setWalkingMode(WalkingMode walkingMode) {
		this.walkingMode = walkingMode;
	}

	public void useStamina() {
		stamina -= getWalkingMode().getStaminaCost();
		if (stamina < 0)
			stamina = 0;
	}

	public void recoverStamina() {
		stamina +=2;
		if (stamina > staminaMax)
			stamina = staminaMax;
	}
	
}
