package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Tiny;

public class TinyResponse extends InSimResponse {

	private Tiny type;
	
	public TinyResponse() {
		super(PacketType.TINY);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		type = Tiny.getTiny(buffer.get());
	}
	
	public String toString() {
		return super.toString() + ", type: " + type;
	}

	public Tiny getType() {
		return type;
	}
	
}
