package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.PenaltyReason;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class PenaltyResponse extends PlayerResponse {

	private byte oldPenalty;

	private byte newPenalty;

	private PenaltyReason reason;

	public PenaltyResponse() {
		super(PacketType.PENALTY);
	}

	public PenaltyReason getReason() {
		return reason;
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {

		super.construct(buffer);
		oldPenalty = buffer.get();
		newPenalty = buffer.get();
		reason = PenaltyReason.getPenaltyReason(buffer.get());
		buffer.position(buffer.position());
	}

	public int getNewPenalty() {
		return newPenalty & 0xFF;
	}

	public void setNewPenalty(byte newPen) {
		this.newPenalty = newPen;
	}

	public int getOldPenalty() {
		return oldPenalty & 0xFF;
	}

	public void setOldPenalty(byte oldPen) {
		this.oldPenalty = oldPen;
	}

	public String toString() {
		String result = super.toString();
		result += "oldPenalty " + oldPenalty + "\n";
		result += "newPenalty " + newPenalty + "\n";
		return result;
	}

}
