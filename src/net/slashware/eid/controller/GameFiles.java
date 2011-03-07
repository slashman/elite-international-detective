package net.slashware.eid.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.slashie.serf.game.GameSessionInfo;
import net.slashie.serf.game.Player;
import net.slashie.utils.FileUtil;
import net.slashie.utils.SerializableChecker;
import net.slashware.eid.EIDGame;

public class GameFiles {

	public static void saveMemorialFile(Player player){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String now = sdf.format(new Date());
			BufferedWriter fileWriter = FileUtil.getWriter("memorials/"+player.getName()+"("+now+").life");
			GameSessionInfo gsi = player.getGameSessionInfo();
			//TODO
			fileWriter.close();
		} catch (IOException ioe){
			EIDGame.crash("Error writing the memorial file", ioe);
		}
		
	}
	
	public static void saveGame(EIDGame g, Player p){
		String filename = "savegame/"+p.getName()+".sav";
		p.setSelector(null);
		try {
			SerializableChecker sc = new SerializableChecker();
			sc.writeObject(g);
			sc.close();
			
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
			os.writeObject(g);
			os.close();
			
			
		} catch (IOException ioe){
			EIDGame.crash("Error saving the game", ioe);
		}
	}
	
	public static void permadeath(Player p){
		String filename = "savegame/"+p.getSaveFilename()+".sav";
		if (FileUtil.fileExists(filename)) {
			FileUtil.deleteFile(filename);
		}
	}
	
	public static void saveChardump(Player player){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
			String now = sdf.format(new Date());
			BufferedWriter fileWriter = FileUtil.getWriter("memorials/"+player.getName()+" {Alive}("+now+").life");
			GameSessionInfo gsi = player.getGameSessionInfo();
			gsi.setDeathLevelDescription(player.getLevel().getDescription());
			fileWriter.close();
		} catch (IOException ioe){
			EIDGame.crash("Error writing the chardump", ioe);
		}
	}
			
	
}
