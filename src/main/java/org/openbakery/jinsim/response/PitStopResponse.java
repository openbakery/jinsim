package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.Tyres;

public class PitStopResponse extends PlayerResponse {

	public static final int NOTHING = 1;

	public static final int STOP = 2;

	public static final int FRONT_DAMAGE = 4;

	public static final int FRONT_WHEEL = 8;

	public static final int LEFT_FRONT_DAMAGE = 16;

	public static final int LEFT_FRONT_WHEEL = 32;

	public static final int RIGHT_FRONT_DAMAGE = 64;

	public static final int RIGHT_FRONT_WHEEL = 128;

	public static final int REAR_DAMAGE = 256;

	public static final int REAR_WHEEL = 512;

	public static final int LEFT_REAR_DAMAGE = 1024;

	public static final int LEFT_REAR_WHEEL = 2048;

	public static final int RIGHT_REAR_DAMAGE = 4096;

	public static final int RIGHT_REAR_WHEEL = 8192;

	public static final int BODY_MINOR = 16384;

	public static final int BODY_MAJOR = 32768;

	public static final int SETUP = 65536;

	public static final int REFUEL = 131072;

	private int laps;

	private int playerFlags;

	private byte penalty;

	private byte numberOfPitstops;

	private Tyres tyres;

	private int work;

	public PitStopResponse() {
		super(PacketType.PIT);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		setLaps(buffer.getShort());
		setPlayerFlags(buffer.getShort());
		buffer.position(buffer.position() + 1);
		setPenalty(buffer.get());
		setNumberOfPitstops(buffer.get());
		buffer.position(buffer.position() + 1);
		setTyres(new Tyres(buffer));
		setWork(buffer.getInt());
		buffer.position(buffer.position() + 4);

	}

	public Tyres getTyres() {
		return tyres;
	}

	public void setTyres(Tyres tyres) {
		this.tyres = tyres;
	}

	public int getWork() {
		return work;
	}

	public void setWork(int work) {
		this.work = work;
	}

	public int getLaps() {
		return laps;
	}

	public void setLaps(int laps) {
		this.laps = laps;
	}

	public int getPlayerFlags() {
		return playerFlags;
	}

	public void setPlayerFlags(int playerFlags) {
		this.playerFlags = playerFlags;
	}

	public int getPenalty() {
		return penalty & 0xFF;
	}

	public void setPenalty(byte penalty) {
		this.penalty = penalty;
	}

	public int getNumberOfPitstops() {
		return numberOfPitstops & 0xFF;
	}

	public void setNumberOfPitstops(byte numberOfPitstops) {
		this.numberOfPitstops = numberOfPitstops;
	}

	public boolean didNothing() {
		return (work & NOTHING) > 0;
	}

	public boolean didStop() {
		return (work & STOP) > 0;
	}

	public boolean didFrontDamage() {
		return (work & FRONT_DAMAGE) > 0;
	}

	public boolean didFrontWheel() {
		return (work & FRONT_WHEEL) > 0;
	}

	public boolean didLeftFrontDamage() {
		return (work & LEFT_FRONT_DAMAGE) > 0;
	}

	public boolean didLeftFrontWheel() {
		return (work & LEFT_FRONT_WHEEL) > 0;
	}

	public boolean didRightFrontDamage() {
		return (work & RIGHT_FRONT_DAMAGE) > 0;
	}

	public boolean didRightFrontWheel() {
		return (work & RIGHT_FRONT_WHEEL) > 0;
	}

	public boolean didRearDamage() {
		return (work & REAR_DAMAGE) > 0;
	}

	public boolean didRearWheel() {
		return (work & REAR_WHEEL) > 0;
	}

	public boolean didLeftRearDamage() {
		return (work & LEFT_REAR_DAMAGE) > 0;
	}

	public boolean didLeftRearWheel() {
		return (work & LEFT_REAR_WHEEL) > 0;
	}

	public boolean didRightRearDamage() {
		return (work & RIGHT_REAR_DAMAGE) > 0;
	}

	public boolean didRightRearWheel() {
		return (work & RIGHT_REAR_WHEEL) > 0;
	}

	public boolean didBodyMinor() {
		return (work & BODY_MINOR) > 0;
	}

	public boolean didBodyMajor() {
		return (work & BODY_MAJOR) > 0;
	}

	public boolean didSetup() {
		return (work & SETUP) > 0;
	}

	public boolean didRefuel() {
		return (work & REFUEL) > 0;
	}

	public String toString() {
		return super.toString() + ", laps: " + laps + ", playerFlags: " + playerFlags + ", penalty: " + penalty + ", numberOfPitstops: " + numberOfPitstops + ", tyres: " + tyres + ", work: " + work;
	}

}
