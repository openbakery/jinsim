package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.PenaltyReason;

public class PenaltyResponse extends PlayerResponse {

	private byte oldPenalty;

	private byte newPenalty;

	private PenaltyReason reason;

	public PenaltyReason getReason() {
		return reason;
	}

	public PenaltyResponse() {
		super(PacketType.PENALTY);
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
