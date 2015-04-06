package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.NodeLap;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class NodeLapInfoResponse extends InSimResponse {

	private byte numberPlayers;

	private ArrayList<NodeLap> nodeLaps = new ArrayList<NodeLap>(); // node and lap of each player (max 40 NodeLap objects)

	NodeLapInfoResponse() {
		super(PacketType.NODE_LAP);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		numberPlayers = buffer.get();

		for (int i = 0; i < numberPlayers; i++) {
			nodeLaps.add(new NodeLap(buffer));
		}

	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("NodeLapInfoResponse[numberPlayers=");
		builder.append(getNumberPlayers());

		for (NodeLap nodeLap : nodeLaps) {
			builder.append(", ");
			builder.append(nodeLap);
		}
		builder.append("]");
		return builder.toString();
	}

	public NodeLap getNodeLapAt(int i) {
		return nodeLaps.get(i);
	}

	public ArrayList<NodeLap> getNodeLaps() {
		return nodeLaps;
	}

	public int getNumberPlayers() {
		return numberPlayers & 0xFF;
	}

}
