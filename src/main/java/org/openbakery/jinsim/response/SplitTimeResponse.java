package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.InSimTime;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class SplitTimeResponse extends PlayerResponse {
	// Shared Object for types: SP1,SP2 and SP3

	private InSimTime time; // split time

	private InSimTime totalTime;

	private byte playerNumber; // player's number

	private byte split;

	private byte penalty;

	private byte numberOfStops;

	public SplitTimeResponse() {
		super(PacketType.SPLIT);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		time = new InSimTime(buffer);
		totalTime = new InSimTime(buffer);
		split = buffer.get();
		penalty = buffer.get();
		numberOfStops = buffer.get();
		buffer.position(buffer.position() + 1);
	}

	public String toString() {
		return super.toString() + ", time: " + getTime() + ", player: " + getPlayerNumber() + ", split: " + split + ", penalty: " + penalty + ", numberOfStops: " + numberOfStops;
	}

	public int getPlayerNumber() {
		return playerNumber & 0xFF;
	}

	public void setPlayerNumber(byte playerNumber) {
		this.playerNumber = playerNumber;
	}

	public InSimTime getTime() {
		return time;
	}

	public void setTime(InSimTime t) {
		this.time = t;
	}

	public byte getNumberOfStops() {
		return numberOfStops;
	}

	public byte getPenalty() {
		return penalty;
	}

	public byte getSplit() {
		return split;
	}

	public InSimTime getTotalTime() {
		return totalTime;
	}

}
