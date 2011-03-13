package net.slashware.eid.entity.level;

import net.slashie.utils.Position;

public class UrbanLevel extends EIDLevel{
	private char[][] charMap;
	private Position start;
	private Position target;

	public char[][] getCharMap() {
		return charMap;
	}

	public void setCharMap(char[][] charMap) {
		this.charMap = charMap;
	}

	public Position getStart() {
		return start;
	}

	public void setStart(Position start) {
		this.start = start;
	}

	public Position getTarget() {
		return target;
	}

	public void setTarget(Position target) {
		this.target = target;
	}
	
	
	
}
