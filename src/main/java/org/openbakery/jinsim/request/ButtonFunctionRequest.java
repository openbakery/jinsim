package org.openbakery.jinsim.request;

import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

public class ButtonFunctionRequest extends InSimRequest {

	private ButtonSubtype subtype = ButtonSubtype.DELETE_BUTTON;
	private int connectionId;
	private byte clickId;

	public ButtonFunctionRequest() {
		super(PacketType.BUTTON_FUNCTION, 8);
	}


	public void assemble(ByteBuffer buffer) {
		super.assemble(buffer);
		buffer.put(subtype.getId());
		buffer.put((byte) connectionId);
		buffer.put(clickId);
		buffer.position(buffer.position() + 2);
	}


	public void setClickId(byte clickId) {
		this.clickId = clickId;
	}


	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}


	public void setSubtype(ButtonSubtype subtype) {
		this.subtype = subtype;
	}


}
