package net.slashware.eid.action;

import java.util.ArrayList;
import java.util.List;

import net.slashie.serf.action.Action;
import net.slashie.serf.game.Player;
import net.slashie.serf.ui.UserInterface;
import net.slashware.eid.EIDGame;
import net.slashware.eid.entity.mission.CityLocation;
import net.slashware.eid.entity.player.DetectiveActor;

public class CallTransport extends Action{

	@Override
	public void execute() {
		DetectiveActor player = (DetectiveActor) performer;
		if (player.isOnAirport()){
			// Select a flight
		} else if (player.isOnHQ()){
			// Go to airport
			if (UserInterface.getUI().promptChat("Go to the airport?")){
				player.informPlayerEvent(Player.EVT_GOTO_LEVEL, "AIRPORT");			
			}
		} else {
			List<CityLocation> destinations =  player.getCurrentMission().getSuspiciousPlaces(player.getLocation());
			if (destinations == null){
				youMessage("You need to do some Sleuthwork first!");
			} else {
				String[] destinationsStr = new String[destinations.size()];
				int i = 0;
				for (CityLocation destination: destinations){
					destinationsStr[i] = destination.getName() + "("+destination.getCityLocationType().getDescription() +")";
					i++;
				}
				int choice = UserInterface.getUI().switchChat("Transporter", "Where should I take you to, sir?", destinationsStr);
				if (choice != -1){
					CityLocation destinationChoice = destinations.get(choice);
					int tripTimeHours = calculateTrip(player, destinationChoice);
					((EIDGame)player.getGame()).elapseHours(tripTimeHours);
					player.informPlayerEvent(Player.EVT_GOTO_LEVEL, destinationChoice.getLevelCode());
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

}
