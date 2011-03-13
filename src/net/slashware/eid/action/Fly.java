package net.slashware.eid.action;

import java.util.ArrayList;
import java.util.List;

import net.slashie.serf.action.Action;
import net.slashie.serf.action.Actor;
import net.slashie.serf.game.Player;
import net.slashie.serf.ui.UserInterface;
import net.slashware.eid.EIDGame;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.controller.level.LocationManager;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.level.UrbanLevel;
import net.slashware.eid.entity.mission.CityLocation;
import net.slashware.eid.entity.player.DetectiveActor;
import net.slashware.eid.ui.EIDDisplay;

public class Fly extends Action{

	@Override
	public void execute() {
		DetectiveActor player = (DetectiveActor) performer;
		// Fly, move this to another action?
		if (player.isOnAirport()){
			List<Location> destinations =  new ArrayList<Location>(player.getCurrentMission().getSuspiciousLocations(player.getLocation()));
			if (player.getCurrentMission().getPreviousLocation(player.getLocation()) != null){
				destinations.add(player.getCurrentMission().getPreviousLocation(player.getLocation()));
			}
			if (destinations == null || destinations.size() == 0){
				((EIDUserInterface)UserInterface.getUI()).showBlockingMessage("The agency does not yet know of any possible locations for the suspect. We need more info from you!");
			} else {
				String[] destinationsStr = new String[destinations.size()];
				int i = 0;
				for (Location destination: destinations){
					destinationsStr[i] = destination.getFullCityName();
					i++;
				}
				int choice = UserInterface.getUI().switchChat("Airport Lady", "Where are you travelling, sir?", destinationsStr);
				if (choice != -1){
					Location destinationChoice = destinations.get(choice);
					int tripTimeHours = calculateTrip(player, destinationChoice);
					((EIDGame)player.getGame()).elapseHours(tripTimeHours);
					EIDDisplay.thus.showFlight(player.getLocation(), destinationChoice);
					player.setLocation(destinationChoice);
					player.getCurrentMission().gotoLocation(destinationChoice);
				}
			}
		} else {
			player.getLevel().addMessage("You can't fly here");
		}
				
	}

	private int calculateTrip(DetectiveActor player, Location destinationChoice) {
		return 6;
	}

	@Override
	public String getID() {
		return "Fly";
	}

}
