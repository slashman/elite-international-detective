package net.slashware.eid.action;

import java.util.List;

import net.slashie.serf.action.Action;
import net.slashie.serf.game.Player;
import net.slashie.serf.level.LevelMetaData;
import net.slashie.serf.ui.UserInterface;
import net.slashware.eid.EIDGame;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.controller.LocationManager;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.mission.CityLocation;
import net.slashware.eid.entity.player.DetectiveActor;
import net.slashware.eid.ui.EIDDisplay;

public class CallTransport extends Action{

	@Override
	public void execute() {
		DetectiveActor player = (DetectiveActor) performer;
		if (player.isOnAirport()){
			if (UserInterface.getUI().promptChat("Take a flight? Y/N")){
				List<Location> destinations =  player.getCurrentMission().getSuspiciousLocations(player.getLocation());
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
				return;
			}
		} else {
			
		}
		if (player.isOnHQ()){
			// Go to airport
			if (UserInterface.getUI().promptChat("Go to the airport? Y/N")){
				player.informPlayerEvent(Player.EVT_GOTO_LEVEL, "AIRPORT");			
			}
		} else {
			List<CityLocation> destinations =  player.getCurrentMission().getSuspiciousPlaces(player.getLocation());
			if (destinations == null){
				if (UserInterface.getUI().promptChat("You do not know where to go. Will you engage into sleuthwork now? Y/N")){
					player.getCurrentMission().doSleuthwork(player.getLocation());
				}
			} else {
				int size = destinations.size();
				if (!player.isOnAirport()){
					size ++;
				}
				String[] destinationsStr = new String[size];
				if (!player.isOnAirport()){
					destinationsStr[destinations.size()] = "Airport";
				}
				int i = 0;
				for (CityLocation destination: destinations){
					destinationsStr[i] = destination.getName() + " ("+destination.getCityLocationType().getDescription() +")";
					i++;
				}
				int choice = UserInterface.getUI().switchChat("Transporter", "Where should I take you to, sir?", destinationsStr);
				if (choice != -1){
					if (choice == destinations.size()){
						player.informPlayerEvent(Player.EVT_GOTO_LEVEL, "AIRPORT");
					} else {
						CityLocation destinationChoice = destinations.get(choice);
						int tripTimeHours = calculateTrip(player, destinationChoice);
						((EIDGame)player.getGame()).elapseHours(tripTimeHours);
						LocationManager.setLevelMetadata(destinationChoice.getLevelCode(), player);
						player.informPlayerEvent(Player.EVT_GOTO_LEVEL, destinationChoice.getLevelCode());
					}
				}
			}
		}
	}

	private int calculateTrip(DetectiveActor player, Location destinationChoice) {
		return 6;
	}

	private int calculateTrip(DetectiveActor player,
			CityLocation destinationChoice) {
		return 2;
	}

	@Override
	public String getID() {
		return "Call Transport";
	}

}
