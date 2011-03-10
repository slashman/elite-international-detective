package net.slashware.eid.entity;

import java.util.ArrayList;

import net.slashie.serf.action.ActionSelector;
import net.slashie.serf.action.AwareActor;
import net.slashie.serf.action.NullSelector;
import net.slashie.serf.ai.RangedActionSpec;
import net.slashie.serf.ai.SimpleAI;
import net.slashie.serf.ui.UserInterface;
import net.slashie.utils.Util;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.action.Walk;
import net.slashware.eid.data.ItemFactory;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.level.EIDLevel;

public class NPC extends AwareActor implements Cloneable, EIDActor{
	private static final long serialVersionUID = 1L;
	private SimpleAI hostileSelector;
	private String description;
	private String[] talkLines;
	private boolean isUnique;
	private String typeId;
	
	private int luckyPoints;
	private String weaponId;
	private String armorId;
	private EIDItem weapon;
	private EIDItem clothing;
	private boolean isHostile;
	
	public boolean isHostile() {
		return isHostile;
	}

	public void setHostile(boolean isHostile) {
		this.isHostile = isHostile;
		if (isHostile){
			hostileSelector = new SimpleAI(getLevel().getPlayer(), new Walk());
			hostileSelector.setRangedActions(fireSpec);
			setSelector(hostileSelector);
		}
	}

	@Override
	public String getClassifierID() {
		return super.toString();
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	private final static ArrayList<RangedActionSpec> fireSpec = new ArrayList<RangedActionSpec>();
	static {
		RangedActionSpec e = new RangedActionSpec("Fire", 10, 100, "","");
		fireSpec.add(e);
	}
	public NPC(String typeId, String description, boolean isUnique, boolean nullSelector, String weaponId, String armorId, int luckyPoints, String... talkLines) {
		super();
		this.typeId = typeId;
		setAppearanceId(typeId);
		this.description = description;
		this.isUnique = isUnique;
		if (nullSelector){
			selector = new NullSelector();
		} else {
			selector = new SimpleAI(null, new Walk());
		}
		this.weaponId = weaponId;
		this.armorId = armorId;
		this.talkLines = talkLines;
		this.luckyPoints = luckyPoints;
	}
	
	public void arm(){
		if (weaponId != null)
			weapon = ItemFactory.createItem(weaponId);
		if (armorId != null)
			clothing = ItemFactory.createItem(armorId);
	}

	@Override
	public int getSightRange() {
		return 5;
	}
	
	@Override
	public void onPlayerBump() {
		if (talkLines.length > 0){
			m(says()+Util.randomElementOf(talkLines));
		}
	}
	
	private String says() {
		if (isUnique())
			return getDescription()+" says: XXX ";
		else
			return "The "+getDescription()+" says: XXX ";
	}

	private boolean isUnique() {
		return isUnique;
	}

	private void m(String string) {
		((EIDUserInterface)UserInterface.getUI()).showBlockingMessage(string);		
	}

	public String getTypeId() {
		return typeId;
	}


	private String [] MISS_MESSAGES= new String []{
			"is almost hit!",
			"almost dodges the bullet!",
			"leaps thru the hit!",
			
		};
		
	private String [] HIT_MESSAGES= new String []{
			"is hit right in his chest",
			"is bathed in his own blood!",
			"arm is hit!",
			"leg is severed"
		};
		
	public void damageWithWeapon(int attack) {
		luckyPoints -= attack;
		if (luckyPoints > 0) {
			getLevel().addMessage("The "+getDescription()+" "+Util.randomElementOf(MISS_MESSAGES), getPosition());
		} else {
			getLevel().addMessage("The "+getDescription()+" "+Util.randomElementOf(HIT_MESSAGES), getPosition());
			((EIDLevel)getLevel()).addBlood(getPosition());
			if (Util.chance(50)){
				getLevel().addMessage("The "+getDescription()+" drops dead.", getPosition());
				die();
			}
		}
		setHostile(true);
		
	}

	public EIDItem getWeapon() {
		return weapon;
	}

	public EIDItem getClothing() {
		return clothing;
	}
	
}
