package org.openbakery.jinsim;

public enum Small {

	NONE(0),
	START_SENDING_POSITION(1),
	START_SENDING_GAUGES(2),
	VOTE_ACTION(3),
	TIME_STOP(4),
	TIME_STEP(5),
	RACE_TIME_PACKET(6),
	NODE_LAP_INTERVAL(7);
	
	private byte type;
	
	Small(int type) {
		this.type = (byte)type;
	}
	
	public static Small getSmall(byte type) {
		for (Small small : Small.values()) {
			if (small.type == type) {
				return small;
			}
		}
		throw new IllegalArgumentException("The specified id is not a small type: " + type);
	}
	
	public byte getType() {
		return (byte)type;
	}

}
