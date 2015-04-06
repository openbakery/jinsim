package org.openbakery.jinsim.types;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * A class that represents information about which node and lap a particular player is on. Sent with LAP data when ISF_RACE_TRACKING
 * is set during initialization of InSim.
 *
 * @author Rob Heiser (jinsim@kerf.org)
 * @see org.openbakery.jinsim.request.InitRequest
 * @see org.openbakery.jinsim.response.NodeLapInfoResponse
 * @see org.openbakery.request.NodeLapInfoRequest
 * @see org.openbakery.jinsim.types.CompCar
 * @since 0.001
 */

public class NodeLap {

	private short node;
	private short lap;
	private byte playerId;
	private byte position;

	/**
	 * Construct a new NodeLap object using a ByteBuffer. The construction of a NodeLap is a bit strange, since it was expanded
	 * after version 0.5Q so that the first short was repurposed so that the high 3 bits actually were part of the lap. So, if the
	 * first three bytes were examined coming from the server, they would look like this (where "N" is a node bit and "L" is a lap
	 * bit):
	 * <p/>
	 * LLLNNNNN NNNNNNNN LLLLLLLL
	 * <p/>
	 * That's the reason for the bit-twiddling below.
	 *
	 * @param buffer ByteBuffer that contains the data used to construct the NodeLap.
	 * @throws java.nio.BufferUnderflowException when the ByteBuffer doesn't contain at least 5 bytes.
	 */
	public NodeLap(ByteBuffer buffer) throws BufferUnderflowException {
		short readNode = buffer.getShort();
		short readLap = buffer.getShort();

		setNode((short) (readNode & 0x00001FFF)); // Mask off the first three bits and use the rest as the actual node number
		setLap((short) (((readNode & 0x0000E000) >> 5) | (readLap & 0x000000FF))); // Mask of the low five bits of the first short
		// and add them to the third byte for actual lap
		// number.

		playerId = buffer.get();
		position = buffer.get();
	}

	public String toString() {
		return "NodeLap[node=" + getNode() + ", Lap=" + getLap() + ", playerId=" + getPlayerId() + ", position=" + position + "]";
	}

	/**
	 * Get the current lap number for this car.
	 *
	 * @return The current lap number as a short.
	 */
	public short getLap() {
		return lap;
	}

	private void setLap(short lap) {
		this.lap = lap;
	}

	/**
	 * Get the current node the car is in.
	 *
	 * @return The "node" the car is currently in. The InSim.txt documentation doesn't say much about the
	 * definition of a "node", but someone on the Live For Speed Forums asked the question and an answer
	 * was provided by Scawen:
	 * <p/>
	 * "They are a series of points with direction and width that describe the track that you drive along.
	 * LFS uses it to watch your progress along the track, decides if you are driving in reverse. They
	 * provide the data for the echoes and the lightmaps, hold information about which objects you can see
	 * from that point, define the left and right boundaries for the AI drivers and are also used in yellow
	 * and blue flag systems, the position list, timing and some other things.
	 * <p/>
	 * Their length is not constant but there is approximately 0.2 seconds of time between passing one node
	 * and the next, when you are driving at a reasonable speed."
	 */
	public short getNode() {
		return node;
	}

	private void setNode(short node) {
		this.node = node;
	}

	/**
	 * Get the unique id for this car.
	 *
	 * @return The unique id assigned to this car.
	 */
	public byte getPlayerId() {
		return playerId;
	}

	public byte getPosition() {
		return position;
	}


}
