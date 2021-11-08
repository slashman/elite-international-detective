package net.slashware.eid.controller.level;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.slashie.serf.level.Dispatcher;
import net.slashie.serf.levelGeneration.StaticGenerator;
import net.slashie.serf.levelGeneration.bsp.BSPMapGenerator;
import net.slashie.serf.levelGeneration.bsp.BSPRoom;
import net.slashie.utils.Position;
import net.slashie.utils.Util;
import net.slashware.eid.entity.level.EIDLevel;
import net.slashware.eid.entity.level.UrbanLevel;
import net.slashware.eid.entity.mission.CityLocation;
import net.slashware.eid.entity.player.DetectiveActor;

public class UrbanZoneGenerator extends EIDStaticGenerator {
	enum CardinalQuest {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	private final static Hashtable<String, String> charMap = new Hashtable<String, String>();
	static {
		charMap.put(".", "STREET");
		charMap.put("D", "DOOR");
		charMap.put(",", "FLOOR");
		charMap.put("w", "WALKWAY");
		charMap.put("|", "STREET_V_BAR");
		charMap.put("-", "STREET_H_BAR");
		charMap.put("S", "STREET EXIT _START");
		charMap.put("B", "BUILDING_WALL");
		charMap.put("/", "BLIND_DOOR");

		charMap.put("E", "FLOOR FEATURE CLUE");
		
		charMap.put("C", "FLOOR CRIMINAL");
		charMap.put("I", "FLOOR NPC INFORMANT");
		charMap.put("U", "FLOOR NPC SUSPECT");
		charMap.put("O", "FLOOR NPC COP");
		charMap.put("W", "FLOOR NPC WITNESS");
		charMap.put("v", "FLOOR NPC CIVILIAN");
		
		charMap.put("$", "FLOOR WEAPON");
		
		// Criminals
		charMap.put("L", "FLOOR CRIMINAL_LEADER");
		
	}
	public EIDLevel generateUrbanZone(
		CityLocation cityLocation, int width, int height,
		int chaos,
		int parks,
		DetectiveActor detective,
		int difficulty
		){
		UrbanLevel ret = new UrbanLevel();
		ret.setDispatcher(new Dispatcher());
		List<BSPRoom> rooms = BSPMapGenerator.generateBSPMap(width, height, 10, 60);
		char[][] charBuffer = new char[height][width];
		for (BSPRoom room: rooms){
			drawBlock(charBuffer, room);
			// cityLocation.drawBlock(charBuffer, room); // TEST
		}
		
		// Pick one of the rooms for the cityLocation (Target), use the right border
		List<BSPRoom> borderRooms = new ArrayList<BSPRoom>();
		for (BSPRoom room: rooms){
			if (room.getXpos()+room.getWidth() == width)
				borderRooms.add(room);
		}
		BSPRoom cityLocationRoom = (BSPRoom) Util.randomElementOf(borderRooms);
		cityLocation.drawBlock(charBuffer, cityLocationRoom);
		ret.setTarget(new Position(cityLocationRoom.getXpos()+(int)Math.round(cityLocationRoom.getWidth()/2.0d), cityLocationRoom.getYpos()+(int)Math.round(cityLocationRoom.getHeight()/2.0d)));
		
		// Add civilians
		int civilians = Util.rand(10, 20);
		for (int i = 0; i < civilians; i++){
			int x = Util.rand(5, width - 5);
			int y = Util.rand(5, height - 5);
			while (charBuffer[y][x] != 'w'){
				x = Util.rand(5, width - 5);
				y = Util.rand(5, height - 5);
			}
			charBuffer[y][x] = 'v';
		}
		
		int criminals = Util.rand(4, 6);
		for (int i = 0; i < criminals; i++){
			int x = Util.rand(5, width - 5);
			int y = Util.rand(5, height - 5);
			while (charBuffer[y][x] != 'w'){
				x = Util.rand(5, width - 5);
				y = Util.rand(5, height - 5);
			}
			charBuffer[y][x] = 'C';
		}
		
		int items = Util.rand(1, 2);
		for (int i = 0; i < items; i++){
			int x = Util.rand(5, width - 5);
			int y = Util.rand(5, height - 5);
			while (charBuffer[y][x] != ','){
				x = Util.rand(5, width - 5);
				y = Util.rand(5, height - 5);
			}
			charBuffer[y][x] = '$';
		}
		
		
		// TODO: Crop the string map
		// Starting location, select the BSPRooms in the left border
		borderRooms = new ArrayList<BSPRoom>();
		for (BSPRoom room: rooms){
			if (room.getXpos() == 0 /*&& room.getYpos() != 0*/)
				borderRooms.add(room);
		}
		BSPRoom borderRoom = (BSPRoom) Util.randomElementOf(borderRooms);
		charBuffer[borderRoom.getYpos()][borderRoom.getXpos()] = 'S';
		ret.setStart(new Position(borderRoom.getXpos(),borderRoom.getYpos()));
		
		// Render the map
		
		String[] stringMap = new String[charBuffer.length];
		int i = 0;
		for (char[] charLine: charBuffer){
			stringMap[i] = new String(charLine);
			i++;
		}
		ret.initializeCells(1, width, height);
		ret.setCharMap(charBuffer);
		
		ret.setPlayer(detective);
		renderOverLevel(ret, stringMap, charMap, new Position(0,0));
		

		return ret;
	}
		
	private static void drawBlock(char[][] charBuffer, BSPRoom room) {
		drawStreetsFrame(charBuffer, room);
		drawSimpleRoomWithEntrance(charBuffer, room);
	}

	public static void drawStreetsFrame(char[][] charBuffer, BSPRoom room) {
		for (int x = room.getXpos(); x < room.getXpos()+room.getWidth(); x++){
			if (x % 2 == 0)
				charBuffer[room.getYpos()][x] = '-';
			else
				charBuffer[room.getYpos()][x] = '.';
			charBuffer[room.getYpos()+1][x] = '.';
			if (x>room.getXpos()+1 && x < room.getXpos()+room.getWidth() - 1){
				charBuffer[room.getYpos()+2][x] = 'w';
				charBuffer[room.getYpos()+room.getHeight()-2][x] = 'w';
			}
			charBuffer[room.getYpos()+room.getHeight()-1][x] = '.';
		}
		for (int y = room.getYpos(); y < room.getYpos()+room.getHeight(); y++){
			if (y%2 == 0){
				charBuffer[y][room.getXpos()] = '|';
			} else {
				charBuffer[y][room.getXpos()] = '.';
			}
			charBuffer[y][room.getXpos()+1] = '.';
			charBuffer[y][room.getXpos()+room.getWidth()-1] = '.';
			if (y>room.getYpos()+1 && y < room.getYpos()+room.getHeight() - 1){
				charBuffer[y][room.getXpos()+2] = 'w';
				charBuffer[y][room.getXpos()+room.getWidth()-2] = 'w';
			}
		}
	}

	public static void drawSimpleRoomWithEntrance(char[][] charBuffer,
			BSPRoom room) {
		for (int x = room.getXpos()+3; x < room.getXpos()+room.getWidth()-2; x++){
			for (int y = room.getYpos()+3; y < room.getYpos()+room.getHeight()-2; y++){
				if (x == room.getXpos()+3 || x == room.getXpos()+room.getWidth()-3 ||
					y == room.getYpos()+3 || y == room.getYpos()+room.getHeight()-3
						) {
					// Is a wall
					charBuffer[y][x] = 'B';
				} else {
					charBuffer[y][x] = ',';
				}
			}
		}
		int xdoor = 0;
		int ydoor = 0;
		switch (Util.rand(0, 3)){
		case 0: // UP
			xdoor = Util.rand(4, room.getWidth()-5);
			ydoor = 3;
			break;
		case 1: // DOWN
			xdoor = Util.rand(4, room.getWidth()-5);
			ydoor = room.getHeight()-3;
			break;
		case 2: // LEFT
			xdoor = 3;
			ydoor = Util.rand(4, room.getHeight()-5);
			break;
		case 3: // RIGHT
			xdoor = room.getWidth()-3;
			ydoor = Util.rand(4, room.getHeight()-5);
			break;
		}
		charBuffer[ydoor+room.getYpos()][xdoor+room.getXpos()] = ',';
		
	}
}
