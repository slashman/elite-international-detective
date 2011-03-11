package net.slashware.eid.data.levels;

import net.slashie.serf.level.Unleasher;
import net.slashie.serf.levelGeneration.StaticPattern;

public class Placeholder_Pattern extends StaticPattern {

	public Placeholder_Pattern () {
		
		this.cellMap = new String [][]{{
			"####################################################",
			"#..................................................#",
			"#..................................................#",
			"#..................................................#",
			"#..................................................#",
			"#..................................................#",
			"#........................S.........................#",
			"#..................................................#",
			"#..................................................#",
			"#..................................................#",
			"#..................................................#",
			"####################################################",
			
		}};
		
		charMap.put(".", "FLOOR");
		charMap.put("#", "BLUE_WALL");
		charMap.put("S", "FLOOR EXIT _START");
		unleashers = new Unleasher[]{};
	}

	@Override
	public String getDescription() {
		return "Placeholder";
	}

}
