package net.slashware.eid.action;

import java.util.List;

import net.slashie.serf.action.Action;
import net.slashie.serf.action.Actor;
import net.slashie.serf.game.Player;
import net.slashie.serf.ui.UserInterface;

import net.slashware.eid.EIDGame;
import net.slashware.eid.controller.level.LocationManager;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.level.UrbanLevel;
import net.slashware.eid.entity.mission.CityLocation;
import net.slashware.eid.entity.player.DetectiveActor;

public class CallTransport extends Action{

	@Override
	public void execute() {
		DetectiveActor player = (DetectiveActor) performer;
		if (player.isOnHQ()){
			// TODO: Shouldn't need to go to the airport if we are investigating in the HQ hometown.
			if (UserInterface.getUI().promptChat("Go to the airport? Y/N")){
				player.informPlayerEvent(Player.EVT_GOTO_LEVEL, "AIRPORT");
				((EIDGame)player.getGame()).elapseHours(1);
			}
		} else {
			// Player may be at the airport or at an urban area
			List<CityLocation> destinations =  player.getCurrentMission().getSuspiciousPlaces(player.getLocation());
			if (destinations == null){
				if (UserInterface.getUI().promptChat("You do not know where to go. Will you engage into sleuthwork now? Y/N")){
					player.getCurrentMission().doSleuthwork(player.getLocation());
				}
			} else {
				int size = destinations.size();
				if (!player.isOnAirport()){
					size ++;
				} else if (player.getLocation().equals(Location.getHQLocation())){
					size++;
				}
				String[] destinationsStr = new String[size];
				if (!player.isOnAirport()){
					destinationsStr[destinations.size()] = "Airport";
				} else if (player.getLocation().equals(Location.getHQLocation())){
					destinationsStr[destinations.size()] = "Headquarters";
				}
				int i = 0;
				for (CityLocation destination: destinations){
					destinationsStr[i] = destination.getName() + " ("+destination.getCityLocationType().getDescription() +")";
					i++;
				}
				int choice = UserInterface.getUI().switchChat("Next Location", "Where will you go?", destinationsStr);
				if (choice != -1){
					if (choice == destinations.size()){
						if (!player.isOnAirport()){
							player.informPlayerEvent(Player.EVT_GOTO_LEVEL, "AIRPORT");
							((EIDGame)player.getGame()).elapseHours(1);
						} else if (player.getLocation().equals(Location.getHQLocation())){
							player.informPlayerEvent(Player.EVT_GOTO_LEVEL, "HQ");
							((EIDGame)player.getGame()).elapseHours(1);
						}
					} else {
						CityLocation destinationChoice = destinations.get(choice);
						int tripTimeHours = calculateTrip(player, destinationChoice);
						((EIDGame)player.getGame()).elapseHours(tripTimeHours);
						LocationManager.setLevelMetadata(destinationChoice, player);
						player.informPlayerEvent(Player.EVT_GOTO_LEVEL, destinationChoice.getLevelCode());
					}
				}
			}
		}
	}

	private int calculateTrip(DetectiveActor player,
			CityLocation destinationChoice) {
		return 2;
	}

	@Override
	public String getID() {
		return "Call Transport";
	}
	
	@Override
	public boolean canPerform(Actor a) {
		// If player is in an urban area he should be in the borders to escape
		DetectiveActor player = ((DetectiveActor)a);
		if (player.getLevel() instanceof UrbanLevel){
			if (player.getPosition().x == 0){
				return true;
			} else {
				invalidationMessage = "You need to be in the western border to exit this location!";
				return false;
			}
		} else {
			return super.canPerform(a);
		}
	}

}
