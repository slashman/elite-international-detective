package net.slashware.eid.entity.mission;

public class PersonSubject extends CrimeSubject {
	private String name;
	private boolean male;
	private String background; 
	
	public PersonSubject(String name, String background, boolean male) {
		super();
		this.name = name;
		this.male = male;
		this.background = background;
	}
	
	public String getName() {
		return name;
	}
	public boolean isMale() {
		return male;
	}
	public String getBackground() {
		return background;
	}
	
	public String getDetailedDescription(){
		return getName()+", "+getBackground()+",";
	}
	
	@Override
	public String getSimpleDescription() {
		return getName();
	}
}
