package org.openbakery.jinsim;

public enum Tiny {

	NONE(0),
	VERSION(1),
	CLOSE(2),
	PING(3),
	REPLY(4),
	VOTE_CANCEL(5),
	SEND_CAMERA_POSITION(6),
	SEND_STATE_INFO(7),
	GET_TIME_IN_HUNDREDS(8),
	MULTIPLAYER_END(9),
	MULTIPLAYER_INFO(10),
	RACE_END(11),
	CLEAR_ALL_PLAYERS_FROM_RACE(12),
	ALL_CONNECTIONS(13),
	ALL_PLAYERS(14),
	ALL_RESULTS(15),
	NODE_LAP(16),
	MULTI_CAR_INFO(17),
	REORDER(18),
	RESTART(19),
	AUTOCROSS_LAYOUT(20),
	AUTOCROSS_HIT(21),
    REPLAY_INFORMATION(22); // not implemented yet

	
	private int type;
	
	Tiny(int type) {
		this.type = type;
	}
	
	public byte getType() {
		return (byte)type;
	}
	
	public static Tiny getTiny(int type) {
		for (Tiny tiny : Tiny.values()) {
			if (tiny.type == type) {
				return tiny;
			}
		}
		throw new IllegalArgumentException("The specified id is not a tiny type: " + type);
	}

}
