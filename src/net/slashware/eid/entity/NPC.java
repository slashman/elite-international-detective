package net.slashware.eid.entity;

import java.util.ArrayList;

import net.slashie.serf.action.ActionSelector;
import net.slashie.serf.action.AwareActor;
import net.slashie.serf.action.NullSelector;
import net.slashie.serf.ai.RangedActionSpec;
import net.slashie.serf.ai.SimpleAI;
import net.slashie.serf.ui.UserInterface;
import net.slashie.utils.Util;
import net.slashware.eid.EIDGame;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.action.Walk;
import net.slashware.eid.controller.mission.MissionGenerator;
import net.slashware.eid.data.ItemFactory;
import net.slashware.eid.data.NPCFactory;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.level.EIDLevel;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.mission.Mission;
import net.slashware.eid.entity.player.DetectiveActor;
import net.slashware.eid.entity.player.Rank;
import net.slashware.eid.ui.EIDDisplay;

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
	private boolean interrogated = false;
	
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
	public NPC(String typeId, String description, boolean isUnique, ActionSelector defaultSelector, String weaponId, String armorId, int luckyPoints, String... talkLines) {
		super();
		this.typeId = typeId;
		setAppearanceId(typeId);
		this.description = description;
		this.isUnique = isUnique;
		selector = defaultSelector;
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
		if (getTypeId().equals("INFORMANT") ||
				getTypeId().equals("SUSPECT") ||
				getTypeId().equals("WITNESS")
				){
			if (interrogated ){
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You have alredy interrogated "+uniDesc());
			} else {
				((DetectiveActor)getLevel().getPlayer()).getCurrentMission().doInternationalIntelligence(((DetectiveActor)getLevel().getPlayer()).getLocation());
				String clue = ((DetectiveActor)getLevel().getPlayer()).getCurrentMission().getClue();
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("You interrogate "+uniDesc()+", revealing the following clue for the location of the criminal: XXX "+clue);
				((DetectiveActor)getLevel().getPlayer()).addClue(clue);
				interrogated = true;
			}
		}
		
		if (getTypeId().equals("JEFF")){
			DetectiveActor detective = (DetectiveActor)getLevel().getPlayer();
			if (detective.getCurrentMission().isCriminalKilled()){
				m(says()+"Congratulations "+detective.getDescription()+"! You have completed your mission!.");
				m(says()+"Thanks to your help, the people of "+detective.getCurrentMission().getCrime().getLocation().getCountryName()+" will live a peaceful life.");
				m(says()+detective.getCurrentMission().getCrime().getCriminal().getDescription()+" was found to be the culprit of "+detective.getCurrentMission().getCrime().getTitle()+", he shall pay for it in the afterlife");
				m(says()+"The InterSleuth agency thanks you for your work on this case.");
				Rank currentRank = detective.getRank();
				detective.addCompletedMission(detective.getCurrentMission());
				Rank newRank = detective.getRank();
				if (newRank != currentRank){
					m(says()+"Good job "+detective.getName()+", you have earned a promotion.");
					m(says()+"Your new rank is "+newRank.getDescription());
				}
				m(says()+"I hope you are ready for the next case!");
				Mission nextMission = MissionGenerator.generateMission(((EIDGame)detective.getGame()).getGameTime().getTime(), detective.getRank().getDifficulty(), detective);
				((EIDGame)detective.getGame()).getGameTime().setTime(nextMission.getMissionStart());
				detective.setCurrentMission(nextMission);
				EIDDisplay.thus.showMission(detective, nextMission);
				return;
			} 
		} 
		
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
	
	private String uniDesc(){
		if (isUnique())
			return getDescription();
		else
			return "The "+getDescription();
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
		if (wannaDie())
			return;	
		luckyPoints -= attack;
		if (luckyPoints > 0) {
			getLevel().addMessage(uniDesc()+" "+Util.randomElementOf(MISS_MESSAGES), getPosition());
		} else {
			getLevel().addMessage(uniDesc()+" "+Util.randomElementOf(HIT_MESSAGES), getPosition());
			((EIDLevel)getLevel()).addBlood(getPosition());
			if (Util.chance(50)){
				getLevel().addMessage(uniDesc()+" drops dead.", getPosition());
				if (getTypeId().equals(((DetectiveActor)getLevel().getPlayer()).getCurrentMission().getCrime().getCriminal().getNPCID())){
					// Mission complete!
					((DetectiveActor)getLevel().getPlayer()).getCurrentMission().criminalKilled();
				}
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

	public void setWeapon(EIDItem weapon) {
		this.weapon = weapon;
	}
	
}
