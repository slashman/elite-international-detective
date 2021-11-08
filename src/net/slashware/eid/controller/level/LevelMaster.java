package net.slashware.eid.controller.level;

import net.slashie.serf.game.Player;
import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.level.LevelMetaData;
import net.slashie.serf.levelGeneration.StaticPattern;
import net.slashie.utils.Position;
import net.slashie.utils.Util;
import net.slashware.eid.data.levels.Airport_Pattern;
import net.slashware.eid.data.levels.HQ_Pattern;
import net.slashware.eid.data.levels.Placeholder_Pattern;
import net.slashware.eid.entity.level.EIDLevel;
import net.slashware.eid.entity.player.DetectiveActor;

public class LevelMaster {
	private final static UrbanZoneGenerator urbanZoneGenerator = new UrbanZoneGenerator();

	public static AbstractLevel createLevel(LevelMetaData levelMetaData, Player p) {
		EIDLevel ret = null;
		if (levelMetaData instanceof UrbanLevelMetadata){
			UrbanLevelMetadata ulm = (UrbanLevelMetadata) levelMetaData;
			int difficulty = ((DetectiveActor)p).getCurrentMission().getDifficulty();
			ret = urbanZoneGenerator.generateUrbanZone(
					ulm.getTarget(), 80, 25, 5, 0, (DetectiveActor)p, difficulty);
			ret.setDescription("Near "+ulm.getTarget().getName());
			ret.setMusicKey("R"+Util.rand(0, 4));
			ret.setID(levelMetaData.getLevelID());
		} else {
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
			}
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
