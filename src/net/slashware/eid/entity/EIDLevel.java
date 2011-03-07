package net.slashware.eid.entity;

import net.slashie.serf.level.BufferedLevel;

public class EIDLevel extends BufferedLevel {
	private static final long serialVersionUID = 1L;
	
	private String musicKey;

	public String getMusicKey() {
		return musicKey;
	}

	public void setMusicKey(String musicKey) {
		this.musicKey = musicKey;
	}
}
