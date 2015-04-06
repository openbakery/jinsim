package org.openbakery.jinsim.request;

import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

public class SetStateRequest extends InSimRequest {

	// State flags
	static public final short ISS_SHIFTU_FOLLOW = 32;  // following car
	static public final short ISS_SHIFTU_NO_OPT = 64;  // buttons are hidden
	static public final short ISS_SHOW_2D = 128; // showing 2d display
	static public final short ISS_MPSPEEDUP = 1024; // multiplayer speedup option
	static public final short ISS_SOUND_MUTE = 4096; // sound is switched off

	static public final byte STATE_OFF = 0x0;
	static public final byte STATE_ON = 0x1;

	private short flag;
	private byte state;          // 0 = off, 1 = on

	public SetStateRequest() {
		super(PacketType.STATE_FLAG, (byte) 8);
	}

	public void assemble(ByteBuffer data) {
		super.assemble(data);
		data.put((byte) 0);
		data.putShort(flag);
		data.put(state);
		data.put((byte) 0);
	}


	public void setState(short flag, byte state) {
		this.flag = flag;
		this.state = state;
	}

}
