package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Small;

public class SmallResponse extends InSimResponse {
	
	public SmallResponse() {
		super(PacketType.SMALL);
	}

	private Small type;
	private int value;
	
	
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
