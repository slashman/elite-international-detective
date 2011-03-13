package net.slashware.eid.data;

import net.slashware.eid.EIDGame;
import net.slashware.eid.entity.player.DetectiveActor;
import net.slashware.eid.entity.player.Rank;

public class PlayerFactory {
	public enum Sex {
		MALE,
		FEMALE
	}
	public static DetectiveActor generateDetective(String name, Sex sex, EIDGame game){
		DetectiveActor ret = new DetectiveActor();
		ret.setName(name);
		ret.setRank(Rank.ROOKIE);
		ret.setGame(game);
		ret.setClothing(ItemFactory.createItem("GREEN_RAINCOAT"));
		ret.setWeapon(ItemFactory.createItem("BERGMANN_MP18"));
		//ret.setWeapon(ItemFactory.createItem("WINCHESTHER_M12"));
		ret.addItem(ItemFactory.createItem("DARK_BLUE_SUIT"), 1);
		ret.addItem(ItemFactory.createItem("REMOTE_COM"), 1);
		ret.setLuckyPoints(20);
		ret.setStamina(100);
		return ret;
	}
}
