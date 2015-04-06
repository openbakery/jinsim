package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.request.ButtonSubtype;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class ButtonFunctionResponse extends InSimResponse {

	private ButtonSubtype subtype;

	private byte connectionId;

	private byte clickId;

	public ButtonFunctionResponse() {
		super(PacketType.BUTTON_FUNCTION);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		subtype = ButtonSubtype.getButtonSubtype(buffer.get());
		connectionId = buffer.get();
		clickId = buffer.get();
		buffer.position(buffer.position() + 2);
	}

	public int getClickId() {
		return clickId & 0xFF;
	}

	public int getConnectionId() {
		return connectionId & 0xFF;
	}

	public ButtonSubtype getSubtype() {
		return subtype;
	}

}
