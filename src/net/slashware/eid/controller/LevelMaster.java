package net.slashware.eid.controller;

import net.slashie.serf.game.Player;
import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.level.LevelMetaData;
import net.slashie.serf.levelGeneration.StaticPattern;
import net.slashie.utils.Position;
import net.slashware.eid.data.levels.Airport_Pattern;
import net.slashware.eid.data.levels.HQ_Pattern;
import net.slashware.eid.data.levels.Placeholder_Pattern;
import net.slashware.eid.entity.level.EIDLevel;

public class LevelMaster {

	public static AbstractLevel createLevel(LevelMetaData levelMetaData, Player p) {
		EIDLevel ret = null;
		String levelID = levelMetaData.getLevelID();
		if (levelID.equals("HQ")){
			ret = new EIDLevel();
			StaticPattern pattern = new HQ_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setDescription("Headquarters");
			ret.setMusicKey("HQ");
			ret.setID("HQ");
		}else if (levelID.equals("AIRPORT")){
			ret = new EIDLevel();
			
			StaticPattern pattern = new Airport_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setMusicKey("AIRPORT");
			ret.setID("AIRPORT");
		} else if (levelID.endsWith("WAREHOUSE")){
			ret = new EIDLevel();
			StaticPattern pattern = new Placeholder_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setMusicKey("WAREHOUSE");
			ret.setDescription("Warehouse");
			ret.setID(levelID);
		} else if (levelID.endsWith("MALL")){
			ret = new EIDLevel();
			StaticPattern pattern = new Placeholder_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setMusicKey("MALL");
			ret.setDescription("Shopping Mall");
			ret.setID(levelID);
		} else if (levelID.endsWith("POLICE")){
			ret = new EIDLevel();
			StaticPattern pattern = new Placeholder_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setMusicKey("POLICE");
			ret.setDescription("Police Station");
			ret.setID(levelID);			
		} else if (levelID.endsWith("HOUSE")){
			ret = new EIDLevel();
			StaticPattern pattern = new Placeholder_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setMusicKey("HOUSE");
			ret.setDescription("House");
			ret.setID(levelID);					
		}else if (levelID.endsWith("LIBRARY")){
			ret = new EIDLevel();
			StaticPattern pattern = new Placeholder_Pattern();
			EIDStaticGenerator generator = new EIDStaticGenerator();
			pattern.setup(generator);
			generator.createLevel(ret);
			ret.setDescription(pattern.getDescription());
			if (pattern.getUnleashers() != null){
				ret.setUnleashers(pattern.getUnleashers());
			}
			ret.setMusicKey("LIBRARY");
			ret.setDescription("Library");
			ret.setID(levelID);					
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
