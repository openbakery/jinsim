package org.openbakery.jinsim.request;

import org.openbakery.jinsim.Colors;
import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Sound;

import java.nio.ByteBuffer;

public class MessageToLocalComputer extends InSimRequest implements Colors {

	private String message;
	private Sound sound;

	public MessageToLocalComputer() {
		super(PacketType.MESSAGE_TO_LOCAL, 132);
		sound = Sound.SILENT;
	}


	public MessageToLocalComputer(String message, Sound sound) {
		this();
		this.message = message;
		this.sound = sound;
	}


	public void assemble(ByteBuffer buffer) {
		super.assemble(buffer);
		buffer.put(sound.getId());
		assembleString(buffer, message, 128);
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public void setSound(Sound sound) {
		this.sound = sound;
	}

}
