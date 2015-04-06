package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class ReorderResponse extends InSimResponse {

	private ArrayList<Integer> playerPositions = new ArrayList<Integer>(MAX_PLAYERS);

	private byte numberPlayers;

	ReorderResponse() {
		super(PacketType.REORDER);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		numberPlayers = buffer.get();
		for (int i = 0; i < MAX_PLAYERS; i++) {
			playerPositions.add(new Integer(buffer.get() & 0xFF));
		}
	}

	public String toString() {
		String value = super.toString();
		value += "Number of players: " + getNumberPlayers();
		value += ", positions: ";
		for (Integer position : playerPositions) {
			value += position + ", ";
		}
		return value;
	}

	public int getPlayerPositionAt(int n) {
		return playerPositions.get(n).intValue();
	}

	public ArrayList<Integer> getPlayerPositions() {
		return playerPositions;
	}

	public int getNumberPlayers() {
		return numberPlayers & 0xFF;
	}

	public void setNumberPlayers(byte numPlayers) {
		this.numberPlayers = numPlayers;
	}

}
