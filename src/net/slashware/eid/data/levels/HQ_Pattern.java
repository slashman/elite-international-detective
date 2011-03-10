package net.slashware.eid.data.levels;

import net.slashie.serf.level.Unleasher;
import net.slashie.serf.levelGeneration.StaticPattern;

public class HQ_Pattern extends StaticPattern {

	public HQ_Pattern () {
		this.cellMap = new String [][]{{
			"####################################################",
			"#==..######....#.....a......#..#....=.|...|........#",
			"#....#....#....#.====.====..#..#..a.=.|............/",
			"#....#....#....#.====.====..#..#....=.|...|........#",
			"#....######....#.....a......#/##.....##...#--------#",
			"#.......##########/#####..................#....=a..#",
			"#.......#........#..#..#.......................=...#",
			"#.......#...=....#..#..#....#.=.=.##/####..........#",
			"#.......#..===...####/##....#.....#...#.#.....=====#",
			"#.......#...=..aaa..........#.=.=.#...#/#.......J..#",
			"#.......#..===...#######....###########.############",
			"#.......#...=....#.................................#",
			"#.......#........#.....=.....=.........S=a....=....#",
			"#.......##########...===========...#..===========..#",
			"#................#.....=.....=.a...#...L=.....=....#",
			"#................#.................#...............#",
			"####################################################",
		}};
		
		charMap.put(".", "FLOOR");
		charMap.put("-", "H_GLASS");
		charMap.put("|", "V_GLASS");
		charMap.put("=", "DESK");
		charMap.put("/", "DOOR");
		charMap.put("L", "FLOOR NPC LALI");
		charMap.put("#", "WALL");
		charMap.put("S", "FLOOR EXIT _START");
		charMap.put("b", "FLOOR ITEM BERGMANN_MP18");
		charMap.put("J", "FLOOR NPC JEFF");
		charMap.put("a", "FLOOR NPC AGENT");
		charMap.put("E", "FLOOR EXIT LEVEL_1");

		unleashers = new Unleasher[]{};
	}

	@Override
	public String getDescription() {
		return "HQ";
	}

}
