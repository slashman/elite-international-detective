package net.slashware.eid.entity.mission;

public enum CrimeLocation {
	a("house at", true),
	b("mansion at", true),
	c("the city library of", false),
	e("the airport of", false),
	f("the central park of", false),
	g("the colosseum of", false),
	h("the cathedral of", false),
	i("a supermarket in", false),
	j("a bank in", false),
	k("a metro station in", false),
	l("the palace of", false),
	m("the University of", false),
	n("the streets of", false),
	o("car at", true);
	
	private String detailedLocation;
	private boolean requiresHisHer;
	public String getDetailedLocation() {
		return detailedLocation;
	}
	public boolean isRequiresHisHer() {
		return requiresHisHer;
	}
	private CrimeLocation(String detailedLocation, boolean requiresHisHer) {
		this.detailedLocation = detailedLocation;
		this.requiresHisHer = requiresHisHer;
	}
	
}
