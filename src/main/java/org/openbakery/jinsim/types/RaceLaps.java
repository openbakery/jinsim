package org.openbakery.jinsim.types;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * The RaceLaps class holds information about the current lap as given in some response packets. It uses the class to hold more than just the lap number, it also indicates if the laps are during
 * practice, or if it's a time based race, rather than a lap based race, the number of hours the race has been run.
 *
 * @author Rob Heiser (jinsim@kerf.org)
 * @see org.openbakery.jinsim.response.StateResponse
 * @see org.openbakery.jinsim.response.RaceStartResponse
 * @since 0.001
 */
public class RaceLaps {
	boolean isPractice;

	boolean isTimedRace;

	int laps;

	public RaceLaps(ByteBuffer buffer) throws BufferUnderflowException {
		int readLap = buffer.get() & 0xFF;

		if (readLap == 0) {
			isPractice = true;
			isTimedRace = false;
			laps = 0;
		} else if (readLap > 190) {
			isPractice = false;
			isTimedRace = true;
			laps = readLap - 190;
		} else if (readLap > 100) {
			isPractice = false;
			isTimedRace = false;
			laps = (readLap - 100) * 10 + 100;
		} else {
			isPractice = false;
			isTimedRace = false;
			laps = readLap;
		}
	}

	public String toString() {
		String retval = "";

		if (isPractice()) {
			retval += "Practice Session";
		} else if (isTimedRace()) {
			retval += getLaps() + " hours.";
		} else {
			retval += getLaps() + " laps.";
		}

		return retval;
	}

	public boolean isPractice() {
		return isPractice;
	}

	public void setPractice(boolean isPractice) {
		this.isPractice = isPractice;
	}

	public boolean isTimedRace() {
		return isTimedRace;
	}

	public void setTimedRace(boolean isTimedRace) {
		this.isTimedRace = isTimedRace;
	}

	public int getLaps() {
		return laps;
	}

	public void setLaps(int laps) {
		this.laps = laps;
	}

	public int getHours() {
		if (isTimedRace) {
			return laps;
		}
		return 0;
	}
}
