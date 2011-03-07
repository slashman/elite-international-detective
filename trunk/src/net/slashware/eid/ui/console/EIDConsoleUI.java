package net.slashware.eid.ui.console;

import java.io.File;
import java.util.Calendar;
import java.util.Vector;

import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.textcomponents.MenuBox;
import net.slashie.libjcsi.textcomponents.MenuItem;
import net.slashie.libjcsi.textcomponents.TextBox;
import net.slashie.serf.action.Action;
import net.slashie.serf.action.Actor;
import net.slashie.serf.sound.STMusicManagerNew;
import net.slashie.serf.ui.UserCommand;
import net.slashie.serf.ui.consoleUI.ConsoleUserInterface;
import net.slashie.utils.Position;
import net.slashware.eid.EIDGame;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.data.PlayerFactory;
import net.slashware.eid.entity.DetectiveActor;
import net.slashware.eid.entity.EIDLevel;
import net.slashware.eid.ui.EIDDisplay;


public class EIDConsoleUI extends ConsoleUserInterface implements EIDUserInterface{
	private ConsoleSystemInterface csi;
			
	public EIDConsoleUI (ConsoleSystemInterface csi){
		this.csi = csi;
		consoleDisplay = new EIDConsoleDisplay(csi);
	}
	
	public void init(ConsoleSystemInterface psi, UserCommand[] gameCommands, Action target){
		super.init(psi, gameCommands, target);
		VP_START = new Position(1,1);
		VP_END = new Position (21,21);
		PC_POS = new Position(10, 10);
		xrange = 9;
		yrange = 9;
		messageBox.setPosition(49,14);
		messageBox.setWidth(30);
		messageBox.setHeight(10);
		messageBox.setForeColor(ConsoleSystemInterface.WHITE);
		
		idList.setPosition(22,1);
		idList.setWidth(20);
		idList.setHeight(10);
	}
	
	@Override
	protected void beforeDrawStatus() {
		drawAddornment();
	}
	
	@Override
	public void drawStatus() {
		DetectiveActor detective = getDetective();
		// Box 1
		Calendar gameTime = ((EIDGame)player.getGame()).getGameTime(); 
		csi.print(49, 3, gameTime.get(Calendar.YEAR)+", " +months[gameTime.get(Calendar.MONTH)] +" "+ gameTime.get(Calendar.DATE));
		csi.print(49, 2, detective.getDescription());
	}
	
	private void drawAddornment(){
		int addornmentColor = ConsoleSystemInterface.RED;
		csi.print(0,  0, "/==============================================v===============================\\", addornmentColor);
		csi.print(0,  1, "|                                              |                               |", addornmentColor);
		csi.print(0,  2, "|                                              |                               |", addornmentColor);
		csi.print(0,  3, "|                                              |                               |", addornmentColor);
		csi.print(0,  4, "|                                              |                               |", addornmentColor);
		csi.print(0,  5, "|                                              |                               |", addornmentColor);
		csi.print(0,  6, "|                                              |                               |", addornmentColor);
		csi.print(0,  7, "|                                              |                               |", addornmentColor);
		csi.print(0,  8, "|                                              |                               |", addornmentColor);
		csi.print(0,  9, "|                                              |                               |", addornmentColor);
		csi.print(0, 10, "|                                              |                               |", addornmentColor);
		csi.print(0, 11, "|                                              |                               |", addornmentColor);
		csi.print(0, 12, "|                                              >===============================<", addornmentColor);
		csi.print(0, 13, "|                                              |                               |", addornmentColor);
		csi.print(0, 14, "|                                              |                               |", addornmentColor);
		csi.print(0, 15, "|                                              |                               |", addornmentColor);
		csi.print(0, 16, "|                                              |                               |", addornmentColor);
		csi.print(0, 17, "|                                              |                               |", addornmentColor);
		csi.print(0, 18, "|                                              |                               |", addornmentColor);
		csi.print(0, 19, "|                                              |                               |", addornmentColor);
		csi.print(0, 20, "|                                              |                               |", addornmentColor);
		csi.print(0, 21, "|                                              |                               |", addornmentColor);
		csi.print(0, 22, "|                                              |                               |", addornmentColor);
		csi.print(0, 23, "|                                              |                               |", addornmentColor);
		csi.print(0, 24, "\\= ELITE INTERNATIONAL DETECTIVE ==============^=Slashware Interactive 2011====/", addornmentColor);

		 
	}
	
	@Override
	public String getQuitPrompt() {
		return "Quit?";
	}

	@Override
	public void onMusicOn() {
		EIDLevel level = (EIDLevel)getPlayer().getLevel();
		if (level.getMusicKey() != null)
			STMusicManagerNew.thus.playKey(level.getMusicKey());
	}

	private DetectiveActor getDetective(){
		return (DetectiveActor)getPlayer();
	}
	
	private int readQuantity(int x, int y, String spaces, int inputLength){
		int quantity = -1;
		while (quantity == -1){
			csi.print(x,y,spaces);
			csi.refresh();
			csi.locateCaret(x, y);
			String strInput = csi.input(inputLength);
			if (strInput == null)
				continue;
			strInput = strInput.trim();
			try {
				quantity = Integer.parseInt(strInput);
			}catch (NumberFormatException e) {
			}
			if (quantity < 0)
				quantity = -1;
		}
		return quantity;
	}
	
	public void showBlockingMessage(String message) {
		TextBox chatBox = new TextBox(csi);
		chatBox.setHeight(8);
		chatBox.setWidth(50);
		chatBox.setPosition(15, 12);
		chatBox.setBorder(true);
		chatBox.setForeColor(ConsoleSystemInterface.WHITE);
		chatBox.setBorderColor(ConsoleSystemInterface.RED);
		chatBox.setText(message);
		chatBox.setTitle("[Space] to continue");
		chatBox.draw();
		csi.refresh();
		csi.waitKey(CharKey.SPACE);
	}
	
	public boolean promptChat(String message){
		return super.promptChat(message, 22,1,34,8);
	}

	@Override
	public int switchChat(String title, String prompt, String... options) {
		MenuBox selectionBox = new MenuBox(csi);
		selectionBox.setPosition(15,10);
		selectionBox.setWidth(50);
		selectionBox.setHeight(10);
		
  		Vector<MenuItem> menuItems = new Vector<MenuItem>();
  		int i = 0;
  		for (String option: options){
  			menuItems.add(new SimpleItem(i,option));
  			i++;
  		}
  		selectionBox.setMenuItems(menuItems);
  		selectionBox.setPromptSize(2);
  		selectionBox.setBorder(true);
  		selectionBox.setBorderColor(CSIColor.RED);
  		selectionBox.setTitle(title);
  		selectionBox.setPrompt(prompt);
  		selectionBox.draw();
  		
		while (true) {
			csi.refresh();
			SimpleItem itemChoice = ((SimpleItem)selectionBox.getSelection());
			if (itemChoice == null)
				break;
			return itemChoice.getValue();
		}
		return -1;	
	}
	
	public String inputBox(String prompt){
		return inputBox(prompt, 20, 2, 31, 8, 22, 6,20);
	}
	
	private class SimpleItem implements MenuItem{
		private String text;
		private int value;
	
		private SimpleItem (int value, String text){
			this.text = text;
			this.value = value;
		}
		
		public char getMenuChar() {
			return '*';
		}
		
		public int getMenuColor() {
			return ConsoleSystemInterface.WHITE;
		}
		
		public String getMenuDescription() {
			return text;
		}
		
		public int getValue(){
			return value;
		}
	}
	
	@Override
	public boolean drawIdList() {
		return true;
	}
	private EIDDisplay consoleDisplay; 
	
	private class EIDConsoleDisplay extends EIDDisplay {
		private ConsoleSystemInterface csi;
		
		public EIDConsoleDisplay(ConsoleSystemInterface csi){
			this.csi = csi;	
		}
		
		@Override
		public DetectiveActor createDetective(EIDGame game) {
			csi.cls();
			csi.print(1,1, "Logging into InterSleuth operative servers...        [Ok]");
			csi.print(1,2, "Detective at the keyboard, please identify yourself:");
			csi.locateCaret(2, 4);
			csi.refresh();
			String name = csi.input(15);
			csi.print(1,6, "There's no record of your name in the InterSleuth Network. ");
			csi.print(1,7, "Are you new here? (Y/N)");
			csi.refresh();
			CharKey key = csi.inkey();
			
			csi.print(1,9, "You have been identified as \""+name+"\".");
			csi.print(1,10, "Your current rank is Rookie.");
			csi.print(1,11, "Press space to continue.");
			csi.refresh();
			csi.waitKey(CharKey.SPACE);
			return PlayerFactory.generateDetective(name, PlayerFactory.Sex.MALE, game);
		}

		@Override
		public void showHelp() {
			
		}

		@Override
		public void showMission(DetectiveActor detective) {
			csi.cls();
			csi.print(1,1, "*** FLASH! ***");
			csi.print(1,3, "British Senate Representative McGregor has been murdered at");
			csi.print(1,4, "his mansion on Vancouver, Canada.");
			csi.print(1,6, "Terrorist organization Black Knights has taken responsability");
			csi.print(1,7, "for the event.");
			csi.print(1,9, "A female suspect was spotted at the crime scene.");
			csi.print(1,11, "Your assignment: Travel to Canada, track the murderer to his");
			csi.print(1,12, "hideout and neutralize him.");
			csi.print(1,14, "You have been given license to kill, you must complete your");
			csi.print(1,15, "mission by Sunday, 5 PM");
			csi.print(1,17, "Good Luck, "+detective.getDescription());
			csi.print(1,18, "Press space to continue.");
			csi.refresh();
			csi.waitKey(CharKey.SPACE);

		}

		@Override
		public int showSavedGames(File[] saves) {
			return 0;
		}

		@Override
		public int showTitleScreen() {
			csi.cls();
			csi.print(18, 4, "Elite International Detective", ConsoleSystemInterface.RED);
			csi.print(20,12, "a. Login", ConsoleSystemInterface.WHITE);
			csi.print(20,13, "b. Load", ConsoleSystemInterface.WHITE);
			csi.print(20,14, "c. Exit", ConsoleSystemInterface.WHITE);
			
			csi.print(8,17, "Version "+EIDGame.getVersion()+", Developed by Slashware Interactive 2011", ConsoleSystemInterface.RED);
			csi.refresh();
	    	STMusicManagerNew.thus.playKey("TITLE");
	    	CharKey x = new CharKey(CharKey.NONE);
			while (x.code != CharKey.A && x.code != CharKey.a &&
					x.code != CharKey.B && x.code != CharKey.b &&
					x.code != CharKey.C && x.code != CharKey.c)
				x = csi.inkey();
			csi.cls();
			switch (x.code){
			case CharKey.A: case CharKey.a:
				return 0;
			case CharKey.B: case CharKey.b:
				return 1;
			}
			return 0;
		}
		
	};

	public EIDDisplay getConsoleDisplay() {
		return consoleDisplay;
	}

	@Override
	public void showDetailedInfo(Actor a) {
		
	}

	@Override
	public void showInventory() {
		
	}
}
