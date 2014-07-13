package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

/**
 * 
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * 
 */
public class TakeOverCarResponse extends PlayerResponse {

	private byte oldConnectionId;

	private byte newConnectionId;

	public TakeOverCarResponse() {
		super(PacketType.TAKE_OVER_CAR);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		oldConnectionId = buffer.get();
		newConnectionId = buffer.get();
		buffer.position(buffer.position() + 2);
	}

	public String toString() {
		return super.toString() + ", oldConnectionId: " + oldConnectionId + ", newConnectionId: " + newConnectionId;
	}

	public int getNewConnectionId() {
		return newConnectionId & 0xFF;
	}

	public int getOldConnectionId() {
		return oldConnectionId & 0xFF;
	}
}
