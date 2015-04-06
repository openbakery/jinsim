package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Small;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class SmallResponse extends InSimResponse {

	private Small type;
	private int value;
	public SmallResponse() {
		super(PacketType.SMALL);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		type = Small.getSmall(buffer.get());
		value = buffer.getInt();
	}

	@Override
	public String toString() {
		return super.toString() + ", type: " + type + ", value " + value;
	}

	public Small getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

}
