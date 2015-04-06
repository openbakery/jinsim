package org.openbakery.jinsim.request;

import org.openbakery.jinsim.Colors;
import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

public class MessageExtendedRequest extends InSimRequest implements Colors {

	private String message;


	public MessageExtendedRequest() {
		super(PacketType.MESSAGE_EXTENDED, 100);
	}


	public MessageExtendedRequest(String message) {
		this();
		this.message = message;
	}


	public void assemble(ByteBuffer buffer) {
		super.assemble(buffer);
		buffer.put((byte) 0);
		assembleString(buffer, message, 96);
	}


	public void setMessage(String message) {
		this.message = message;
	}

}
