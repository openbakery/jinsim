package org.openbakery.jinsim;

public enum Wind {
	NONE,
	LOW,
	HIGH;

	public static Wind getWind(int insimValue) {
		switch (insimValue) {
			case 0:
				return NONE;
			case 1:
				return LOW;
			case 2:
				return HIGH;
		}
		return NONE;
	}

	public static int getValue(Wind wind) {
		switch (wind) {
			case LOW:
				return 1;
			case HIGH:
				return 2;
		}
		return 0;
	}
}
