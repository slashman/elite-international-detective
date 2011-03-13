package net.slashware.eid.ui;

import java.text.DateFormat;
import java.util.Locale;

import net.slashware.eid.entity.mission.Mission;
import net.slashware.eid.entity.player.DetectiveActor;

public class CommonUI {
	private static final DateFormat DATEFORMAT = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.US);

	public static String composeMissionDescription(DetectiveActor detective, Mission m){
		StringBuilder builder = new StringBuilder();
		builder.append(m.getCrime().getSubject().getDetailedDescription());
		builder.append(" has been ");
		builder.append(m.getCrime().getCrimeType().getCrimeDescription());
		builder.append(" at ");
		builder.append(m.getCrime().getDetailedLocation());
		builder.append("!");
		builder.append(" XXX XXX ");
		if (m.getCrime().getCriminal().getCriminalOrganization() == null){
			builder.append("No criminal organization has taken responsability for this crime.");
		} else {
			builder.append("Criminal organization \"");
			builder.append(m.getCrime().getCriminal().getCriminalOrganization().getName());
			builder.append("\" has taken responsability for this crime.");
		}
		builder.append(" XXX XXX ");
		builder.append("Your assignment: Travel to ");
		builder.append(m.getCrime().getLocation().getCountryName());
		builder.append(", track the ");
		builder.append(m.getCrime().getCrimeType().getCriminalDescription());
		builder.append(" to his hideout and neutralize him.");
		builder.append(" XXX XXX ");
		builder.append("You are authorized to do whatever it takes to disable or exterminate the criminal.");
		builder.append(" XXX XXX ");
		builder.append("The ");
		builder.append(m.getCrime().getCrimeType().getCriminalDescription());
		builder.append(" has threathened to strike back by ");
		builder.append(DATEFORMAT.format(m.getDeadline()));
		builder.append("; if you have not captured him/her by then, your mission will be considered a failure.");
		builder.append(" XXX XXX ");
		builder.append("Good Luck, ");
		builder.append(detective.getDescription());
		return builder.toString();
	}
}
