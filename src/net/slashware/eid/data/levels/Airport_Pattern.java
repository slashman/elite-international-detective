package net.slashware.eid.data.levels;

import net.slashie.serf.level.Unleasher;
import net.slashie.serf.levelGeneration.StaticPattern;

public class Airport_Pattern extends StaticPattern {

	public Airport_Pattern () {
		
		this.cellMap = new String [][]{{
			"####################################################",
			"#>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>#",
			"....................................................",
			"#...........l................l...............l.....#",
			"#==================================================#",
			"....................................................",
			".........................S...................g......",
			"........g...........................................",
			"..........................g.........................",
			"....................................................",
			"....................................................",
			
		}};
		
		charMap.put(".", "FLOOR");
		charMap.put("=", "DESK");
		charMap.put("/", "DOOR");
		charMap.put("#", "BLUE_WALL");
		charMap.put(">", "BAND");
		charMap.put("S", "FLOOR EXIT _START");
		charMap.put("l", "FLOOR NPC AIRPORT_LADY");
		charMap.put("g", "FLOOR NPC AIRPORT_GUY");
		unleashers = new Unleasher[]{};
	}

	@Override
	public String getDescription() {
		return "Airport";
	}

}
