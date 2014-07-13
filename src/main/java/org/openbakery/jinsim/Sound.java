package org.openbakery.jinsim;

public enum Sound {
	
	SILENT(0),
	MESSAGE(1),
	SYSTEM_MESSAGE(2),
	INVALID_KEY(3),
	ERROR(4),
	NUMBER(5);
	
	private int id;
	
	public byte getId() {
		return ((byte)id);
	}
	
	Sound(int id) {
		this.id = id;
	}
	
	public static Sound getSound(int id) {
		switch(id) {
		case 0:
			return SILENT;
		case 1:
			return MESSAGE;
		case 2:
			return SYSTEM_MESSAGE;
		case 3:
			return INVALID_KEY;
		case 4:
			return ERROR;
		case 5:
			return NUMBER;
		}
		return SILENT;
	}

}
