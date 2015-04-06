package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 */
public class PlayerFlagResponse extends PlayerResponse {

	private int flags;

	public PlayerFlagResponse() {
		super(PacketType.PLAYER_FLAGS);
	}

	public void construct(ByteBuffer buffer)
					throws BufferUnderflowException {
		super.construct(buffer);
		flags = buffer.getShort();
		buffer.position(buffer.position() + 2);
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public String toString() {
		return super.toString() + "flags: " + flags;
	}


}
