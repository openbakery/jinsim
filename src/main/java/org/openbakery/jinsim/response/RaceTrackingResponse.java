package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * The base class for responses from InSim related to race tracking. Race tracking is turned on during intialization with the {@link org.openbakery.jinsim.request.InitRequest} type ("ISI"). It will
 * periodically send information about the race to the client. All responses of this type are acknowledged automatically with and {@link org.openbakery.request.AckRequest} with the proper value field filled
 * in.
 *
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
abstract public class RaceTrackingResponse extends InSimResponse {

	/**
	 * When set, indicates the driver side has "swapped". LFS defaults to a righthand drive, so if this flag is set the car has been changed to lefthand drive.
	 */
	static public final int PLAYER_FLAG_SWAPSIDE = 1;

	/**
	 * When set, indicates that the player has turned on the option for LFS to cut the throttle automatically during gear changes.
	 */
	static public final int PLAYER_FLAG_GEAR_CHANGE_CUT = 2;

	/**
	 * When set, indicates the player has turned on the option for LFS to blip the throttle automatically during gear changes.
	 */
	static public final int PLAYER_FLAG_GEAR_CHANGE_BLIP = 4;

	/**
	 * When set, indicates the player has turned on automatic shifting.
	 */
	static public final int PLAYER_FLAG_AUTOGEARS = 8;

	/**
	 * Undocumented reserved flag.
	 */
	static public final int PLAYER_FLAG_SHIFTER = 16;

	static public final int PLAYER_FLAG_REVERSED = 32;

	/**
	 * When set, indicates the player has turned on braking help.
	 */
	static public final int PLAYER_FLAG_HELP_BRAKE = 64;

	/**
	 * When set, indicates the player has turned on throttle help.
	 */
	static public final int PLAYER_FLAG_AXIS_CLUTCH = 128;

	/**
	 * Undocumented reserved flag.
	 */
	static public final int PLAYER_FLAG_IN_PITS = 256;

	/**
	 * When set, indicates the player has turned on automatic clutch help while shifting.
	 */
	static public final int PLAYER_FLAG_AUTO_CLUTCH = 512;

	/**
	 * When set, indicates the player is using a mouse as his controller.
	 */
	static public final int PLAYER_FLAG_MOUSE = 1024;

	/**
	 * When set, indicates the player is using the keyboard as his controller, with no stabilization help.
	 */
	static public final int PLAYER_FLAG_KEYBOARD_NO_HELP = 2048;

	/**
	 * When set, indicates the player is using the keyboard as his controller with stabilization help.
	 */
	static public final int PLAYER_FLAG_KEYBOARD_STABILISED = 4096;

	static public final int PLAYER_FLAG_CUSTOM_VIEW = 8192;

	static public final int CONFIRMATION_MENTIONED = 1;

	static public final int CONFIRMATION_CONFIRMED = 2;

	static public final int CONFIRMATION_PENALTY_DRIVE_THROUGH = 4;

	static public final int CONFIRMATION_PENALTY_STOP_AND_GO = 8;

	static public final int CONFIRMATION_PENALTY_TIME_30 = 16;

	static public final int CONFIRMATION_PENALTY_TIME_45 = 32;

	static public final int CONFIRMATION_DID_NOT_PIT = 64;

	static public final int CONFIRMATION_DISQUALIFIED = CONFIRMATION_PENALTY_DRIVE_THROUGH | CONFIRMATION_PENALTY_STOP_AND_GO | CONFIRMATION_DID_NOT_PIT;

	static public final int CONFIRMATION_TIME = CONFIRMATION_PENALTY_TIME_30 | CONFIRMATION_PENALTY_TIME_45;

	private byte connectionId;

	RaceTrackingResponse(PacketType type) {
		super(type);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		connectionId = buffer.get();
	}

	/**
	 * @return the connection id
	 */
	public int getConnectionId() {
		return connectionId & 0xFF;
	}

	@Override
	public String toString() {
		return super.toString() + ", Unique ID: " + getConnectionId();
	}

}
