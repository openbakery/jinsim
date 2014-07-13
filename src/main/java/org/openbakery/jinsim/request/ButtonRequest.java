package org.openbakery.jinsim.request;

import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonRequest extends InSimRequest {

	private static Logger log = LoggerFactory.getLogger(ButtonRequest.class);

	private static final int TEXT_LENGTH = 240;

	public static byte BUTTON_STYLE_LIGHT_GREY = 0;

	public static byte BUTTON_STYLE_YELLOW = 1;

	public static byte BUTTON_STYLE_BLACK = 2;

	public static byte BUTTON_STYLE_WHITE = 3;

	public static byte BUTTON_STYLE_GREEN = 4;

	public static byte BUTTON_STYLE_RED = 5;

	public static byte BUTTON_STYLE_BLUE = 6;

	public static byte BUTTON_STYLE_GREY = 7;

	public static byte BUTTON_STYLE_CLICK = 8;

	public static byte BUTTON_STYLE_LIGHT = 16;

	public static byte BUTTON_STYLE_DARK = 32;

	public static byte BUTTON_STYLE_LEFT = 64;

	public static byte BUTTON_STYLE_RIGHT = -128;

	private int connectionId;

	private byte clickId;

	private byte inst;

	private int buttonStyle;

	private byte typeIn;

	private byte left;

	private byte top;

	private byte width;

	private byte height;

	private String text;

	public ButtonRequest() {
		super(PacketType.BUTTON, TEXT_LENGTH + 12);
	}

	public void assemble(ByteBuffer buffer) {
		// set size to multiple of 4
		int textLength = (((int) text.length() / 4) + 1) * 4;
		size = textLength + 12;
		if (log.isDebugEnabled()) {
			log.debug("Button buffer size: " + size);
		}
		super.assemble(buffer);

		buffer.put((byte)connectionId);
		buffer.put(clickId);
		buffer.put(inst);
		buffer.put((byte) buttonStyle);
		buffer.put(typeIn);
		buffer.put(left);
		buffer.put(top);
		buffer.put(width);
		buffer.put(height);
		if (textLength > TEXT_LENGTH) {
			textLength = TEXT_LENGTH;
		}
		// size = 12 + textLength;
		// buffer.limit(size);
		assembleString(buffer, text, textLength);
	}

	public void setButtonStyle(int buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public void setClickId(byte clickId) {
		this.clickId = clickId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public void setHeight(byte height) {
		this.height = height;
	}

	public void setInst(byte inst) {
		this.inst = inst;
	}

	public void setLeft(byte left) {
		this.left = left;
	}

	public void setTop(byte top) {
		this.top = top;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTypeIn(byte typeIn) {
		this.typeIn = typeIn;
	}

	public void setWidth(byte width) {
		this.width = width;
	}

}
