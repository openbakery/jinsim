package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class AutocrossLayoutResponse extends InSimResponse {

	private byte start;

	private byte checkpoints;

	private byte objects;

	private String name;

	public AutocrossLayoutResponse() {
		super(PacketType.AUTOCROSS_LAYOUT);
	}

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

}
