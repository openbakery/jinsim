package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

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
