package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.Encoding;
import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.UDPChannel;

/**
 * An abstract class to represent a response from LFS. A response is defined as any packet sent from LFS to the InSim client.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 * 
 */
abstract public class InSimResponse {

	protected static final int MAX_PLAYERS = 32;

	protected PacketType packetType;

	protected byte requestInfo;

	public int getRequestInfo() {
		return requestInfo & 0xFF;
	}

	public InSimResponse(PacketType packetType) {
		this.packetType = packetType;
	}

	/**
	 * @param buffer
	 *          A ByteBuffer that will be turned into a specific type of response. This ByteBuffer usually is the ByteBuffer contained within the UDP packet sent to the client. A notable exception to
	 *          this rule is the {@link ErrorResponse} packet that is constructed by {@link UDPChannel} when an error occurs while listening for packets. These types of "fake" responses are created to
	 *          communicate with the {@link org.openbakery.UDPClient}, which expects an object that can resolve to an InSimResponse type.
	 * 
	 * @return The fully constructed response.
	 * @throws BufferUnderflowException
	 */
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		requestInfo = buffer.get();
	}

	public String toString() {
		return "[" + getClass().getName() + "]";
	}

	/**
	 * Get the response type.
	 * 
	 * @return The type of response this is; the four character type identifiers as defined in the InSim.txt documentation.
	 */
	public PacketType getPacketType() {
		return packetType;
	}

	protected byte[] getBytes(ByteBuffer buffer, int size) {
		byte[] data = new byte[size];
		buffer.get(data);
		return data;
	}

	protected String getString(ByteBuffer buffer, int size) {
		byte[] data = new byte[size];
		buffer.get(data);
		// return (new String(data)).trim();
		return Encoding.decodeString(data);
	}
}
