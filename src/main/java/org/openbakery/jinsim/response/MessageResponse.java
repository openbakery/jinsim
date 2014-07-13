package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.Encoding;
import org.openbakery.jinsim.PacketType;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class MessageResponse extends InSimResponse {

	private String message;

	private String driverName;

	private byte connectionId;

	private byte playerId;

	private byte typedByUser;

	private byte textStart;

	public MessageResponse() {
		super(PacketType.MESSAGE_OUT);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);
		connectionId = buffer.get();
		playerId = buffer.get();
		typedByUser = buffer.get();
		textStart = buffer.get();
		byte[] rawMessage = getBytes(buffer, 128);
		byte[] rawDriverName = new byte[textStart];
		System.arraycopy(rawMessage, 0, rawDriverName, 0, textStart);

		byte[] rawMessageText = new byte[128 - textStart];
		System.arraycopy(rawMessage, textStart, rawMessageText, 0, 128 - textStart);

		driverName = Encoding.decodeString(rawDriverName);
		message = Encoding.decodeString(rawMessageText);
	}

	public String toString() {
		String retval = super.toString();
		retval += getMessage() + "\n";
		return retval;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getConnectionId() {
		return connectionId & 0xFF;
	}

	public int getPlayerId() {
		return playerId & 0xFF;
	}

	public int getTextStart() {
		return textStart & 0xFF;
	}

	public int getTypedByUser() {
		return typedByUser & 0xFF;
	}

	public String getDriverName() {
		return driverName;
	}

}
