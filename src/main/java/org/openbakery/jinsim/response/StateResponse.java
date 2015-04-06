package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Track;
import org.openbakery.jinsim.Weather;
import org.openbakery.jinsim.Wind;
import org.openbakery.jinsim.types.RaceLaps;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class StateResponse extends InSimResponse {

	// State flags
	static public final short ISS_GAME = 1; // in game (or MPR)

	static public final short ISS_REPLAY = 2; // in SPR

	static public final short ISS_PAUSED = 4; // paused

	static public final short ISS_SHIFTU = 8; // in SHIFT+U mode

	static public final short ISS_SHIFTU_HIGH = 16; // HIGH view

	static public final short ISS_SHIFTU_FOLLOW = 32; // following car

	static public final short ISS_SHIFTU_NO_OPT = 64; // buttons are hidden

	static public final short ISS_SHOW_2D = 128; // showing 2d display

	static public final short ISS_FRONT_END = 256; // in front end screen

	static public final short ISS_MULTI = 512; // multiplayer mode

	static public final short ISS_MPSPEEDUP = 1024; // multiplayer speedup option

	static public final short ISS_WINDOWED = 2048; // lfs is running in a window

	static public final short ISS_SOUND_MUTE = 4096; // sound is switched off

	static public final short ISS_VIEW_OVERRIDE = 8192; // override user view

	// Camera types
	static public final byte ARCADE_VIEW = 0; // Arcade View

	static public final byte HELICOPTER_VIEW = 1; // Helicopter View

	static public final byte TV_VIEW = 2; // TV Camera View

	static public final byte DRIVER_VIEW = 3; // Driver View

	static public final byte CUSTOM_VIEW = 4; // Custom View

	private float replaySpeed; // 1.0 is normal speed

	private short flags; // State Flags (see above)

	private byte camera; // Which type of camera is selected (see above)

	private byte player; // Player index of viewed car

	private byte numPlayers; // Number of cars in race

	private byte numConnections; // Number of connections including host

	private byte numFinished; // Number finished or qualified

	private byte raceInProgress; // 0 - no / 1 - race / 2 - qualifying

	private byte qualifyingLength;

	private RaceLaps numLaps;

	private String shortTrackName; // short name for track e.g. FE2R

	private Weather weatherType;

	private Wind windType; // 0=off 1=weak 2=strong

	StateResponse() {
		super(PacketType.STATE);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);

		setReplaySpeed(buffer.getFloat());
		setFlags(buffer.getShort());
		setCamera(buffer.get());
		setPlayer(buffer.get());
		setNumPlayers(buffer.get());
		setNumConnections(buffer.get());
		setNumFinished(buffer.get());
		setRaceInProgress(buffer.get());
		setQualifyingLength(buffer.get());
		setNumLaps(new RaceLaps(buffer));
		setShortTrackName(getString(buffer, 6));
		setWeatherType(buffer.get());
		setWindType(buffer.get());
	}

	public String toString() {
		String retval = super.toString();

		retval += "Replay speed: " + (getReplaySpeed() / 1.0f) + "X normal\n";
		retval += "Flags: \n" + getFlagsString();
		retval += "Camera: " + getCameraString() + " View\n";
		retval += "Player: " + getPlayer() + "\n";
		retval += "Number of players: " + getNumPlayers() + "\n";
		retval += "Number of connections: " + getNumConnections() + "\n";
		retval += "Number of racers finished: " + getNumFinished() + "\n";
		retval += "Race in progress: " + getRaceInProgressString() + "\n";
		retval += "Qualifying length: " + getQualifyingLength() + " laps.\n";
		retval += "Race length: " + getNumLaps() + "\n";
		retval += "Track name: " + getShortTrackName() + "\n";
		retval += "Weather type: " + getWeatherType() + "\n";
		retval += "Wind type: " + getWindType() + "\n";

		return retval;
	}

	public String getCameraString() {
		try {
			String[] cameras = {"Arcade", "Helicopter", "TV", "Driver", "Custom"};

			return cameras[getCamera()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "Unknown (" + getCamera() + ")";
		}
	}

	public int getCamera() {
		return camera & 0xFF;
	}

	public void setCamera(byte camera) {
		this.camera = camera;
	}

	public short getFlags() {
		return flags;
	}

	public void setFlags(short flags) {
		this.flags = flags;
	}

	public byte getNumConnections() {
		return numConnections;
	}

	public void setNumConnections(byte numConnections) {
		this.numConnections = numConnections;
	}

	public int getNumFinished() {
		return numFinished & 0xFF;
	}

	public void setNumFinished(byte numFinished) {
		this.numFinished = numFinished;
	}

	public RaceLaps getNumLaps() {
		return numLaps;
	}

	public void setNumLaps(RaceLaps numLaps) {
		this.numLaps = numLaps;
	}

	public int getNumPlayers() {
		return numPlayers & 0xFF;
	}

	public void setNumPlayers(byte numPlayers) {
		this.numPlayers = numPlayers;
	}

	public int getPlayer() {
		return player & 0xFF;
	}

	public void setPlayer(byte player) {
		this.player = player;
	}

	public int getQualifyingLength() {
		return qualifyingLength & 0xFF;
	}

	public void setQualifyingLength(byte qualifyingLength) {
		this.qualifyingLength = qualifyingLength;
	}

	public String getRaceInProgressString() {
		try {
			String[] raceStrings = {"No", "Racing", "Qualifying"};

			return raceStrings[getRaceInProgress()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "Unknown";
		}
	}

	public int getRaceInProgress() {
		return raceInProgress & 0xFF;
	}

	public void setRaceInProgress(byte raceInProgress) {
		this.raceInProgress = raceInProgress;
	}

	public float getReplaySpeed() {
		return replaySpeed;
	}

	public void setReplaySpeed(float replaySpeed) {
		this.replaySpeed = replaySpeed;
	}

	public String getShortTrackName() {
		return shortTrackName;
	}

	public void setShortTrackName(String shortTrackName) {
		this.shortTrackName = shortTrackName;
	}

	public Track getTrack() {
		return Track.getTrackByShortName(shortTrackName);
	}

	public Weather getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(byte weatherType) {
		this.weatherType = Weather.getWeather(weatherType);
	}

	public Wind getWindType() {
		return windType;
	}

	public void setWindType(byte windType) {
		this.windType = Wind.getWind(windType);
	}

	public String getFlagsString() {
		String retval = "";
		short flags = getFlags();

		if ((flags & ISS_GAME) != 0) {
			retval += "In game (or multiplayer replay).\n";
		} else if ((flags & ISS_REPLAY) != 0) {
			retval += "In single player replay mode\n";
		} else if ((flags & ISS_PAUSED) != 0) {
			retval += "Paused.\n";
		} else if ((flags & ISS_SHIFTU) != 0) {
			retval += "In Shift-U mode.\n";
		} else if ((flags & ISS_SHIFTU_HIGH) != 0) {
			retval += "In HIGH view.\n";
		} else if ((flags & ISS_SHIFTU_FOLLOW) != 0) {
			retval += "In follow mode.\n";
		} else if ((flags & ISS_SHIFTU_NO_OPT) != 0) {
			retval += "Buttons are hidden\n";
		} else if ((flags & ISS_SHOW_2D) != 0) {
			retval += "Show 2D display.\n";
		} else if ((flags & ISS_FRONT_END) != 0) {
			retval += "In front end screen.\n";
		} else if ((flags & ISS_MULTI) != 0) {
			retval += "In multiplayer mode\n";
		} else if ((flags & ISS_MPSPEEDUP) != 0) {
			retval += "Multiplayer speed up option\n";
		} else if ((flags & ISS_WINDOWED) != 0) {
			retval += "Running in a window\n";
		} else if ((flags & ISS_SOUND_MUTE) != 0) {
			retval += "Sound is off.\n";
		} else if ((flags & ISS_VIEW_OVERRIDE) != 0) {
			retval += "User view is overridden.\n";
		}

		return retval;

	}

}
