package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.InSimVector;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class CameraPositionResponse extends InSimResponse {

	public static final String TYPE = "CPP";

	static public final int ISS_SHIFTU = 8; // in SHIFT+U mode

	static public final int ISS_SHIFTU_HIGH = 16; // HIGH view

	static public final int ISS_SHIFTU_FOLLOW = 32; // following car

	static public final int ISS_VIEW_OVERRIDE = 8192; // override user view

	private InSimVector position; // Position vector

	private short heading; // 0 points along Y axis

	private short pitch; // 0 means looking at horizon

	private short roll; // 0 means no roll

	private byte playerIndex; // Player Index of car to view (0 = pole...)

	private byte cameraType; // InGameCam (as reported in StatePack)

	private float fov; // 4-byte float : FOV in degrees

	private short time; // Time to get there (0 means instant + reset)

	private short flags; // State Flags

	CameraPositionResponse() {
		super(PacketType.CAMERA_POSITION);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);

		setPosition(new InSimVector(buffer));
		setHeading(buffer.getShort());
		setPitch(buffer.getShort());
		setRoll(buffer.getShort());
		setPlayerIndex(buffer.get());
		setCameraType(buffer.get());
		setFov(buffer.getFloat());
		setTime(buffer.getShort());
		setFlags(buffer.getShort());
	}

	public String toString() {
		String retval = super.toString();

		retval += "Position: " + getPosition() + "\n";
		retval += "Heading: " + getHeading() + "\n";
		retval += "Pitch: " + getPitch() + "\n";
		retval += "Roll: " + getRoll() + "\n";
		retval += "Player index: " + getPlayerIndex() + "\n";
		retval += "Camera type: " + getCameraType() + "\n";
		retval += "Field of view: " + getFov() + "\n";
		retval += "Time: " + getTime() + "\n";
		retval += "Flags: " + getFlags() + "\n";

		return retval;
	}

	public int getCameraType() {
		return cameraType & 0xFF;
	}

	public void setCameraType(byte cameraType) {
		this.cameraType = cameraType;
	}

	public short getFlags() {
		return flags;
	}

	public void setFlags(short flags) {
		this.flags = flags;
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

	public short getHeading() {
		return heading;
	}

	public void setHeading(short heading) {
		this.heading = heading;
	}

	public short getPitch() {
		return pitch;
	}

	public void setPitch(short pitch) {
		this.pitch = pitch;
	}

	public int getPlayerIndex() {
		return playerIndex & 0xFF;
	}

	public void setPlayerIndex(byte playerIndex) {
		this.playerIndex = playerIndex;
	}

	public InSimVector getPosition() {
		return position;
	}

	public void setPosition(InSimVector position) {
		this.position = position;
	}

	public short getRoll() {
		return roll;
	}

	public void setRoll(short roll) {
		this.roll = roll;
	}

	public short getTime() {
		return time;
	}

	public void setTime(short time) {
		this.time = time;
	}

}
