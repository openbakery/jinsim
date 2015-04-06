package org.openbakery.jinsim.types;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Represents a three dimensional vector in LFS.
 *
 * @author Rob Heiser (jinsim@kerf.org)
 * @see org.openbakery.jinsim.response.CameraPositionResponse
 * @since 0.001
 */
public class InSimVector {
	private int x;
	private int y;
	private int z;

	/**
	 * Construct using a ByteBuffer.
	 *
	 * @param buffer Buffer to use for object construction.
	 * @throws BufferUnderflowException when the buffer doesn't have twelve more bytes left in it.
	 */

	public InSimVector(ByteBuffer buffer) throws BufferUnderflowException {
		setX(buffer.getInt());
		setY(buffer.getInt());
		setZ(buffer.getInt());
	}

	/**
	 * Construct using direct int values.
	 *
	 * @param i distance along the x axis
	 * @param j distance along the y axis
	 * @param k distance along the z axis
	 */
	public InSimVector(int i, int j, int k) {
		setX(i);
		setY(j);
		setZ(k);
	}

	public String toString() {
		return "InSimVector[" + x + ", " + y + ", " + z + "]";
	}

	/**
	 * Get the x component of the vector
	 *
	 * @return The x component of the vector where 65536 = 1 meter.
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get the y component of the vector
	 *
	 * @return The y component of the vector where 65536 = 1 meter.
	 */
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the z component of the vector
	 *
	 * @return The z component of the vector where 65536 = 1 meter.
	 */
	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public double delta(InSimVector v2) {
		double retval = 0;

		double deltaX = (double) (v2.getX() - getX());
		double deltaY = (double) (v2.getY() - getY());
		double deltaZ = (double) (v2.getZ() - getZ());

		retval = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));

		return retval;
	}

	public InSimVector add(InSimVector v2) {
		InSimVector retval = new InSimVector(getX() + v2.getX(), getY() + v2.getY(), getZ() + v2.getZ());

		return retval;
	}
}
