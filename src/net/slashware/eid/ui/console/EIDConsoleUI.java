package net.slashware.eid.ui.console;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.textcomponents.MenuBox;
import net.slashie.libjcsi.textcomponents.MenuItem;
import net.slashie.libjcsi.textcomponents.TextBox;
import net.slashie.serf.action.Action;
import net.slashie.serf.action.Actor;
import net.slashie.serf.action.Message;
import net.slashie.serf.game.Equipment;
import net.slashie.serf.sound.STMusicManagerNew;
import net.slashie.serf.ui.UserCommand;
import net.slashie.serf.ui.consoleUI.CharAppearance;
import net.slashie.serf.ui.consoleUI.ConsoleUserInterface;
import net.slashie.utils.Position;
import net.slashware.eid.EIDGame;
import net.slashware.eid.EIDUserInterface;
import net.slashware.eid.data.PlayerFactory;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.item.ItemType;
import net.slashware.eid.entity.level.EIDLevel;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.level.UrbanLevel;
import net.slashware.eid.entity.mission.Mission;
import net.slashware.eid.entity.player.DetectiveActor;
import net.slashware.eid.ui.CommonUI;
import net.slashware.eid.ui.EIDDisplay;


public class EIDConsoleUI extends ConsoleUserInterface implements EIDUserInterface{
	private static final String[] GENERAL_DIRECTION_DESCRIPTIONS = new String[]{
		"North",
		"South",
		"West",
		"East",
		"Northeast",
		"Northwest",
		"Southeast",
		"Southwest",
		""
	};
	
	private ConsoleSystemInterface csi;
			
	public EIDConsoleUI (ConsoleSystemInterface csi){
		this.csi = csi;
		consoleDisplay = new EIDConsoleDisplay(csi);
		POSITION_PICKER_COLOR = ConsoleSystemInterface.DARK_RED;
		POSITION_PICKER_TEXT_COLOR = ConsoleSystemInterface.WHITE;
		POSITION_PICKER_TIP_COLOR = ConsoleSystemInterface.RED;
	}
	
	public void init(ConsoleSystemInterface psi, UserCommand[] gameCommands, Action target){
		super.init(psi, gameCommands, target);
		VP_START = new Position(1,1);
		VP_END = new Position (21,21);
		PC_POS = new Position(10, 10);
		xrange = 9;
		yrange = 9;
		messageBox.setPosition(49,13);
		messageBox.setWidth(29);
		messageBox.setHeight(10);
		messageBox.setForeColor(ConsoleSystemInterface.WHITE);
		
		idList.setPosition(24,2);
		idList.setWidth(20);
		idList.setHeight(10);
	}
	
	@Override
	protected void beforeDrawStatus() {
		drawAddornment();
	}
	
	private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.US);
	@Override
	public void drawStatus() {
		DetectiveActor detective = getDetective();
		// Box 1
		Calendar gameTime = ((EIDGame)player.getGame()).getGameTime(); 
		csi.print(49, 1, detective.getDescription());
		csi.print(49, 2, (detective.getWeapon()!= null? detective.getWeapon().getDescription():"No Weapon"));
		csi.print(49, 3, (detective.getClothing()!= null? detective.getClothing().getDescription():"No Clothing (!)"));
		csi.print(49, 5, "Heroic Luck: "+detective.getLuckyPoints()+"/"+detective.getLuckyPointsMax());
		csi.print(49, 6, "Lethality: "+detective.getLethality().getDescription());
		csi.print(49, 7, "Movement: "+detective.getWalkingMode().getDescription());
		csi.print(49, 8, "Stamina: "+detective.getStamina()+"/"+detective.getStaminaMax());
		csi.print(3, 0, "   ~~ "+detective.getCurrentMission().getCrime().getTitle()+" ~~   ", CSIColor.RED);
		String targetPinpoint = "";
		if(getPlayer().getLevel() instanceof UrbanLevel){
			Position targetPosition = ((UrbanLevel)getPlayer().getLevel()).getTarget();
			Position playerPosition = getPlayer().getPosition();
			int generalDirection = Action.getGeneralDirection(playerPosition, targetPosition);
			if (generalDirection != Action.SELF){
				targetPinpoint = " (to the "+GENERAL_DIRECTION_DESCRIPTIONS[generalDirection]+")";
			}
		}
		csi.print(4, 20, detective.getLevel().getDescription()+targetPinpoint);
		csi.print(4, 21, detective.getLocation().getFullCityName());
		csi.print(4, 22, dateFormat.format(gameTime.getTime()));
		
	}
	
	@Override
	public void drawAfterCells(Position runner, int x, int y) {
		super.drawAfterCells(runner,x, y);
		Integer blood = ((EIDLevel)getDetective().getLevel()).getBloodAt(runner);
		if (blood != null){
			csi.print(x, y, '.', CSIColor.RED);
		}
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
		csi.print(0, 10, "|                                              |[m]ove  [g]rab  [z] walk speed |", addornmentColor);
		csi.print(0, 11, "|                                              |[f]ly   [u]se   [ ] fire       |", addornmentColor);
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
		csi.print(0, 24, "\\= ELITE INTERNATIONAL DETECTIVE ==============^==Slashware Interactive 2011===/", addornmentColor);

		 
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
		int xpos = 15, ypos = 12, width = 50, height = 8;
		
		TextBox chatBox = new TextBox(csi);
		chatBox.setHeight(height);
		chatBox.setWidth(width);
		chatBox.setPosition(xpos, ypos);
		chatBox.setBorder(true);
		chatBox.setForeColor(ConsoleSystemInterface.WHITE);
		chatBox.setBorderColor(ConsoleSystemInterface.DARK_RED);
		chatBox.setText(message);
		chatBox.draw();
		csi.print(xpos+30, ypos+height, "[Space] to continue", ConsoleSystemInterface.RED);
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
			csi.print(3,1, "Logging into InterSleuth operative servers...");
			csi.print(60,1, "[Ok]", ConsoleSystemInterface.LEMON);
			csi.print(3,2, "Detective at the keyboard, please identify yourself:");
			csi.print(5,4, "_");
			csi.locateCaret(5, 4);
			csi.refresh();
			String name = csi.input(15);
			if (name.trim().equals("")){
				name = "Jack";
			}
			name = name.trim();
			csi.print(3,6, "There's no record of your name in the InterSleuth Network. ");
			csi.print(3,7, "Are you new here? (Y/N)");
			csi.refresh();
			CharKey key = csi.inkey();
			
			csi.print(3,9, "You have been identified as \""+name+"\".");
			csi.print(3,10, "Your current rank is Rookie.");
			csi.print(3,11, "Press space to continue.");
			csi.refresh();
			csi.waitKey(CharKey.SPACE);
			return PlayerFactory.generateDetective(name, PlayerFactory.Sex.MALE, game);
		}

		@Override
		public void showHelp() {
			
		}

		@Override
		public void showMission(DetectiveActor detective, Mission mission) {
			String missionDescription = CommonUI.composeMissionDescription(detective, mission);
			missionDescription += " XXX XXX Press Space to Continue";
			csi.cls();
			csi.print(3,1, "*** FLASH! ***", ConsoleSystemInterface.RED);
			csi.refresh();
			
			showTextBox(missionDescription, 3, 3, 75, 19, CSIColor.BLACK);

		}

		@Override
		public int showSavedGames(File[] saves) {
			return 0;
		}

		@Override
		public void showFlight(Location location, Location destination) {
			csi.cls();
			csi.print(8, 2, "                     \\            ", ConsoleSystemInterface.RED);                  
			csi.print(8, 3, "               ,-  >--->          ", ConsoleSystemInterface.RED);                  
			csi.print(8, 4, "             ,/      /            ", ConsoleSystemInterface.RED);                  
			csi.print(8, 5, "           ,/                     ", ConsoleSystemInterface.GRAY);                  
			csi.print(8, 6, "         ,/                       ", ConsoleSystemInterface.GRAY);                  
			csi.print(8, 7, "     ,.-´                         ", ConsoleSystemInterface.GRAY);                  
			csi.print(8, 8, "    o                                o", ConsoleSystemInterface.BLUE);
			csi.print(8,10, location.getFullCityName(), ConsoleSystemInterface.WHITE);
			csi.print(40,10, destination.getFullCityName(), ConsoleSystemInterface.WHITE);

			
			printTextBox(destination.getHistory(), 2, 11, 35, 10, CSIColor.BLACK);
			printTextBox(destination.getDescription(), 38, 11, 35, 10, CSIColor.BLACK);

			csi.refresh();
			csi.waitKey(CharKey.SPACE);

		}
		
		@Override
		public int showTitleScreen() {
			csi.cls();
			int xstart = 42;
			int ystart = 5;
			csi.print(xstart, ystart+ 0, "            /^\\/^\\", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 1, "            \\----|", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 2, "        _---'---~~~~-_", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 3, "         ~~~|~~L~|~~~~", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 4, "            (/_  /~~--", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 5, "          \\~ \\  /  /~", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 6, "        __~\\  ~ /   ~~----,", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 7, "        \\    | |       /  \\", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 8, "        /|   |/       |    |", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+ 9, "        | | | o  o     /~   |", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+10, "      _-~_  |        ||  \\  /", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+11, "     (// )) | o  o    \\\\---'", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+12, "     //_- |  |          \\", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+13, "    //   |____|\\______\\__\\", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+14, "    ~      |   / |    |", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+15, "            |_ /   \\ _|", ConsoleSystemInterface.DARK_RED);
			csi.print(xstart, ystart+16, "          /~___|  /____\\", ConsoleSystemInterface.DARK_RED); 
			
			csi.print(8, 4, "Elite International Detective", ConsoleSystemInterface.RED);
			csi.print(10, 6, "a. login into InterSleuth Servers", ConsoleSystemInterface.WHITE);
			csi.print(10, 7, "b. exit", ConsoleSystemInterface.WHITE);
			
			csi.print(8,19, "Version "+EIDGame.getVersion()+", Developed by Slashware Interactive 2011", ConsoleSystemInterface.RED);
			csi.refresh();
	    	STMusicManagerNew.thus.playKey("TITLE");
	    	CharKey x = new CharKey(CharKey.NONE);
			while (x.code != CharKey.A && x.code != CharKey.a &&
					x.code != CharKey.B && x.code != CharKey.b)
				x = csi.inkey();
			csi.cls();
			switch (x.code){
			case CharKey.A: case CharKey.a:
				return 0;
			case CharKey.B: case CharKey.b:
				return 2;
			}
			return 0;
		}
		
		@Override
		public void showLevelMap(UrbanLevel level) {
			int mx = 27, my = 0;
			char[][] charMap = level.getCharMap();
			csi.cls();
			// Levels are 100 x 100, scale them to 25 x 25
			//char[][] scaledMap = new char[20][20];
			for (int xgroup = 0; xgroup < 24; xgroup++){
				for (int ygroup = 0; ygroup < 24; ygroup++){
					int streets = countStreets(charMap, xgroup, ygroup, 4);
					if (streets > 0){
						csi.print(mx+xgroup, my+ygroup, '.', ConsoleSystemInterface.GRAY);
					} else {
						csi.print(mx+xgroup, my+ygroup, '#', ConsoleSystemInterface.TEAL);
					}
				}
			}
			
			Position start = level.getStart();
			Position target = level.getTarget();
			csi.print(mx+(int)Math.floor((double)start.x/4.0d), my+(int)Math.floor((double)start.y/4.0d), ">", ConsoleSystemInterface.RED);
			csi.print(mx+(int)Math.floor((double)target.x/4.0d), my+(int)Math.floor((double)target.y/4.0d), "<", ConsoleSystemInterface.RED);
			
			csi.refresh();
			csi.waitKey(CharKey.SPACE);
		}

		private int countStreets(char[][] charMap, int xgroup, int ygroup, int i) {
			int count = 0;
			for (int x = xgroup * i; x < xgroup*i + i; x++)
				for (int y = ygroup * i; y < ygroup*i + i; y++)
					if (charMap[y][x] == '.'){
						count++;
					}
			return count;
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
		Equipment.eqMode = true;
		int xpos = 8, ypos = 3;
  		MenuBox menuBox = new MenuBox(csi);
  		menuBox.setHeight(17);
  		menuBox.setWidth(71);
  		menuBox.setPosition(8,7);
  		menuBox.setBorder(true);
  		TextBox itemDescription = new TextBox(csi);
  		itemDescription.setBounds(52,9,25,5);
  		csi.saveBuffer();
  		csi.print(xpos,24,  "[Space] to continue, Up and Down to browse");
  		int choice = 0;
  		while (true){
  			csi.print(xpos,ypos+2,  "  __________     __________     _________      __________ ", ConsoleSystemInterface.WHITE);
  			csi.print(xpos,ypos+3,  " / Weapons  \\   / Clothing \\   / Gadgets  \\   / Misc     \\", ConsoleSystemInterface.WHITE);
  	  		List<Equipment> inventory = null;
  	  		switch (choice){
  	  		case 0:
  	  			inventory = getDetective().getInventory(ItemType.WEAPON);
  	  			break;
  	  		case 1:
  	  		inventory = getDetective().getInventory(ItemType.CLOTHING);
  	  			break;
  	  		case 2:
  	  		inventory = getDetective().getInventory(ItemType.GADGET);
  	  			break;
  	  		case 3:
  	  		inventory = getDetective().getInventory(ItemType.MISC);
  	  			break;
  	  		}
  	  		
  	  		Vector menuItems = new Vector();
  	  		for (Equipment item: inventory){
  	  			menuItems.add(new InventoryItem(item, getDetective()));
  	  		}
  	  		
  	  		menuBox.setMenuItems(menuItems);
  	  		menuBox.draw();
  	  		csi.print(xpos+choice*15, ypos+4, "/            \\", ConsoleSystemInterface.WHITE);
  	  		csi.refresh();
  	  		
	  		CharKey x = new CharKey(CharKey.NONE);
			while (x.code != CharKey.SPACE && !x.isArrow())
				x = csi.inkey();
			if (x.code == CharKey.SPACE || x.code == CharKey.ESC){
				break;
			}
			if (x.isLeftArrow()){
				choice--;
				if (choice == -1)
					choice = 0;
			}
			if (x.isRightArrow()){
				choice++;
				if (choice == 4)
					choice = 3;
			}
  		}
		csi.restore();
		csi.refresh();

	}
	
	private class InventoryItem implements MenuItem{
		private Equipment item;
		private DetectiveActor detective;

		public InventoryItem(Equipment item, DetectiveActor detective) {
			this.item = item;
			this.detective = detective;
		}
		
		public Equipment getEquipment(){
			return item;
		}

		public char getMenuChar() {
			return ((CharAppearance)item.getItem().getAppearance()).getChar();
		}
		
		public int getMenuColor() {
			return ((CharAppearance)item.getItem().getAppearance()).getColor();
		}
		
		public String getMenuDescription() {
			String itemDescription = item.getItem().getDescription();
			int quantity = item.getQuantity();
			EIDItem eitem = (EIDItem)item.getItem();
			switch (eitem.getItemType()){
			case WEAPON:
				return quantity + " " + itemDescription + " Damage "+ eitem.getAttack().getString()+" Range "+ eitem.getRange()+" Spread " + eitem.getSpread();
			case CLOTHING:
				return quantity + " " + itemDescription + " Armor "+ eitem.getArmor().getString()+" Coolness "+ eitem.getCoolness() +" Disguise "+eitem.getDisguise();
			case GADGET:
				return quantity + " " + itemDescription;
			case MISC:
				return quantity + " " + itemDescription;
			}
			return "N/A";
		}
	}
}
