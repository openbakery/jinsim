package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 */
public class HiddenMessageResponse extends InSimResponse {

	private String message;

	private byte connectionId;

	private byte playerId;

	public HiddenMessageResponse() {
		super(PacketType.HIDDEN_MESSAGE);
	}

	public int getConnectionId() {
		return connectionId & 0xFF;
	}

	public String getMessage() {
		return message;
	}

	public int getPlayerId() {
		return playerId & 0xFF;
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);
		connectionId = buffer.get();
		playerId = buffer.get();
		message = getString(buffer, 64);
	}

	@Override
	public String toString() {
		return super.toString() + ", connectionId: " + connectionId + ", playerId: " + playerId + ", message: " + message;
	}

}
