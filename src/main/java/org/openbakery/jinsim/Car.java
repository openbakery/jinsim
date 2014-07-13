package org.openbakery.jinsim;

public enum Car {

	UF1("UF 1000"),
	XFG("XF GTI"),
	XRG("XR GT"),
	LX4("LX4"),
	RB4("RB4 GT"),
	FXO("FXO TURBO"),
	XRT("XR GT TURBO"),
	LX6("LX6"),
	RAC("RA"),
	FZ5("FZ50"),
	UFR("UF GTR"),
	XFR("XF GTR"),
	MRT("MTR5"),
	FOX("FORMULA XR"),
	FO8("FORMULA V8"),
	BF1("BMW SAUBER"),
	FXR("FXO GTR"),
	XRR("XR GTR"),
	FZR("FZ50 GTR"),
	FBM("FORMULA BMW"),
	SVW("VW Scirocco");

	private String longname;

	Car(String longname) {
		this.longname = longname;
	}

	public String getLongname() {
		return longname;
	}

	public static Car getCarByLongName(String longname) {
		for (Car car : Car.values()) {
			if (car.getLongname().equalsIgnoreCase(longname)) {
				return car;
			}
		}
		return null;
	}

	public static Car getCarByName(String name) {
		for (Car car : Car.values()) {
			if (car.toString().equalsIgnoreCase(name)) {
				return car;
			}
		}
		return null;
	}

}
