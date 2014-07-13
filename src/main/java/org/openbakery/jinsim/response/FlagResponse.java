package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

public class FlagResponse extends PlayerResponse {

	public static final int FLAG_BLUE = 1;

	public static final int FLAG_YELLOW = 2;

	private boolean on;

	private byte flag;

	private byte carBehind;

	public FlagResponse() {
		super(PacketType.FLAG);
	}

	public int getCarBehind() {
		return carBehind & 0xFF;
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		on = (buffer.get() > 0);
		flag = buffer.get();
		carBehind = buffer.get();
		buffer.position(buffer.position() + 1);
	}

	public String toString() {
		return super.toString() + ", on: " + on + ", flag: " + flag;
	}

	public int getFlag() {
		return flag & 0xFF;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public boolean getOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

}
