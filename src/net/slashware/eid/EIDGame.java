package net.slashware.eid;

import java.util.Calendar;

import net.slashie.serf.action.Actor;
import net.slashie.serf.game.Player;
import net.slashie.serf.game.SworeGame;
import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.level.LevelMetaData;
import net.slashie.serf.sound.STMusicManagerNew;
import net.slashie.simpleRL.domain.world.Level;
import net.slashware.eid.controller.LevelMaster;
import net.slashware.eid.entity.DetectiveActor;
import net.slashware.eid.ui.EIDDisplay;

public class EIDGame extends SworeGame{

	@Override
	public AbstractLevel createLevel(LevelMetaData levelMetadata) {
		return LevelMaster.createLevel(levelMetadata, getPlayer());
	}

	@Override
	public Player generatePlayer(int gameType, SworeGame game) {
		return EIDDisplay.thus.createDetective((EIDGame)game);
	}

	@Override
	public String getDeathMessage() {
		return "This is the end for you...";
	}

	@Override
	public String getFirstMessage(Actor player) {
		return "Welcome, "+player.getDescription()+", press F1 or ? for help";
	}
	
	public static String getVersion(){
		return "7DRL";
	}

	@Override
	public void onGameStart(int gameType) {
		loadMetadata();
		EIDDisplay.thus.showMission(getDetective());
		loadLevel("HQ");
		setGameTime(3,8,1922);
	}

	private Calendar currentTime;
	
	private DetectiveActor getDetective() {
		return (DetectiveActor) getPlayer();
	}

	private void setGameTime(int day, int month, int year) {
		currentTime = Calendar.getInstance();
		currentTime.set(Calendar.YEAR, year);
		currentTime.set(Calendar.MONTH, month-1);
		currentTime.set(Calendar.DATE, day);
	}
	
	public Calendar getGameTime(){
		return currentTime;
	}
	
	private void loadMetadata() {
		LevelMetaData md = null;
		
		md = new LevelMetaData("HQ");
		addMetaData("HQ", md);

	}

	@Override
	public void onLevelLoad(AbstractLevel aLevel) {
		Level level = (Level)aLevel;
		if (level.getMusicKey() != null)
			STMusicManagerNew.thus.playKey(level.getMusicKey());
	}
}
