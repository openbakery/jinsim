package org.openbakery.jinsim;

public enum PenaltyReason {

	UNKNOWN(0),
	ADMIN(1),
	WRONG_WAY(2),
	FALSE_START(3),
	SPEEDING(4),
	STOP_SHORT(5),
	STOP_LATE(6),
	NUM(7);

	private int id;

	PenaltyReason(int id) {
		this.id = id;
	}

	public static PenaltyReason getPenaltyReason(byte id) {
		int givenId = (id & 0xFF);
		for (PenaltyReason type : PenaltyReason.values()) {
			if (type.id == givenId) {
				return type;
			}
		}
		throw new IllegalArgumentException("The specified id is not a valid penalty reason: " + id);
	}

	public int getId() {
		return id;
	}

}
