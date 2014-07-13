package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.InSimTime;


public class PitStopFinishedResponse extends PlayerResponse {

	private InSimTime stopTime;
	
	public PitStopFinishedResponse() {
		super(PacketType.PIT_FINISHED);
	}
	
	public void construct(ByteBuffer buffer)
			throws BufferUnderflowException {
		super.construct(buffer);
		setStopTime(new InSimTime(buffer));
		buffer.position(buffer.position()+4);
	}

	public InSimTime getStopTime() {
		return stopTime;
	}

	public void setStopTime(InSimTime stopTime) {
		this.stopTime = stopTime;
	}

	public String toString() {
		String result = super.toString();
		result += "stopTime: " + stopTime + "\n";
		return result;
	}

}
