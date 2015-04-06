package org.openbakery.jinsim;


public enum Weather {

	BRIGHT_CLEAR,
	OVERCAST,
	CLOUDY_SUNSET_DUSK;

	public static Weather getWeather(int insimValue) {
		switch (insimValue) {
			case 0:
				return BRIGHT_CLEAR;
			case 1:
				return OVERCAST;
			case 2:
				return CLOUDY_SUNSET_DUSK;
		}
		return BRIGHT_CLEAR;
	}

	public static int getValue(Weather weather) {
		switch (weather) {
			case BRIGHT_CLEAR:
				return 0;
			case OVERCAST:
				return 1;
			case CLOUDY_SUNSET_DUSK:
				return 2;
		}
		return 0;
	}

}
