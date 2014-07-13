package org.openbakery.jinsim;

public enum Track {

	BLACKWOOD_GP("000", "Blackwood GP", "BL1", 3 ),
    BLACKWOOD_GP_REVERSE("001", "Blackwood GP Reverse", "BL1R", 3),
    BLACKWOOD_GP_OPEN("002", "Blackwood GP Open", "BL1X", 0),
    BLACKWOOD_GP_OPEN_REVERSE("002", "Blackwood GP Open Reverse", "BL1Y", 0),

    BLACKWOOD_RALLYX("010", "Blackwood Rallyx", "BL2", 2),
    BLACKWOOD_RALLYX_REVERSE("011", "Blackwood Rallyx Reverse", "BL2R", 2),
    BLACKWOOD_RALLYX_OPEN("012", "Blackwood Rallyx Reverse", "BL2X", 0),
    BLACKWOOD_RALLYX_OPEN_REVERSE("012", "Blackwood Rallyx Reverse", "BL2Y", 0),

    BLACKWOOD_CAR_PARK_OPEN("021", "Blackwood Car Park Open", "BL3X", 0),

    SOUTH_CITY_CLASSIC("100", "South City Classic", "SO1", 2),
    SOUTH_CITY_CLASSIC_REVERSE("101", "South City Classic Reverse", "SO1R", 2),
    SOUTH_CITY_CLASSIC_OPEN("102", "South City Classic Open", "SO1X", 0),
    SOUTH_CITY_CLASSIC_OPEN_REVERSE("103", "South City Classic Open Reverse", "SO1Y", 0),

    SOUTH_CITY_SPRINT_TRACK_1("110", "South City Sprint Track 1", "SO2", 2),
    SOUTH_CITY_SPRINT_TRACK_1_REVERSE("111", "South City Sprint Track 1 Reverse", "SO2R", 2),
    SOUTH_CITY_SPRINT_TRACK_1_OPEN("112", "South City Sprint Track 1 Open", "SO2X", 0),
    SOUTH_CITY_SPRINT_TRACK_1_OPEN_REVERSE("113", "South City Sprint Track 1 Open Reverse", "SO2Y", 0),

    SOUTH_CITY_SPRINT_TRACK_2("120", "South City Sprint Track 2", "SO3", 2),
    SOUTH_CITY_SPRINT_TRACK_2_REVERSE("121", "South City Sprint Track 2 Reverse", "SO3R", 2),
    SOUTH_CITY_SPRINT_TRACK_2_OPEN("122", "South City Sprint Track 2 Open", "SO3X", 0),
    SOUTH_CITY_SPRINT_TRACK_2_OPEN_REVERSE("123", "South City Sprint Track 2 Open Reverse", "SO3Y", 0),

    SOUTH_CITY_LONG("130", "South City Long", "SO4", 3),
    SOUTH_CITY_LONG_REVERSE("131", "South City Long Reverse", "SO4R",  3),
    SOUTH_CITY_LONG_OPEN("132", "South City Long Open", "SO4X",  0),
    SOUTH_CITY_LONG_OPEN_REVERSE("133", "South City Long Open Reverse", "SO4Y",  0),

    SOUTH_CITY_TOWN_COURSE("140", "South City Town Course", "SO5", 3),
    SOUTH_CITY_TOWN_COURSE_REVERSE("141", "South City Town Course Reverse", "SO5R", 3),
    SOUTH_CITY_TOWN_COURSE_OPEN("142", "South City Town Course Open", "SO5X", 0),
    SOUTH_CITY_TOWN_COURSE_OPEN_REVERSE("143", "South City Town Course Open Reverse", "SO5Y", 0),

    SOUTH_CITY_CHICANE_ROUTE("150", "South City Chicane Route ", "SO6", 2),
    SOUTH_CITY_CHICANE_ROUTE_REVERSE("151", "South City Chicane Route Reverse", "SO6R", 2),
    SOUTH_CITY_CHICANE_ROUTE_OPEN("152", "South City Chicane Route Open", "SO6X", 0),
    SOUTH_CITY_CHICANE_ROUTE_OPEN_REVERSE("153", "South City Chicane Route Open Reverse", "SO6Y", 0),

	FERN_BAY_CLUB("200", "Fern Bay Club", "FE1", 2),
    FERN_BAY_CLUB_REVERSE("201", "Fern Bay Club Reverse", "FE1R", 2),
    FERN_BAY_CLUB_OPEN("202", "Fern Bay Club Open", "FE1X", 0),
    FERN_BAY_CLUB_OPEN_REVERSE("203", "Fern Bay Club Open Reverse", "FE1Y", 0),

    FERN_BAY_GREEN("210", "Fern Bay Green", "FE2", 3),
    FERN_BAY_GREEN_REVERSE("211", "Fern Bay Green Reverse", "FE2R", 3),
    FERN_BAY_GREEN_OPEN("212", "Fern Bay Green Open", "FE2X", 0),
    FERN_BAY_GREEN_OPEN_REVERSE("212", "Fern Bay Green Open Reverse", "FE2Y", 0),

	FERN_BAY_GOLD("220", "Fern Bay Gold", "FE3", 3),
    FERN_BAY_GOLD_REVERSE("221", "Fern Bay Gold Reverse", "FE3R", 3),
    FERN_BAY_GOLD_OPEN("222", "Fern Bay Gold Open", "FE3X", 0),
    FERN_BAY_GOLD_OPEN_REVERSE("223", "Fern Bay Gold Open Reverse", "FE3Y", 0),

	FERN_BAY_BLACK("230", "Fern Bay Black", "FE4", 4),
    FERN_BAY_BLACK_REVERSE("231", "Fern Bay Black Reverse", "FE4R", 4),
    FERN_BAY_BLACK_OPEN("232", "Fern Bay Black Open", "FE4X", 0),
    FERN_BAY_BLACK_OPEN_REVERSE("233", "Fern Bay Black Open Reverse", "FE4Y", 0),

	FERN_BAY_RALLYCROSS("240", "Fern Bay Rallycross", "FE5", 2),
    FERN_BAY_RALLYCROSS_REVERSE("241", "Fern Bay Rallycross Reverse", "FE5R", 2),
    FERN_BAY_RALLYCROSS_OPEN("242", "Fern Bay Rallycross Open", "FE5X", 0),
    FERN_BAY_RALLYCROSS_OPEN_REVERSE("243", "Fern Bay Rallycross Open Reverse", "FE5Y", 0),

	FERN_BAY_RALLYX_GREEN("250", "Fern Bay Rallyx Green", "FE6", 2),
    FERN_BAY_RALLYX_GREEN_REVERSE("251", "Fern Bay Rallyx Green Reverse", "FE6R", 2),
    FERN_BAY_RALLYX_GREEN_OPEN("252", "Fern Bay Rallyx Green Open", "FE6X", 0),
    FERN_BAY_RALLYX_GREEN_OPEN_REVERSE("253", "Fern Bay Rallyx Green Open Reverse", "FE6Y", 0),


    AUTOCROSS("300","Autocross", "AU1", 0),
	AUTOCROSS_OPEN("301","Autocross Open", "AU1X", 0),
	
	SKID_PAD("310", "Skid Pad", "AU2", 0),
	SKID_PAD_OPEN("311", "Skid Pad Open", "AU2X", 0),
	
	DRAG_STRIP("320", "Drag Strip", "AU3", 1),
	DRAG_STRIP_OPEN("321", "Drag Strip Open", "AU3X", 0),
	
	EIGHT_LANE_DRAG("330", "Eight Lane Drag", "AU4", 1),
    EIGHT_LANE_DRAG_OPEN("331", "Eight Lane Drag Open", "AU4X", 0),

    KYOTO_RING_OVAL("400", "Kyoto Ring Oval", "KY1", 2),
    KYOTO_RING_OVAL_REVERSE("401", "Kyoto Ring Oval Reverse", "KY1R", 2),
    KYOTO_RING_OVAL_OPEN("402", "Kyoto Ring Oval Open", "KY1X", 0),
    KYOTO_RING_OVAL_OPEN_REVERSE("403", "Kyoto Ring Oval Open Reverse", "KY1Y", 0),

	KYOTO_RING_NATIONAL("410", "Kyoto Ring National", "KY2", 3),
    KYOTO_RING_NATIONAL_REVERSE("411", "Kyoto Ring National Reverse", "KY2R", 3),
    KYOTO_RING_NATIONAL_OPEN("412", "Kyoto Ring National Open", "KY2X", 0),
    KYOTO_RING_NATIONAL_OPEN_REVERSE("413", "Kyoto Ring National Open Reverse", "KY2Y", 0),


	KYOTO_RING_GP_LONG("420", "Kyoto Ring GP Long", "KY3", 3),
    KYOTO_RING_GP_LONG_REVERSE("421", "Kyoto Ring GP Long Reverse", "KY3R", 3),
    KYOTO_RING_GP_LONG_OPEN("422", "Kyoto Ring GP Long Open", "KY3X", 0),
    KYOTO_RING_GP_LONG_OPEN_REVERSE("423", "Kyoto Ring GP Long Open Reverse", "KY3Y", 0),


	WESTHILL_INTERNATIONAL("500", "Westhill International", "WE1", 3),
    WESTHILL_INTERNATIONAL_REVERSE("501", "Westhill International Reverse", "WE1R", 3),
    WESTHILL_INTERNATIONAL_OPEN("502", "Westhill International Open", "WE1X", 0),
    WESTHILL_INTERNATIONAL_OPEN_REVERSE("503", "Westhill International Open Reverse", "WE1Y", 0),


    ASTON_CADET("600", "Aston Cadet", "AS1", 2),
    ASTON_CADET_REVERSE("601", "Aston Cadet Reverse", "AS1R", 2),
    ASTON_CADET_OPEN("602", "Aston Cadet Open", "AS1X", 0),
    ASTON_CADET_OPEN_REVERSE("603", "Aston Cadet Open Reverse", "AS1Y", 0),

	ASTON_CLUB("610", "Aston Club", "AS2", 2),
    ASTON_CLUB_REVERSE("611", "Aston Club Reverse", "AS2R", 2),
    ASTON_CLUB_OPEN("612", "Aston Club Reverse", "AS2X", 0),
    ASTON_CLUB_OPEN_REVERSE("613", "Aston Club Reverse", "AS2Y", 0),

	ASTON_NATIONAL("620", "Aston National", "AS3", 2),
    ASTON_NATIONAL_REVERSE("621", "Aston National Reverse", "AS3R", 2),
    ASTON_NATIONAL_OPEN("622", "Aston National Open", "AS3X", 0),
    ASTON_NATIONAL_OPEN_REVERSE("623", "Aston National Open Reverse", "AS3Y", 0),


	ASTON_HISTORIC("630", "Aston Historic", "AS4", 3),
    ASTON_HISTORIC_REVERSE("641", "Aston Historic Reverse", "AS4R",  3),
    ASTON_HISTORIC_OPEN("642", "Aston Historic Reverse", "AS4X",  0),
    ASTON_HISTORIC_OPEN_REVERSE("643", "Aston Historic Reverse", "AS4Y",  0),

	ASTON_GRAND_PRIX("650", "Aston Grand Prix", "AS5", 3),
    ASTON_GRAND_PRIX_REVERSE("651", "Aston Grand Prix Reverse", "AS5R", 3),
    ASTON_GRAND_PRIX_OPEN("652", "Aston Grand Prix Open", "AS5X", 0),
    ASTON_GRAND_PRIX_OPEN_REVERSE("653", "Aston Grand Prix Open Reverse", "AS5Y", 0),

	ASTON_GRAND_TOURING("660", "Aston Grand Touring", "AS6", 4),
    ASTON_GRAND_TOURING_REVERSE("661", "Aston Grand Touring Reverse", "AS6R", 4),
    ASTON_GRAND_TOURING_OPEN("662", "Aston Grand Touring Open", "AS6X", 0),
    ASTON_GRAND_TOURING_OPEN_REVERSE("663", "Aston Grand Touring Open Reverse", "AS6Y", 0),

	ASTON_NORTH("670", "Aston North", "AS7", 4),
    ASTON_NORTH_REVERSE("671", "Aston North Reverse", "AS7R", 4),
    ASTON_NORTH_OPEN("672", "Aston North Open", "AS7X", 0),
    ASTON_NORTH_OPEN_REVERSE("673", "Aston North Open Reverse", "AS7Y", 0);


	private String number;
	private String name;
	private int splits;
	private String shortname;
	
	public String getShortname() {
		return shortname;
	}

	Track(String number, String name, String shortname, int splits) {
		this.number = number;
		this.name = name;
		this.shortname = shortname;
		this.splits = splits;
	}

	public String getNumber() {
		return number;
	}
	
	public static Track getTrackByNumber(String number) {
		for (Track track : Track.values()) {
			if (track.getNumber().equals(number)) {
				return track;
			}
		}
		throw new IllegalArgumentException("No track for given number found: " + number);
	}

	public String getName() {
		return name;
	}

	public int getSplits() {
		return splits;
	}

	public static Track getTrackByShortName(String trackname) {
		for (Track track : Track.values()) {
			if (track.getShortname().equals(trackname)) {
				return track;
			}
		}
		throw new IllegalArgumentException("No track for given number found: " + trackname);
	}
	
	
}
