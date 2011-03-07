package net.slashware.eid.controller;

import net.slashie.serf.game.Player;
import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.level.Dispatcher;
import net.slashie.serf.level.LevelMetaData;
import net.slashie.serf.levelGeneration.StaticGenerator;
import net.slashie.serf.levelGeneration.StaticPattern;
import net.slashie.simpleRL.data.patterns.AmuletRoom_Pattern;
import net.slashie.simpleRL.data.patterns.Town_Pattern;
import net.slashie.simpleRL.domain.world.Level;
import net.slashie.simpleRL.procedural.level.CaveLevelGenerator;
import net.slashie.utils.Position;
import net.slashware.eid.data.levels.HQ_Pattern;

public class LevelMaster {

	public static AbstractLevel createLevel(LevelMetaData levelMetaData, Player p) {
		Level ret = null;
		String levelID = levelMetaData.getLevelID();
		if (levelID.equals("HQ")){
			ret = new Level();
			
			StaticPattern pattern = new HQ_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setMusicKey("HQ");
			ret.setID("HQ");
		}
		
		if (ret.getExitFor("_BACK") != null){
			Position pos = ret.getExitFor("_BACK");
			ret.removeExit("_BACK");
			ret.addExit(pos, levelMetaData.getExit("_BACK"));
			
		}
		
		if (ret.getExitFor("_NEXT") != null){
			Position pos = ret.getExitFor("_NEXT");
			ret.removeExit("_NEXT");
			ret.addExit(pos, levelMetaData.getExit("_NEXT"));
		}
		
		return ret;
	}

}
