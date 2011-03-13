package net.slashware.eid.entity.level;

import java.util.HashMap;
import java.util.Map;

import net.slashie.serf.level.BufferedLevel;
import net.slashie.utils.Position;
import net.slashie.utils.Util;

public class EIDLevel extends BufferedLevel {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Integer> bloods = new HashMap<String, Integer>();
	private String musicKey;

	public String getMusicKey() {
		return musicKey;
	}

	public void setMusicKey(String musicKey) {
		this.musicKey = musicKey;
	}
	
	public void addBlood(Position where){
		bloods.put(where.toString(), Util.rand(0,3));
	}
	
	public Integer getBloodAt(Position where){
		return bloods.get(where.toString());
	}


}
