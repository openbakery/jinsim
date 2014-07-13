package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

public class AutocrossLayoutResponse extends InSimResponse {

	private byte start;

	private byte checkpoints;

	private byte objects;

	private String name;

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);
		start = buffer.get();
		checkpoints = buffer.get();
		objects = buffer.get();
		name = getString(buffer, 32);
	}

	public int getStart() {
		return start & 0xFF;
	}

	public int getCheckpoints() {
		return checkpoints & 0xFF;
	}

	public int getObjects() {
		return objects & 0xFF;
	}

	public String getName() {
		return name;
	}

	public AutocrossLayoutResponse() {
		super(PacketType.AUTOCROSS_LAYOUT);
	}

}
