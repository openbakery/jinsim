package org.openbakery.jinsim.types;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

/**
 * InSimTime is a compressed time representation used in some InSim messages. It
 * uses one byte for each time unit from hours down to thousandths of a second.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * @see org.openbakery.jinsim.response.LapTimeResponse
 * @see org.openbakery.jinsim.response.ResultResponse
 * @see org.openbakery.jinsim.response.SplitTimeResponse
 * 
 */
public class InSimTime {

	private static DecimalFormat formatter = new DecimalFormat("00");

	private static DecimalFormat formatterThoutands = new DecimalFormat("00");

	private static DecimalFormat formatterOne = new DecimalFormat("0");

	private int time;

	/**
	 * @param buffer
	 *            The buffer to use for construction of an InSimTime object.
	 * @throws java.nio.BufferUnderflowException
	 */
	public InSimTime(ByteBuffer buffer) throws BufferUnderflowException {
		time = buffer.getInt();
	}

	public InSimTime(int time) {
		this.time = time;
	}

	@Override
	public String toString() {

		StringBuilder result = new StringBuilder();
		if (getHours() > 0) {
			result.append(getHours());
			result.append(":");
		}
		if (result.length() > 0 || getMinutes() > 0) {
			result.append(formatter.format(getMinutes()));
			result.append(":");
		}
		if (result.length() > 0) {
			result.append(formatter.format(getSeconds()));
		} else {
			result.append(formatterOne.format(getSeconds()));
		}
		result.append(".");
		result.append(formatterThoutands.format(getThousandths() / 10));
		return result.toString();
	}

	public static String toString(int time) {
		return toString(time, false);
	}

	public static String toString(int time, boolean sign) {
		if (!sign) {
			return new InSimTime(time).toString();
		}
		if (time > 0) {
			return "^2-" + new InSimTime(time).toString();
		}
		return "^1+" + new InSimTime(-time).toString();
	}

	/**
	 * Get the hours component
	 * 
	 * @return The value of the hours position.
	 */
	public int getHours() {
		return time / 3600000;
	}

	/**
	 * Get the minutes component
	 * 
	 * @return The value of the minutes position.
	 */
	public int getMinutes() {
		return (time % 3600000) / 60000;
	}

	/**
	 * Get the seconds component
	 * 
	 * @return The value of the seconds position.
	 */
	public int getSeconds() {
		return (time % 60000) / 1000;
	}

	/**
	 * Get the thousandths (of a second) component
	 * 
	 * @return The value of the thousandths (of a second) position.
	 */
	public int getThousandths() {
		return time % 1000;
	}

	public int getTime() {
		return time;
	}

}
