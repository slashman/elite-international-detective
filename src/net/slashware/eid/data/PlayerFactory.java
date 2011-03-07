package net.slashware.eid.data;

import net.slashware.eid.EIDGame;
import net.slashware.eid.entity.DetectiveActor;
import net.slashware.eid.entity.Rank;

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
		return ret;
	}
}
