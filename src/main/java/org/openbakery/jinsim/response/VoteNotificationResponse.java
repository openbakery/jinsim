package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class VoteNotificationResponse extends InSimResponse {

	private byte connectionId;

	private byte action;

	public VoteNotificationResponse() {
		super(PacketType.VOTE_NOTIFICATION);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);
		connectionId = buffer.get();
		action = buffer.get();
		buffer.position(buffer.position() + 2);
	}

	@Override
	public String toString() {
		return super.toString() + ", connectionId: " + connectionId + ", action: " + action;
	}

	public int getAction() {
		return action & 0xFF;
	}

	public int getConnectionId() {
		return connectionId & 0xFF;
	}

}
