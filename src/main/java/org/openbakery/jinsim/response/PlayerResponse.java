package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class PlayerResponse extends InSimResponse {

	protected byte playerId;

	public PlayerResponse(PacketType type) {
		super(type);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		playerId = buffer.get();
	}

	public int getPlayerId() {
		return playerId & 0xFF;
	}

	public String toString() {
		String result = super.toString();
		result += ", Player number: " + getPlayerId();
		return result;
	}

}
