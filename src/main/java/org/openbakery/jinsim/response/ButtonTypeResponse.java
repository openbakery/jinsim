package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class ButtonTypeResponse extends ButtonClickedResponse {

	private String typeInText;

	public ButtonTypeResponse() {
		super(PacketType.BUTTON_TYPED);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		typeInText = getString(buffer, 96);
	}

	public String getTypeInText() {
		return typeInText;
	}
}
