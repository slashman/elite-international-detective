package net.slashware.eid.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.slashie.serf.action.ActionSelector;
import net.slashie.serf.action.NullSelector;
import net.slashie.serf.ai.EnemyAI;
import net.slashie.serf.ai.RangedActionSpec;
import net.slashie.serf.ai.SimpleAI;
import net.slashie.serf.level.AbstractCell;
import net.slashie.serf.level.AbstractFeature;
import net.slashie.utils.roll.Roll;
import net.slashware.eid.action.Walk;
import net.slashware.eid.entity.NPC;
import net.slashware.eid.entity.item.Clothing;
import net.slashware.eid.entity.item.ClueFeature;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.item.Gadget;
import net.slashware.eid.entity.item.Weapon;
import net.slashware.eid.entity.level.Country;
import net.slashware.eid.entity.level.Landmark;
import net.slashware.eid.entity.level.Location;
import net.slashware.eid.entity.level.SimpleLevelCell;
import net.slashware.eid.entity.mission.Criminal;
import net.slashware.eid.entity.mission.CriminalOrganization;

public class EIDData {
	public static AbstractCell[] getCellDefinitions (){
		return new AbstractCell[]{
			new SimpleLevelCell("FLOOR", "Floor", false, false),
			new SimpleLevelCell("WALL", "Wall", true, true),
			new SimpleLevelCell("BLUE_WALL", "Wall", true, true),
			new SimpleLevelCell("BAND", "Band", true, false),
			new SimpleLevelCell("V_GLASS", "Glass Panel", true, false),
			new SimpleLevelCell("H_GLASS", "Glass Panel", true, false),
			new SimpleLevelCell("DESK", "Desk", true, false),
			new SimpleLevelCell("DOOR", "Door", false, true),
			
			new SimpleLevelCell("STREET", "Street", false, false),
			new SimpleLevelCell("STREET_H_BAR", "Street", false, false),
			new SimpleLevelCell("STREET_V_BAR", "Street", false, false),
			new SimpleLevelCell("WALKWAY", "Walkway", false, false),
			new SimpleLevelCell("BUILDING_WALL", "Building", true, true),
			new SimpleLevelCell("BLIND_DOOR", "Door", true, true),
		};
	}
	
	public static EIDItem[] getItemDefinitions(){
		return new EIDItem[]{
			new Weapon("BERGMANN_MP18", "Bergmann MP18", new Roll(2,4), 5, 3, 5, "MACHINE_GUN","wav/Gun_Shot2.wav"),
			new Weapon("THOMPSON_M1921", "Thompson M1921", new Roll(3,6), 5, 3, 6, "MACHINE_GUN","wav/Gun_Shot2.wav"),
			new Weapon("WINCHESTHER_M12", "Winchester M12", new Roll(3,7), 5, 3, 1, "SHOTGUN","wav/Shotgun1.wav"),
			new Clothing("GREEN_RAINCOAT", "Cheap Green Raincoat",  new Roll(1,1), 3, 1),
			new Clothing("BLACK_RAINCOAT", "Black Raincoat",  new Roll(1,1), 3, 1),
			new Clothing("DARK_BLUE_SUIT", "Dark Blue Suit",  new Roll(1,1), 2, 3),
			new Clothing("DARK_BLACK_SUIT", "Dark Black Suit",  new Roll(1,1), 2, 3),
			new Clothing("RED_DRESS", "Red Dress",  new Roll(1,1), 2, 3),
			new Gadget("REMOTE_COM", "Remote Communication Device")
		};
		
	}

	public static NPC[] getNPCs() {
		ActionSelector NULL = new NullSelector();
		ActionSelector simpleWalk = new SimpleAI(null, new Walk());
		EnemyAI simpleEnemy = new EnemyAI(new Walk());
		ArrayList<RangedActionSpec> fireSpec = new ArrayList<RangedActionSpec>();
		RangedActionSpec e = new RangedActionSpec("Fire", 10, 100, "","");
		fireSpec.add(e);
		simpleEnemy.setRangedActions(fireSpec);
		
		
		return new NPC[]{
			new NPC("JEFF","Boss Jeff",true,simpleWalk,"THOMPSON_M1921","DARK_BLACK_SUIT",80,"Remember, we are working on the honor basis", "This gun is my welcome gift. Good luck!", "Kill only as a last resource"),
			new NPC("LALI","Lali",true,simpleWalk,null,"RED_DRESS",80,"Hello, sweetheart!"),
			new NPC("AGENT","Detective",false,simpleWalk,"WINCHESTHER_M12", "BLACK_RAINCOAT",40,"The bastards have pulled another caper!", "Where's the coffee machine?", "Welcome to the agency, rookie", "I need some tickets..."),
			new NPC("AIRPORT_LADY","Airport Lady",false,simpleWalk,null,null,5,"Good Day!"),
			new NPC("AIRPORT_GUY","Gentleman",false,simpleWalk,null,null,5,"Good Day!"),
			
			new NPC("INFORMANT","Informant",false,NULL,null,null,5,"I have some info that may come in handy..."),
			new NPC("SUSPECT","Suspect",false,NULL,null,null,5,". . ."),
			new NPC("WITNESS","Witness",false,NULL,null,null,5,"Please, protect me!"),
			new NPC("COP","Cop",false,simpleWalk,"WINCHESTHER_M12", "BLACK_RAINCOAT",20,"To protect and serve!"),
			new NPC("CRIMINAL","Criminal",false,simpleEnemy,"WINCHESTHER_M12", "BLACK_RAINCOAT",40,"Die!"),
			new NPC("LEADER","Criminal Leader",false,simpleEnemy,"THOMPSON_M1921", "DARK_BLACK_SUIT",80,"Die!"),
			new NPC("CIVILIAN","Civilian",false,simpleWalk,null,null,5,"Hello, Sir."),
			
			// Criminals
			new NPC("KORNEL_SANDIEGO","Kornel Sandiego",true,simpleEnemy,"WINCHESTHER_M12","DARK_BLACK_SUIT",2,"Chaos should reign!")
			
		};
	}


	
	public static Location[] getLocations(){
		Map<String, Country> countriesMap = new HashMap<String, Country>();
		Country[] countries = new Country[]{
			new Country("US", "The United States of America", "United States", "americans",
				new String[]{
					"stars over blue",
					"red stripes",
					"white stripes",
				},
				new String []{
					"bald eagle with its wings displayed",
					"13 arrows",
					"olive branch",
					"E pluribus unum",
					"blue chief, red and white stripes"
				}),
			new Country("HU", "The Hungarian Republic", "Hungary", "hungarian",
				new String[]{
					"red bar",
					"white bar",
					"green bar"
				},
				new String []{
					"black shockheaded dog (puli)",
					"fatty soup (gulas)",
					"carpathian basin"
				}),
			new Country("IC", "Republic of Iceland", "Iceland", "icelander",
				new String[]{
					"blue as the sky",
					"snow-white cross",
					"fiery-red cross inside the white cross"
				},
				new String []{
					"silver on a sky-blue shield",
					"the landvættir",
					"pahoehoe lava block",
					"bull, griffin, dragon and stone giant",
					"azure, on a cross argent a cross gules."
				}),			
			new Country("UK", "The United Kingdom of Great Britain and Northern Ireland", "United Kingdom", "british",
				new String[]{
					"union jack",
					"red cross of St. George",
					"cross of St. Patrick",
					"saltire of St. Andrew",
				},
				new String []{
					"three passant guardant lions",
					"rampant lion",
					"a harp",
					"statant guardant lion",
					"white unicorn",
					"Dieu et mon droit"
				}),		
			new Country("RU", "Russia", "Russia", "russian",
				new String[]{
					"white for the silver armor", //http://en.wikipedia.org/wiki/Flag_of_Russia , Meaning and origin of the colours
					"blue for his cape",
					"red for the field of battle"
				},
				new String []{
					"double headed eagle",
					"dragon slaying knight",
					"scepter and globus cruciger"
				}),
			new Country("CA", "Canada", "Canada", "canadian",
				new String[]{// flag clues 
					"maple leaf",
					"white surrounded by red"
				},
				new String []{
					"maple leaf",
					"three passant guardant lions",  //Same as UK, that could get messy ;\
					"rampant lion",
					"a harp",
					"statant guardant lion",
					"white unicorn",
					"Dieu et mon droit"
				}),
			new Country("BE", "Belgium", "Belgium", "belgian",
				new String[]{// flag clues 
					"red for blood",
					"black for death",
					"yellow for gold"
				},
				new String []{
					"l'union fait la force",
					"eendracht maakt macht",
					"nine provinces",
					"two lions supporting a banner"
				}),
			new Country("FR", "France", "France", "french",
				new String[]{// flag clues 
					"blue for Saint Martin",
					"red for Saint Denis",
					"white for Unity",
				},
				new String []{
					"wide shield",
					"head of a lion",
					"oak branch for wisdom",
					"laruel branch for victory"
				}),
			new Country("JP", "Japan", "Japan", "japanese",
				new String[]{// flag clues 
					"the sun",
					"red on white",
					"red disc",
				},
				new String []{
					"imperial seal",
					"chrysanthemum",
				}),
			new Country("CO", "Colombia", "Colombia", "colombian",
				new String[]{ // flag clues
					"yellow, generosity",
					"red, bravery",
					"blue, vigilance"
				},
				new String []{ // clues
					"vulture holding a banner",
					"two flags hanging sideways",
					"ships for a maritime history",
					"phrygian cap for for liberty and freedom",
				}),
			new Country("IN", "Republic of India", "India", "indian",
					new String[]{// flag clues 
					"bar of deep saffron",
					"bar of white",
					"bar of indian green",
					"many spoked wheel"
					},
					new String []{ // clues
					"four lions back to back",
					"a circular abacus",
					"the guardians of the four directions",
					"a lotus"
					}),
		};
		for (Country country: countries){
			countriesMap.put(country.getId(), country);
		}
		return new Location[]{
			new Location("US1", countriesMap.get("US"), "San Francisco", 
					"San Francisco is the financial, cultural, and transportation center of the San Francisco Bay Area and one the most important cities on the US west coast.", 
					"In 1776, the Spanish established a fort at the Golden Gate and a mission named for Francis of Assisi on the site. The California Gold Rush in 1848 propelled the city into a period of rapid growth, increasing the population in one year from 1,000 to 25,000.",
				new String[]{  // clues
				  
				},
				new Landmark [] {
					new Landmark("The Golden Gate Bridge", "one of the most heavily used bridges in the world")
				}
			),
			new Location("US2", countriesMap.get("US"), "Sacramento", 
					"Sacramento is the capital city of the U.S. state of California. It is located at the Sacramento River and the American River in the northern portion of California's expansive Central Valley.", 
					"Sacramento became a city through the efforts of John Sutter, a Swiss immigrant, and James W. Marshall. Sacramento grew quickly thanks to the protection of Sutter's Fort, which was established by Sutter in 1839.",
				new String[]{ // clues
				},
				new Landmark [] {
					new Landmark("The state capital of California", "a beautiful stone building built in a neoclassical style")
				}
			),
			new Location("UK1", countriesMap.get("UK"), "London", 
					"London is the capital of England and the United Kingdom. London is a leading global city, with strengths in the arts, commerce, education, entertainment, fashion, finance, healthcare, media, R&D and tourism all contributing to its prominence.", 
					"London has been a major settlement for two millennia, its history going back to its founding by the Romans, who called it Londinium.[6] London's ancient core, the City of London, largely retains its square-mile mediaeval boundaries.",
				new String[]{ // clues
				},
				new Landmark [] {
					new Landmark("The Buckingham Palace", "home of Queen Elizabeth II.")
				}
			),			
			new Location("RU1", countriesMap.get("RU"), "Moscow", 
					"Moscow is the capital and the most populous city of Russia. With the Kremlin at it's heart it is the largest city on the European continent.", 
					"In the course of its history the city has served as the capital of a progression of states, from the medieval Grand Duchy of Moscow and the subsequent Tsardom of Russia to the Soviet Union.",
				new String[]{ // clues
				},
				new Landmark [] {
					new Landmark("The Red Square", "historic city square in Moscow.")
				}
			),
			new Location("JP1", countriesMap.get("JP"), "Tokyo", 
					"Tokyo is the capital of Japan, the center of the Greater Tokyo Area, and the largest metropolitan area of Japan. It is the seat of the Japanese government and the Imperial Palace, and the home of the Japanese Imperial Family.", 
					"Tokyo was originally a small fishing village named Edo. It was first fortified by the Edo clan, in the late 12th century. It became the de facto capital of Japan even while the emperor lived in Kyoto, the imperial capital.",
				new String[]{ // clues
				},
				new Landmark [] {
					new Landmark("The Tokyo Tower", "a communications and observation tower, inspired by the Eiffel Tower.")
				}
			),
			new Location("IN1", countriesMap.get("IN"), "Mumbai", 
					"Mumbai is the capital of the Indian state of Maharashtra. It is the most populous city in India, and the second most populous city in the world, with a population of approximately 14 million.", 
					"Mumbai is built on what was once an archipelago of seven islands since the Stone Age. Since then these islands have gone through countless rulers and become unified under the British.",
				new String[]{ // clues
				},
				new Landmark [] {
					new Landmark("Gateway of India", "Basalt Arch built to commemorate the visit of King George V and Queen Mary.")
				}
			),
			new Location("FR1", countriesMap.get("FR"), "Paris", 
					"Paris is the capital and largest city in France, situated on the river Seine, in northern France. Considered the city of Light but also the city of Love it is one of the most influential cities in the world.",
					"Initially founded by celtics, it grew tremendously under the Romans. However it was the Industrial Revolution and the much later Universal Expositions that made Paris into a centre of technology, trade, and tourism",
				new String[]{ // clues
				},
				new Landmark [] {
					new Landmark("Eiffel Tower", "a popular monument standing 324 metres tall.")
				}
			),
			new Location("CA1", countriesMap.get("CA"), "Vancouver", 
					"Vancouver is a coastal city located in the Lower Mainland of British Columbia, Canada. It is the most populous city in Western Canada and the third-largest in the country.", 
					"The Fraser Gold Rush of 1858 brought over 25,000 men, mainly from California, to nearby New Westminster on the Fraser River, on their way to the Fraser Canyon, bypassing what would become Vancouver. Vancouver is among British Columbia's youngest cities.",
				new String[]{ // clues
				},
				new Landmark [] {
				new Landmark("The Canada Place", "the main cruise ship terminal for the region")
				}
			),
			new Location("CO1", countriesMap.get("CO"), "Medellín", 
					"description", 
					"history",
				new String[]{ // clues
				},
				new Landmark [] {
					new Landmark("The Coltejer Tower", "the tallest building of the region")
				}
			)
		};
	}

	public static AbstractFeature[] getFeatures() {
		return new ClueFeature[]{
			new ClueFeature()
		};
	}
}
