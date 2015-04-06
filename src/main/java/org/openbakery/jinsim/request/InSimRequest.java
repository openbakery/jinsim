package org.openbakery.jinsim.request;

import org.openbakery.jinsim.Encoding;
import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

/**
 * An abstract class for client requests to a LFS server.
 *
 * @author rob
 * @since 0.001
 */
abstract public class InSimRequest {

	//private static Log log = LogFactory.getLog(InSimRequest.class);


	protected int size;
	protected byte requestInfo;
	private PacketType type;

	public InSimRequest(PacketType type, int size) {
		this.type = type;
		this.size = size;
		this.requestInfo = 0;
	}

	/**
	 * Get the type of request this is, as defined in the InSim.txt documentation.
	 *
	 * @return a String representation of the packet type, as defined in the InSim.txt documentation.
	 */
	public PacketType getType() {
		return type;
	}

	public void assemble(ByteBuffer byteBuffer) {
		byteBuffer.clear();
		byteBuffer.put((byte) size);
		byteBuffer.put((byte) type.getId());
		byteBuffer.put(requestInfo);
	}

	public String toString() {
		return "InSimRequest of type " + type + ", requestInfo: " + requestInfo;
	}

	public void assembleString(ByteBuffer data, String string, int length) {
		byte[] destination = new byte[length];
		if (string != null) {
			byte[] stringData = Encoding.encodeString(string);
			int copySize = stringData.length;
			if (copySize > length) {
				copySize = length;
			}
			System.arraycopy(stringData, 0, destination, 0, copySize);
		}
		data.put(destination);
	}

	public void setRequestInfo(byte requestInfo) {
		this.requestInfo = requestInfo;
	}

	public int getSize() {
		return size;
	}

}
