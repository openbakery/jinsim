package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.InSimTime;

import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @version 0.001
 */

public class ResultResponse extends PlayerResponse {

	private String userName;

	private String nickname;

	private String plate;

	private String carName;

	private InSimTime totalTime;

	private InSimTime bestLapTime;

	private short lapsDone;

	private short playerFlags;

	private byte confirmationFlags;

	private byte numberPitStops;

	private byte resultPosition;

	private byte totalResults;

	ResultResponse() {
		super(PacketType.RESULT_CONFIRMED);
	}

	public void construct(ByteBuffer buffer) {
		super.construct(buffer);
		userName = getString(buffer, 24);
		nickname = getString(buffer, 24);
		plate = getString(buffer, 8);
		carName = getString(buffer, 4);
		totalTime = new InSimTime(buffer);
		bestLapTime = new InSimTime(buffer);
		buffer.position(buffer.position() + 1);
		numberPitStops = buffer.get();
		confirmationFlags = buffer.get();
		buffer.position(buffer.position() + 1);
		lapsDone = buffer.getShort();
		playerFlags = buffer.getShort();
		resultPosition = buffer.get();
		totalResults = buffer.get();
		buffer.position(buffer.position() + 2);
	}

	public String toString() {
		return super.toString() + ", User name: " + getUserName() + ", Nickname: " + getNickname() + ", Plate: " + getPlate() + ", Car name: " + getCarName() + ", Laps completed: " + getLapsDone()
						+ ", Flags: " + getPlayerFlags() + ", Confirmation flags: " + getConfirmationFlags() + ", Pit stops: " + getNumberPitStops() + ", Total race time: " + getTotalTime() + ", Best lap time: "
						+ getBestLapTime() + ", Result position: " + getResultPosition() + ", Total results: " + getTotalResults();
	}

	public InSimTime getBestLapTime() {
		return bestLapTime;
	}

	public String getCarName() {
		return carName;
	}

	public int getConfirmationFlags() {
		return confirmationFlags & 0xFF;
	}

	/**
	 * Returns a short representation of the "player flags" set for the player that this ResultResponse represents.
	 *
	 * @return The flags set for this player as a short value.
	 */
	public short getPlayerFlags() {
		return playerFlags;
	}

	public short getLapsDone() {
		return lapsDone;
	}

	public String getNickname() {
		return nickname;
	}

	public int getNumberPitStops() {
		return numberPitStops & 0xFF;
	}

	public String getPlate() {
		return plate;
	}

	public int getResultPosition() {
		return resultPosition & 0xFF;
	}

	public InSimTime getTotalTime() {
		return totalTime;
	}

	public byte getTotalResults() {
		return totalResults;
	}

	public String getUserName() {
		return userName;
	}

}
