package net.slashware.eid;

import java.util.Calendar;

import net.slashie.serf.action.Actor;
import net.slashie.serf.game.Player;
import net.slashie.serf.game.SworeGame;
import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.level.LevelMetaData;
import net.slashie.serf.sound.STMusicManagerNew;
import net.slashware.eid.controller.LevelMaster;
import net.slashware.eid.controller.LocationManager;
import net.slashware.eid.controller.mission.MissionGenerator;
import net.slashware.eid.entity.level.EIDLevel;
import net.slashware.eid.entity.mission.Mission;
import net.slashware.eid.entity.player.DetectiveActor;
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
		setGameTime(3,8,1922);
		Mission firstMission = MissionGenerator.generateMission(currentTime.getTime(), 1);
		getDetective().setCurrentMission(firstMission);
		getDetective().setLocation(LocationManager.getLocation("CO MDE"));
		EIDDisplay.thus.showMission(getDetective(), firstMission);
		loadLevel("HQ");
		
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
		currentTime.set(Calendar.HOUR_OF_DAY, 17);
		currentTime.set(Calendar.MINUTE, 0);
	}
	
	public Calendar getGameTime(){
		return currentTime;
	}
	
	private void loadMetadata() {
		LevelMetaData md = null;
		
		md = new LevelMetaData("HQ");
		addMetaData("HQ", md);
		md = new LevelMetaData("AIRPORT");
		addMetaData("AIRPORT", md);

	}

	@Override
	public void onLevelLoad(AbstractLevel aLevel) {
		EIDLevel level = (EIDLevel) aLevel;
		if (level.getMusicKey() != null)
			STMusicManagerNew.thus.playKey(level.getMusicKey());
	}
	
	@Override
	public void afterPlayerAction() {
	}

	
	public void elapseHours(int hours) {
		currentTime.add(Calendar.HOUR_OF_DAY, hours);
		if (currentTime.get(Calendar.HOUR_OF_DAY) > 23){
			// Next day!
			getDetective().takeNap();
		}
	}

	public void tilMorrow() {
		currentTime.add(Calendar.DATE, 1);
		currentTime.set(Calendar.HOUR_OF_DAY, 7);
	}

	public void elapseMillis(int millis) {
		currentTime.add(Calendar.MILLISECOND, millis);
		if (currentTime.get(Calendar.HOUR_OF_DAY) > 23){
			// Next day!
			getDetective().takeNap();
		}
		
	}
}
