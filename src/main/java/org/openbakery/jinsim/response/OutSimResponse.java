package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.InSimVector;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class OutSimResponse extends InSimResponse {

	private int time;

	private InSimVector angularVelocity;

	private float heading;
	private float pitch;
	private float roll;

	private InSimVector acceleration;
	private InSimVector velocity;
	private InSimVector position;

	private int id;

	public OutSimResponse() {
		super(PacketType.OUT_SIM);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		buffer.rewind();

		setTime(buffer.getInt());

		angularVelocity = new InSimVector(buffer);
		heading = buffer.getFloat();
		pitch = buffer.getFloat();
		roll = buffer.getFloat();

		acceleration = new InSimVector(buffer);

		velocity = new InSimVector(buffer);

		position = new InSimVector(buffer);

		if (buffer.hasRemaining()) {
			setId(buffer.getInt());
		}

	}

	public String toString() {
		return "OutSim [time=" + getTime() +
						", angularVelocity= " + angularVelocity + ", heading= " + heading + ", pitch=" + pitch + ", roll=" + roll +
						", acceleration=" + acceleration + ", velocity=" + velocity + ", position=" + position + ", id=" + id;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public InSimVector getAngularVelocity() {
		return angularVelocity;
	}

	public float getHeading() {
		return heading;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	public InSimVector getAcceleration() {
		return acceleration;
	}

	public InSimVector getVelocity() {
		return velocity;
	}

	public InSimVector getPosition() {
		return position;
	}

}
