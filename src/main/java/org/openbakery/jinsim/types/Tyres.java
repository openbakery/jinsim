package org.openbakery.jinsim.types;

import java.nio.ByteBuffer;

public class Tyres {

	public static final byte TYRE_R1 = 0;

	public static final byte TYRE_R2 = 1;

	public static final byte TYRE_R3 = 2;

	public static final byte TYRE_R4 = 3;

	public static final byte TYRE_ROAD_SUPER = 4;

	public static final byte TYRE_ROAD_NORMAL = 5;

	public static final byte TYRE_HYBRID = 6;

	public static final byte TYRE_KNOBBLY = 7;

	public static final byte NOT_CHANGED = (byte) 0xff;

	private byte rearLeft;

	private byte rearRight;

	private byte frontLeft;

	private byte frontRight;

	public Tyres() {
	}

	public Tyres(byte rearLeft, byte rearRight, byte frontLeft, byte frontRight) {
		this.rearLeft = rearLeft;
		this.rearRight = rearRight;
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
	}


	public Tyres(ByteBuffer buffer) {
		rearLeft = buffer.get();
		rearRight = buffer.get();
		frontLeft = buffer.get();
		frontRight = buffer.get();
	}

	public byte getFrontLeft() {
		return frontLeft;
	}

	public void setFrontLeft(byte tyreFrontLeft) {
		this.frontLeft = tyreFrontLeft;
	}

	public byte getFrontRight() {
		return frontRight;
	}

	public void setFrontRight(byte tyreFrontRight) {
		this.frontRight = tyreFrontRight;
	}

	public byte getRearLeft() {
		return rearLeft;
	}

	public void setRearLeft(byte tyreRearLeft) {
		this.rearLeft = tyreRearLeft;
	}

	public byte getRearRight() {
		return rearRight;
	}

	public void setRearRight(byte tyreRearRight) {
		this.rearRight = tyreRearRight;
	}

	public String toString() {
		return "Tyres [rearLeft=" + rearLeft + ", rearRight=" + rearRight
						+ ", frontLeft=" + frontLeft + ", frontRight=" + frontRight
						+ "]";
	}

}
