package org.openbakery.jinsim.request;

import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * User: rene
 * Date: 05.03.12
 */
public class ReorderRequest extends InSimRequest {

	protected static final int MAX_PLAYERS = 40;

	private ArrayList<Integer> playerPositions;


	public ReorderRequest() {
		super(PacketType.REORDER, 36);
	}

	public ReorderRequest(ArrayList<Integer> playerPositions) {
		this();
		this.playerPositions = playerPositions;
	}


	public void assemble(ByteBuffer data) {
		super.assemble(data);
		data.put((byte) playerPositions.size());
		for (int i = 0; i < MAX_PLAYERS; i++) {
			if (i < playerPositions.size()) {
				data.put(playerPositions.get(i).byteValue());
			} else {
				data.put((byte) 0);
			}
		}
	}

	public void setPlayerPositions(ArrayList<Integer> playerPositions) {
		this.playerPositions = playerPositions;
	}
}
