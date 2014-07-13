package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

public class PitLaneResponse extends PlayerResponse {
	
	public static final int PITLANE_EXIT = 0;
	public static final int PITLANE_ENTER = 1;
	public static final int PITLANE_NO_PURPOSE = 2;
	public static final int PITLANE_DT = 3;
	public static final int PITLANE_SG = 4;
	
	private int fact;

	public PitLaneResponse() {
		super(PacketType.PIT_LANE);
	}
	
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		fact = buffer.get();
		buffer.position(buffer.position() +3);
	}

	public String toString() {
		return super.toString() + "fact: " + fact;
	}
	
	public boolean isPitsExit() {
		return fact == PITLANE_EXIT;
	}
	public boolean isPitsEntered() {
		return fact == PITLANE_ENTER;
	}
	public boolean isPitsNoPurpose() {
		return fact == PITLANE_NO_PURPOSE;
	}
	public boolean isPitsDriveThrough() {
		return fact == PITLANE_DT;
	}
	public boolean isPitsStopAndGo() {
		return fact == PITLANE_SG;
	}
	
	

}
