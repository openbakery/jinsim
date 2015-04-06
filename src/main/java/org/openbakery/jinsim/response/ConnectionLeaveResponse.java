package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class ConnectionLeaveResponse extends RaceTrackingResponse {

	private byte total;
	private Reason reason = Reason.UNKOWN;

	ConnectionLeaveResponse() {
		super(PacketType.CONNECTION_LEFT);
	}

	public Reason getReason() {
		return reason;
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		byte reasonData = buffer.get();
		for (Reason r : Reason.values()) {
			if (r.ordinal() == reasonData) {
				reason = r;
			}
		}
		buffer.position(buffer.position() + 1);
		total = buffer.get();
		buffer.position(buffer.position() + 1);
	}

	public String toString() {
		String retval = super.toString();
		retval += "Total: " + getTotal() + ", Reason: " + reason;
		return retval;
	}

	public int getTotal() {
		return total & 0xFF;
	}

	public void setTotal(byte total) {
		this.total = total;
	}

	public enum Reason {
		DISCONNECT,
		TIMEOUT,
		LOST_CONNECTION,
		KICKED,
		BANNED,
		SECURITY,
		UNKOWN;
	}

}
