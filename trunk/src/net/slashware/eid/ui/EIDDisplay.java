package net.slashware.eid.ui;

import java.io.File;

import net.slashware.eid.EIDGame;
import net.slashware.eid.entity.DetectiveActor;

public abstract class EIDDisplay  {
	public static EIDDisplay thus;
	/**
	 * Shows the title screen
	 * @return A numeric option chosen by the user which is interpreted by the ExpeditionGame
	 */
	public abstract int showTitleScreen();
	
	/**
	 * Shows the saved games and allow the user to pick one
	 * @param saves An array of files representing save games
	 * @return The index of the chosen save game or -1 to cancel
	 */
	public abstract int showSavedGames(File[] saves);
	
	public abstract void showMission(DetectiveActor detective);
	
	public abstract void showHelp();
	
	public abstract DetectiveActor createDetective(EIDGame game);

}
