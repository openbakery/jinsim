package org.openbakery.jinsim.request;

public enum ButtonSubtype {

	DELETE_BUTTON(0),
	CLEAR(1),
	USER_CLEAR(2),
	USER_REQUEST(3);

	private int id;

	ButtonSubtype(int id) {
		this.id = id;
	}

	public static ButtonSubtype getButtonSubtype(int id) {
		switch (id) {
			case 0:
				return DELETE_BUTTON;
			case 1:
				return CLEAR;
			case 2:
				return USER_CLEAR;
			case 3:
				return USER_REQUEST;
		}
		return DELETE_BUTTON;
	}

	public byte getId() {
		return (byte) id;
	}


}
