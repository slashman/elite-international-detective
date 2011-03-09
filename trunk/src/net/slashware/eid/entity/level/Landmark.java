package net.slashware.eid.entity.level;

public class Landmark {
	private String name;
	private String background;
	public String getName() {
		return name;
	}
	public String getBackground() {
		return background;
	}
	public Landmark(String name, String background) {
		super();
		this.name = name;
		this.background = background;
	}
	
	public Landmark(String name) {
		this.name = name;
	}
}
