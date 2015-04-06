package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class ButtonClickedResponse extends InSimResponse {

	private int LEFT_CLICK = 1;

	private int RIGHT_CLICK = 2;

	private int CTRL = 4;

	private int SHIFT = 8;

	private byte connectionId;

	private byte clickId;

	private byte clickFlags;

	public ButtonClickedResponse() {
		super(PacketType.BUTTON_CLICKED);
	}

	protected ButtonClickedResponse(PacketType type) {
		super(type);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		connectionId = buffer.get();
		clickId = buffer.get();
		buffer.position(buffer.position() + 1);
		clickFlags = buffer.get();
	}

	public int getClickId() {
		return clickId & 0xFF;
	}

	public int getConnectionId() {
		return connectionId & 0xFF;
	}

	public boolean isLeftClick() {
		return (clickFlags & LEFT_CLICK) > 0;
	}

	public boolean isRightClick() {
		return (clickFlags & RIGHT_CLICK) > 0;
	}

	public boolean isCtrl() {
		return (clickFlags & CTRL) > 0;
	}

	public boolean isShift() {
		return (clickFlags & SHIFT) > 0;
	}

}
