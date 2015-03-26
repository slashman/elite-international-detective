**Note: If you want to contribute, just contact me! and I'll add you as a contributor!**

You can also contribute through the online forms! http://slashware.net/blog/?p=1226

# Scheme description #
  * Cities are grouped in countries, each one with these attributes
    * id: An unique country identifier, uppercade two char ISO is preferred
    * longName: Long official name of the country
    * shortName: Commonly used name
    * adjective: How is people from this country called
    * flagClues: List of clues for the flag of the country, they are used inside the game as information to guess the next destination
    * insigniaClues: List of clues for insignias of the country (coat of arms, seal, motto, etc), they are used inside the game as information to guess the next destination
    * cities: A list of cities, see description below
  * Cities are an attribute of the countries and have the following attributes
    * id: UN/LOCODE city identifier http://www.unece.org/cefact/locode/service/location.htm
    * name: Name of the country
    * lat: latitude, examples: "37°46'45.48N", "37°46'45N"
    * long: longitude, examples: "122°25'9.12W", "122°25'9W"
    * history: Textual description of the country history, it's shown as info when traveling between locations.
    * description: San Francisco is a popular international tourist destination, renowned for its chilly summer fog, steep rolling hills, eclectic mix of Victorian and modern architecture and its famous landmarks, including the Golden Gate Bridge, cable cars, and Chinatown.
  * insigniaClues: List of clues for insignias of the city (nicknames, motto, landmark nicknames, etc), they are used inside the game as information to guess the next destination
  * landMarks: List of landmarks of the city, each one with a name and a foot note for the landmark (for example name: "Statue of Liberty", footnote: "a symbol of freedom to the people of the United States."

# Current Cities #
  * Iceland - Slashie
    * Reykjavík - Slashie
  * United Kingdom - Slashie
    * London - Geoffrey White
  * United States - Slashie
    * Sacramento - EligZero
    * San Francisco - Slashie
    * New York - Geoffrey White
  * Russia - Geoffrey White
    * Moscow - Geoffrey White
  * France - Geoffrey White
    * Paris - Geoffrey White
  * Japan - Geoffrey White
    * Tokyo - Geoffrey White
  * India - Geoffrey White
    * Mumbai - Geoffrey White

# JSON Location Data #
```
"locationData": [
{
	"id": "US",
	"longName": "The United States of America",
	"shortName": "United States",
	"adjective": "american",
	"flagClues": [
		"stars over blue",
		"red stripes",
		"white stripes"
	],
	"insigniaClues": [
		"bald eagle with its wings displayed",
		"13 arrows",
		"olive branch",
		"E pluribus unum",
		"blue chief, red and white stripes"
	],
	"cities" : [
	      {
	         "id": "SF-US",
	         "name": "San Francisco",
	         "lat": "37°46'45.48N",
	         "long": "122°25'9.12W",
	         "history": "In 1776, the Spanish established a fort at the Golden Gate and a mission named for Francis of Assisi on the site. The California Gold Rush in 1848 propelled the city into a period of rapid growth, increasing the population in one year from 1,000 to 25,000",
	         "description": "San Francisco is a popular international tourist destination, renowned for its chilly summer fog, steep rolling hills, eclectic mix of Victorian and modern architecture and its famous landmarks, including the Golden Gate Bridge, cable cars, and Chinatown."
	         "insigniaClues": [
	         	"the paris of the west",
	         	"gold in peace, iron in war",
	         	"the city by the bay",
	         	"the fog city"
	         ],
	         "landMarks": [
	         	{
	         	    "name": "The Golden Gate Bridge",
	         	    "footnote", "one of the most heavily used bridges in the world"
	         	}
	         ]
	      },
             {
             "id": "NY-US",
             "name": "New York City",
             "lat": "40°43'N",
             "long": "74°0'W",
             "history": "Settled in 1524 and then again in 1614, it wasn't until 1664 that the city was surrendered to the English and finally became known as 'New York'.  In 2001 the World Trade Center in New York was destroyed by a terrorist attack, leading to the deaths of nearly 3000 people.",
             "description": "New York City is the most populous city in the United States, and the most linguistically diverse city in the world.  It's also a hub for international business and commerce."
             ],
             "landMarks": [
                 {
                     "name": "Empire State Building",
                     "footnote", "the tallest building in the world for over 40 years."
                 },
                 {
                     "name": "Statue of Liberty",
                     "footnote", "a symbol of freedom to the people of the United States."
                 }
             ]
          },
          {
                "id": "SAC-US",
                "name": "Sacramento",
                "lat": "38°33'20N",
                "long": "121°28'8W",
                "history": "Southern Maidu Native Americans lived in the area for thousands of years before the Spanish explorer Gabriel Moraga named it Sacramento, famously saying 'Es como el sagrado sacramento!' (This is like the Holy Sacrament). In 1848 when gold was discovered at Sutter's Mill in Coloma, many people immigrated to Sacramento in search of gold that most would never find. These failed prospectors along with local farmers and trappers formed the City of Sacramento in 1849 at the intersection of the Sacramento and American rivers. In 1863, Sacramento became the final western destination of the US's first transcontinental railroad. By 1879, Sacramento was named the permanent state capital of California. At the time, it was home to only 21,420 people.",
                "description": "Sacramento is the state capital of California. It sits at the intersection of the Sacramento and American rivers, and enjoys a Mediterranean climate in the Sacramento Valley filled with oak trees and tall yellow grass. Summers are regularly above 109 degrees Fahrenheit, but it almost never snows in the winter, preferring to rain heavily from October to January. It is home to the beautiful California state capital building, and has a famously boring nickname: the City of Trees. It is an accurate description."
                "insigniaClues": [
                       "the city of trees",
                       "state capital of the golden state"
                ],
                "landMarks": [
                       {
                           "name": "The state capital of California",
                           "footnote", "a beautiful stone building built in a neoclassical style"
                       }
                ]
             }

   	]
},
{
	"id": "IC",
	"longName": "Republic of Iceland",
	"shortName": "Iceland",
	"adjective": "icelander",
	"cities" : [
	      {
	         "id": "SF-US",
	         "countryId": "US",
	         "name": "Reykjavík",
	         "history": "Reykjavík is believed to be the location of the first permanent settlement in Iceland, which Ingólfur Arnarson is said to have established around 870. Until the 18th century, there was no urban development  in the city location. The city was founded in 1786 as an official trading town and grew steadily over the next decades, as it transformed into a regional and later national centre of commerce, population and governmental activities.",
	         "description": "Reykjavík is often dubbed \"the nightlife capital of the north\". It is famous for its nightlife during the weekends. Icelanders tend to go out late so bars that look rather quiet can fill up suddenly usually after midnight on a weekend.."
	      },
   	]	
},
{
	"id": "UK",
	"longName": "The United Kingdom of Great Britain and Northern Ireland",
	"shortName": "United Kingdom",
	"adjective": "british",
        "flagClues": [
            "Union Jack",
        ],
	"cities" : [
          {
             "id": "LN-UK",
             "name": "London",
             "lat": "51°30'26N",
             "long": "0°7'39W",
             "history": "Founded by the Romans, London grew in size and population to become a world leading city in the 19th century.  Despite a history of plague, fire, war and terrorism it remains so today."
             "description": "London is the largest city in the UK, and a major financial centre.  It's also home to a variety of tourist destinations including Buckingham Palace, Tower Bridge, and a number of national museums."
             "landMarks": [
                 {
                     "name": "Buckingham Palace",
                     "footnote", "home of Queen Elizabeth II."
                 },
                 {
                     "name": "The London Eye",
                     "footnote", "a popular tourist attraction."
                 }
                 {
                     "name": "Tower Bridge",
                     "footnote", "a famous suspension bridge over the River Thames."
                 }
             ]
          },
       ]

},
{
    "id": "RS",
    "longName": "Russia",
    "shortName": "Russia",
    "adjective": "russian",
    "cities" : [
          {
             "id": "MO-RS",
             "countryId": "RS",
             "name": "Moscow",
             "lat": "55°45'06N",
             "long": "37°37'04E",
             "history": "Founded around 1147, Moscow has prospered despite numerous takeovers throughout it's history.  Since the collapse of the Soviet Union in 1991, Moscow has embraced capitalism and increasingly western lifestyles."
             "description": "Moscow is the capital city of Russia.  It is the country's largest city and is responsible for approximately 24% of Russian GDP, despite being home to only 7% of the population."
             "landMarks": [
                 {
                     "name": "Red Square",
                     "footnote", "Historic city square in Moscow."
                 },
                 {
                     "name": "Moscow State University",
                     "footnote", "The largest university in Russia."
                 }
             ]
          },
       ]
},
{
    "id": "FR",
    "longName": "France",
    "shortName": "France",
    "adjective": "french",
    "flagClues": [
        "tricolour",
    ],
    "cities" : [
          {
             "id": "PA-FR",
             "countryId": "FR",
             "name": "Paris",
             "lat": "48°51.4'N",
             "long": "2°21.05'W",
             "history": "The city of Paris has over 2000 years of history, perhaps most famously including the Storming of the Bastille during the French Revolution in 1789.  Paris underwent a massive industrial transformation in the 19th century, during which distinctive wide boulevards were built right into the heart of the city under Napoleon III."
             "description": "Paris is the capital city of France and the seat of it's national government.  It's a popular tourist destination famous for art, culture and fashion."
             "landMarks": [
                 {
                     "name": "Eiffel Tower",
                     "footnote", "Popular monument standing 324 metres tall."
                 },
                 {
                     "name": "Arc de Triomphe",
                     "footnote", "Monument in honour of those who died in the French Revolution and the Napoleonic Wars."
                 }
             ]
          },
       ]
},
{
    "id": "JP",
    "longName": "Japan",
    "shortName": "Japan",
    "adjective": "japanese",
    "flagClues": [
        "sun disc",
    ],
    "cities" : [
          {
             "id": "TK-JP",
             "countryId": "JP",
             "name": "Tokyo",
             "lat": "35°42'2N",
             "long": "139°42'54E",
             "history": "Originally a small fishing village, Tokugawa leyasu chose Tokyo (then Edo) as his base when he became shogun in 1603.  It grew into one of the largest cities in the world, and is now part of the world's most populous metropolitan area."
             "description": "Tokyo is the capital of Japan.  It's famous for it's high population density and love of technology."
             "landMarks": [
                 {
                     "name": "Tokyo Tower",
                     "footnote", "Communications and observation tower, inspired by the Eiffel Tower."
                 }
             ]
          },
       ]
},
{
    "id": "IN",
    "longName": "Republic of India",
    "shortName": "India",
    "adjective": "indian",
    "cities" : [
          {
             "id": "MU-IN",
             "countryId": "IN",
             "name": "Mumbai",
             "lat": "18°58'30N",
             "long": "72°49'33E",
             "history": "Mumbai, formerly known as Bombay, is built on a collection of seven islands that were connected through civil engineering projects in the 18th and 19th centuries.  It has undergone massive expansion since India's independence from British rule in 1947."
             "description": "Mumbai is the most populous city in India, a thriving commercial centre and the home of Bollywood.  However there is also widespread poverty and many people are forced to live in the slums."
             "landMarks": [
                 {
                     "name": "Gateway of India",
                     "footnote", "Basalt Arch built to commemorate the visit of King George V and Queen Mary."
                 }
             ]
          },
       ]
},
]

```