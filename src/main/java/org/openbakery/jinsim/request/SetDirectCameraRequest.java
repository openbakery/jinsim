package org.openbakery.jinsim.request;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.response.CameraPositionResponse;
import org.openbakery.jinsim.types.InSimVector;

import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class SetDirectCameraRequest extends InSimRequest {
	static public final short ISS_SHIFTU = 8; // in SHIFT+U mode

	static public final short ISS_SHIFTU_HIGH = 16; // HIGH view

	static public final short ISS_SHIFTU_FOLLOW = 32; // following car

	static public final short ISS_VIEW_OVERRIDE = 8192; // override user view

	private InSimVector position; // Position vector

	private short heading; // 0 points along Y axis

	private short pitch; // 0 means looking at horizon

	private short roll; // 0 means no roll

	private int playerIndex; // Player Index of car to view (0 = pole...)

	private int cameraType; // InGameCam (as reported in StatePack)

	private float fov; // 4-byte float : FOV in radians

	private short time; // Time to get there (0 means instant + reset)

	private short flags; // State Flags

	public SetDirectCameraRequest() {
		super(PacketType.CAMERA_POSITION, 32);
		position = new InSimVector(0, 0, 0);
	}

	public SetDirectCameraRequest(CameraPositionResponse cameraResponse) {
		this();
		this.setCameraType(cameraResponse.getCameraType());
		this.setFlags(cameraResponse.getFlags());
		this.setFov(cameraResponse.getFov());
		this.setHeading(cameraResponse.getHeading());
		this.setPitch(cameraResponse.getPitch());
		this.setPlayerIndex(cameraResponse.getPlayerIndex());
		this.setPosition(cameraResponse.getPosition());
		this.setRoll(cameraResponse.getRoll());
		this.setTime(cameraResponse.getTime());

	}

	public void assemble(ByteBuffer data) {
		super.assemble(data);
		data.put((byte) 0);
		data.putInt(getPosition().getX());
		data.putInt(getPosition().getY());
		data.putInt(getPosition().getZ());
		data.putShort(getHeading());
		data.putShort(getPitch());
		data.putShort(getRoll());
		data.put((byte) getPlayerIndex());
		data.put((byte) getCameraType());
		data.putFloat(getFov());
		data.putShort(getTime());
		data.putShort(getFlags());
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
		return cameraType;
	}

	public void setCameraType(int cameraType) {
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
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
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
