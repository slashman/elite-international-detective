package net.slashware.eid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Properties;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.jcurses.JCursesConsoleInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.serf.SworeException;
import net.slashie.serf.action.Action;
import net.slashie.serf.action.ActionFactory;
import net.slashie.serf.game.SworeGame;
import net.slashie.serf.level.FeatureFactory;
import net.slashie.serf.level.MapCellFactory;
import net.slashie.serf.sound.SFXManager;
import net.slashie.serf.sound.STMusicManagerNew;
import net.slashie.serf.ui.Appearance;
import net.slashie.serf.ui.AppearanceFactory;
import net.slashie.serf.ui.CommandListener;
import net.slashie.serf.ui.EffectFactory;
import net.slashie.serf.ui.UISelector;
import net.slashie.serf.ui.UserAction;
import net.slashie.serf.ui.UserCommand;
import net.slashie.serf.ui.UserInterface;
import net.slashie.serf.ui.consoleUI.ConsoleUISelector;
import net.slashie.serf.ui.consoleUI.ConsoleUserInterface;
import net.slashie.serf.ui.consoleUI.effects.CharEffectFactory;
import net.slashie.serf.ui.oryxUI.GFXUISelector;
import net.slashie.serf.ui.oryxUI.GFXUserInterface;
import net.slashie.serf.ui.oryxUI.SwingSystemInterface;
import net.slashie.serf.ui.oryxUI.effects.GFXEffectFactory;
import net.slashie.utils.sound.midi.STMidiPlayer;
import net.slashware.eid.action.CallTransport;
import net.slashware.eid.action.ChangeLethality;
import net.slashware.eid.action.Fire;
import net.slashware.eid.action.Fly;
import net.slashware.eid.action.Grab;
import net.slashware.eid.action.Run;
import net.slashware.eid.action.Use;
import net.slashware.eid.action.Walk;
import net.slashware.eid.controller.GameFiles;
import net.slashware.eid.controller.level.LocationManager;
import net.slashware.eid.data.EIDData;
import net.slashware.eid.data.ItemFactory;
import net.slashware.eid.data.NPCFactory;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.ui.EIDDisplay;
import net.slashware.eid.ui.console.CharAppearances;
import net.slashware.eid.ui.console.CharEffects;
import net.slashware.eid.ui.console.EIDConsoleUI;
import net.slashware.eid.ui.console.EIDConsoleUISelector;

public class RunGame {
	private final static int JCURSES_CONSOLE = 0, SWING_GFX = 1, SWING_CONSOLE = 2;
	private static UserInterface ui;
	private static UISelector uiSelector;
	
	private static EIDGame currentGame;
	private static boolean createNew = true;
	private static int mode;
	
	public static String getConfigurationVal(String key){
		return configuration.getProperty(key);
	}

	private static void init(){
		if (createNew){
			System.out.println("Elite International Detective "+EIDGame.getVersion());
			System.out.println("by Slashie ~ 2011, 2021");
			System.out.println("Powered By Serf "+SworeGame.getVersion());
			System.out.println("Reading configuration");
	    	readConfiguration();
            try {
    			
    			switch (mode){
				case SWING_GFX:
					System.out.println("Initializing Graphics Appearances");
					initializeGAppearances();
					break;
				case JCURSES_CONSOLE:
				case SWING_CONSOLE:
					System.out.println("Initializing Char Appearances");
					initializeCAppearances();
					break;
    			}
				System.out.println("Initializing Action Objects");
				initializeActions();
				initializeSelectors();
				System.out.println("Loading Data");
				initializeItems();
				initializeCells();
				initializeNPCS();
				initializeLocations();
				initializeFeatures();
				switch (mode){
				case SWING_GFX:
					/* ASCII first 
					System.out.println("Initializing Swing GFX System Interface");
					SwingSystemInterface si = new SwingSystemInterface();
					System.out.println("Initializing Oryx GFX User Interface");
					UserInterface.setSingleton(new ExpeditionOryxUI());
					ExpeditionDisplay.thus = new OryxExpeditionDisplay(si, UIconfiguration);
					
					EffectFactory.setSingleton(new GFXEffectFactory());
					((GFXEffectFactory)EffectFactory.getSingleton()).setEffects(new GFXEffects().getEffects());
					ui = UserInterface.getUI();
					initializeUI(si);*/
					break;
				case JCURSES_CONSOLE:
					System.out.println("Initializing JCurses System Interface");
					ConsoleSystemInterface csi = null;
					try{
						csi = new JCursesConsoleInterface();
						csi.setAutoRefresh(false);
					}
		            catch (ExceptionInInitializerError eiie){
		            	crash("Fatal Error Initializing JCurses", eiie);
		            	eiie.printStackTrace();
		                System.exit(-1);
		            }
		            System.out.println("Initializing Console User Interface");
		            EIDConsoleUI eidConsoleUI = new EIDConsoleUI(csi);
					UserInterface.setSingleton(eidConsoleUI);
					EIDDisplay.thus = eidConsoleUI.getConsoleDisplay();
					
					EffectFactory.setSingleton(new CharEffectFactory());
					((CharEffectFactory)EffectFactory.getSingleton()).setEffects(new CharEffects().getEffects());
					ui = UserInterface.getUI();
					initializeUI(csi);
					break;
				case SWING_CONSOLE:
					System.out.println("Initializing Swing Console System Interface");
					csi = null;
					csi = new WSwingConsoleInterface("Elite International Detective", configuration);
					System.out.println("Initializing Console User Interface");
		            eidConsoleUI = new EIDConsoleUI(csi);
					UserInterface.setSingleton(eidConsoleUI);
					EIDDisplay.thus = eidConsoleUI.getConsoleDisplay();
					
					EffectFactory.setSingleton(new CharEffectFactory());
					((CharEffectFactory)EffectFactory.getSingleton()).setEffects(new CharEffects().getEffects());
					ui = UserInterface.getUI();
					initializeUI(csi);
				}
				
            } catch (SworeException crle){
            	crash("Error initializing", crle);
            }
            STMusicManagerNew.initManager();
        	if (configuration.getProperty("enableSound") != null && configuration.getProperty("enableSound").equals("true")){ // Sound
        		if (configuration.getProperty("enableMusic") == null || !configuration.getProperty("enableMusic").equals("true")){ // Music
    	    		STMusicManagerNew.thus.setEnabled(false);
    		    } else {
    		    	System.out.println("Initializing Midi Sequencer");
    	    		try {
    	    			STMidiPlayer.initializeSequencer();
    	    		} catch(MidiUnavailableException mue) {
    	            	SworeGame.addReport("Midi device unavailable");
    	            	System.out.println("Midi Device Unavailable");
    	            	STMusicManagerNew.thus.setEnabled(false);
    	            	return;
    	            }
    	    		System.out.println("Initializing Music Manager");
    				
    		    	
    	    		Enumeration keys = configuration.keys();
    	    	    while (keys.hasMoreElements()){
    	    	    	String key = (String) keys.nextElement();
    	    	    	if (key.startsWith("mus_")){
    	    	    		String music = key.substring(4);
    	    	    		STMusicManagerNew.thus.addMusic(music, configuration.getProperty(key));
    	    	    	}
    	    	    }
    	    	    STMusicManagerNew.thus.setEnabled(true);
    		    }
    	    	if (configuration.getProperty("enableSFX") == null || !configuration.getProperty("enableSFX").equals("true")){
    		    	SFXManager.setEnabled(false);
    		    } else {
    		    	SFXManager.setEnabled(true);
    		    }
        	}
			createNew = false;
    	}
	}
	private static void initializeLocations() {
		Location[] locations = EIDData.getLocations();
		for (Location location: locations){
			LocationManager.addLocation(location);
		}
	}
	
	private static Properties configuration;
	private static Properties UIconfiguration;
	private static Properties keyConfig;
	private static Properties keyBindings;
	private static String uiFile;
	
	private static void readConfiguration(){
		configuration = new Properties();
	    try {
	    	configuration.load(new FileInputStream("eid.properties"));
	    } catch (IOException e) {
	    	System.out.println("Error loading configuration file, please confirm existence of eid.properties");
	    	System.exit(-1);
	    }
	    
	    
	    keyConfig = new Properties();
	    try {
	    	keyConfig.load(new FileInputStream("keys.properties"));
	    	
	    } catch (IOException e) {
	    	System.out.println("Error loading configuration file, please confirm existence of keys.properties");
	    	System.exit(-1);
	    }
	    
	    if (mode == SWING_GFX){
		    UIconfiguration = new Properties();
		    try {
		    	UIconfiguration.load(new FileInputStream(uiFile));
		    } catch (IOException e) {
		    	System.out.println("Error loading configuration file, please confirm existence of "+uiFile);
		    	System.exit(-1);
		    }
	    }

	}
	
	private static void	title() {
		int choice = EIDDisplay.thus.showTitleScreen();
		switch (choice){
		case 0:
			newGame();
			break;
		case 1:
			loadGame();
			break;
		case 2:
			System.out.println("Elite International Detective "+EIDGame.getVersion()+", clean Exit");
			System.out.println("Thank you for playing!");
			System.exit(0);
			break;
		}
	}
	
	private static void loadGame(){
		File saveDirectory = new File("savegame");
		File[] saves = saveDirectory.listFiles(new SaveGameFilenameFilter() );
		
		int index = EIDDisplay.thus.showSavedGames(saves);
		if (index == -1){
			return;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saves[index]));
			currentGame = (EIDGame) ois.readObject();
			ois.close();
		} catch (IOException ioe){
 
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe){
			crash("Invalid savefile or wrong version", new SworeException("Invalid savefile or wrong version"));
		}
		currentGame.setInterfaces(ui, uiSelector);
		if (currentGame.getPlayer().getLevel() == null){
			crash("Player wasnt loaded", new Exception("Player wasnt loaded"));
		}
		currentGame.setPlayer(currentGame.getPlayer());
		ui.setPlayer(currentGame.getPlayer());
		uiSelector.setPlayer(currentGame.getPlayer());
		currentGame.resume();
	}
	
	private static void newGame(){
		if (currentGame != null){
			ui.removeCommandListener(currentGame);
		}
		currentGame = new EIDGame();
		currentGame.setCanSave(true);
		currentGame.setInterfaces(ui, uiSelector);
		currentGame.newGame(1);
	}

	private static void initializeUI(Object si){
		Action walkAction = new Walk();
		Action meleeAction = new Walk(); //TODO
		Action grabAction = new Grab();
		Action useAction = new Use();
		Action fireAction = new Fire();
		Action runAction = new Run();
		Action moveAction = new CallTransport();
		Action flyAction = new Fly();

		keyBindings = new Properties();
		keyBindings.put("DONOTHING1_KEY", readKeyString(keyConfig, "doNothing"));
		keyBindings.put("DONOTHING2_KEY", readKeyString(keyConfig, "doNothing2"));
		keyBindings.put("UP1_KEY", readKeyString(keyConfig, "up"));
		keyBindings.put("UP2_KEY", readKeyString(keyConfig, "up2"));
		keyBindings.put("LEFT1_KEY", readKeyString(keyConfig, "left"));
		keyBindings.put("LEFT2_KEY", readKeyString(keyConfig, "left2"));
		keyBindings.put("RIGHT1_KEY", readKeyString(keyConfig, "right"));
		keyBindings.put("RIGHT2_KEY", readKeyString(keyConfig, "right2"));
		keyBindings.put("DOWN1_KEY", readKeyString(keyConfig, "down"));
		keyBindings.put("DOWN2_KEY", readKeyString(keyConfig, "down2"));
		keyBindings.put("UPRIGHT1_KEY", readKeyString(keyConfig, "upRight"));
		keyBindings.put("UPRIGHT2_KEY", readKeyString(keyConfig, "upRight2"));
		keyBindings.put("UPLEFT1_KEY", readKeyString(keyConfig, "upLeft"));
		keyBindings.put("UPLEFT2_KEY", readKeyString(keyConfig, "upLeft2"));
		keyBindings.put("DOWNLEFT1_KEY", readKeyString(keyConfig, "downLeft"));
		keyBindings.put("DOWNLEFT2_KEY", readKeyString(keyConfig, "downLeft2"));
		keyBindings.put("DOWNRIGHT1_KEY", readKeyString(keyConfig, "downRight"));
		keyBindings.put("DOWNRIGHT2_KEY", readKeyString(keyConfig, "downRight2"));
		keyBindings.put("SELF1_KEY", readKeyString(keyConfig, "self"));
		keyBindings.put("SELF2_KEY", readKeyString(keyConfig, "self2"));
		
		keyBindings.put("QUIT_KEY", readKeyString(keyConfig, "PROMPTQUIT"));
		keyBindings.put("HELP1_KEY", readKeyString(keyConfig, "HELP1"));
		keyBindings.put("HELP2_KEY", readKeyString(keyConfig, "HELP2"));
		keyBindings.put("LOOK_KEY", readKeyString(keyConfig, "LOOK"));
		keyBindings.put("PROMPT_SAVE_KEY", readKeyString(keyConfig, "PROMPTSAVE"));
		keyBindings.put("SHOW_INVENTORY_KEY", readKeyString(keyConfig, "SHOWINVEN"));
		keyBindings.put("SWITCH_MUSIC_KEY", readKeyString(keyConfig, "SWITCHMUSIC"));
		
		UserAction[] userActions = new UserAction[] {
			new UserAction(grabAction, i(readKeyString(keyConfig, "grab"))),
			new UserAction(useAction, i(readKeyString(keyConfig, "use"))),
			new UserAction(runAction, i(readKeyString(keyConfig, "run"))),
			new UserAction(moveAction, i(readKeyString(keyConfig, "move"))),
			new UserAction(flyAction, i(readKeyString(keyConfig, "fly"))),

		};
		
		UserCommand[] userCommands = new UserCommand[]{
			new UserCommand(CommandListener.Command.PROMPTQUIT, i(keyBindings.getProperty("QUIT_KEY"))),
			new UserCommand(CommandListener.Command.HELP, i(keyBindings.getProperty("HELP1_KEY"))),
			new UserCommand(CommandListener.Command.LOOK, i(keyBindings.getProperty("LOOK_KEY"))),
			new UserCommand(CommandListener.Command.PROMPTSAVE, i(keyBindings.getProperty("PROMPT_SAVE_KEY"))),
			new UserCommand(CommandListener.Command.HELP, i(keyBindings.getProperty("HELP2_KEY"))),
			new UserCommand(CommandListener.Command.SHOWINVEN, i(keyBindings.getProperty("SHOW_INVENTORY_KEY"))),
			new UserCommand(CommandListener.Command.SWITCHMUSIC, i(keyBindings.getProperty("SWITCH_MUSIC_KEY"))),
		};
		
		switch (mode){
		case SWING_GFX:
			/* l8er
			((ExpeditionOryxUI)ui).init((SwingSystemInterface)si, "Expedition: The New World v"+ExpeditionGame.getVersion()+", Santiago Zapata 2009-2010", userCommands, UIconfiguration, null);
			uiSelector = new GFXUISelector();
			((GFXUISelector)uiSelector).init((SwingSystemInterface)si, userActions, UIconfiguration, walkAction, null, meleeAction, (GFXUserInterface)ui, keyBindings);
			break;*/
		case JCURSES_CONSOLE: case SWING_CONSOLE:
			((EIDConsoleUI)ui).init((ConsoleSystemInterface)si, userCommands, fireAction);
			uiSelector = new EIDConsoleUISelector();
			((ConsoleUISelector)uiSelector).init((ConsoleSystemInterface)si, userActions, walkAction, fireAction, meleeAction, (ConsoleUserInterface)ui, keyBindings);
			break;
		}
	}
	
	private static int i(String val){
		return Integer.parseInt(val);
	}
	
	public static void main(String args[]){
		if (args!= null && args.length > 0){
			if (args[0].equalsIgnoreCase("gfx")){
				mode = SWING_GFX;
				if (args.length > 1)
					uiFile = args[1];
				else
					uiFile = "eid-swing.ui";
			} else if (args[0].equalsIgnoreCase("jc")){
				mode = JCURSES_CONSOLE;
			} else if (args[0].equalsIgnoreCase("sc")){
				mode = SWING_CONSOLE;
			}
		} else {
			mode = SWING_GFX;
			uiFile = "eid-swing.ui";

		}
		
		init();
		System.out.println("Launching game");
		try {
			while (true){
				title();
			}
		} catch (Exception e){
			EIDGame.crash("Unrecoverable Exception [Press Space]",e);
		}
	}

	private static String readKeyString(Properties config, String keyName) {
		return readKey(config, keyName)+"";
	}

	
	private static int readKey(Properties config, String keyName) {
		String fieldName = config.getProperty(keyName).trim();
		if (fieldName == null)
			throw new RuntimeException("Invalid key.cfg file, property not found: "+keyName);
		try {
			Field field = CharKey.class.getField(fieldName);
			return field.getInt(CharKey.class);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading field : "+fieldName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading field : "+fieldName);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading field : "+fieldName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading field : "+fieldName);
		}
	}
	private static void initializeGAppearances(){
		/*Appearance[] definitions = GFXAppearances.getGFXAppearances();
		for (int i=0; i<definitions.length; i++){
			AppearanceFactory.getAppearanceFactory().addDefinition(definitions[i]);
		}*/
	}
	
	private static void initializeCAppearances(){
		Appearance[] definitions = CharAppearances.getCharAppearances();
		for (int i=0; i<definitions.length; i++){
			AppearanceFactory.getAppearanceFactory().addDefinition(definitions[i]);
		}
	}
	
	private static void initializeActions(){
		ActionFactory af = ActionFactory.getActionFactory();
		Action[] definitions = new Action[]{
				new Fire()
		};
		for (int i = 0; i < definitions.length; i++)
			af.addDefinition(definitions[i]);
	}
	
	private static void initializeCells(){
		MapCellFactory.getMapCellFactory().init(EIDData.getCellDefinitions());
	}

	private static void initializeFeatures(){
		FeatureFactory.getFactory().init(EIDData.getFeatures());
	}

	private static void initializeSelectors(){
		
	}

	private static void initializeItems(){
		ItemFactory.init(EIDData.getItemDefinitions());
	}
	
	private static void initializeNPCS(){
		NPCFactory.setNPCs(EIDData.getNPCs());
	}

	    public static void crash(String message, Throwable exception){
    	System.out.println("Expedition "+EIDGame.getVersion()+": Error");
        System.out.println("");
        System.out.println("Unrecoverable error: "+message);
        exception.printStackTrace();
        if (currentGame != null){
        	System.out.println("Trying to save game");
        	GameFiles.saveGame(currentGame, currentGame.getPlayer());
        }
        System.exit(-1);
    }
    
}

class SaveGameFilenameFilter implements FilenameFilter {

	public boolean accept(File arg0, String arg1) {
		if (arg1.endsWith(".sav"))
			return true;
		else
			return false;
	}
	
}