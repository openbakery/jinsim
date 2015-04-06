package org.openbakery.jinsim.types;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class CarContact {

	private byte playerId;
	private byte info;
	private byte steer;
	private byte throttle;

	private byte brake;
	private byte clutch;
	private byte handbrake;
	private byte gear;

	private byte speed;
	private byte direction;
	private byte heading;
	private byte accelerateForward;
	private byte accelerateRight;

	private short X;
	private short Y;

	public CarContact(ByteBuffer buffer) throws BufferUnderflowException {

		setPlayerId(buffer.get());
		setInfo(buffer.get());
		buffer.get();
		setSteer(buffer.get());

		byte throttleBreak = buffer.get();
		setThrottle((byte) (throttleBreak >> 4));
		setBrake((byte) (throttleBreak & 0x0F));

		byte clutchHandbrake = buffer.get();
		setClutch((byte) (clutchHandbrake >> 4));
		setBrake((byte) (clutchHandbrake & 0x0F));

		byte gear = buffer.get();
		setGear((byte) (gear >> 4 & 0x0F));

		setSpeed(buffer.get());

		setDirection(buffer.get());
		setHeading(buffer.get());
		setAccelerateForward(buffer.get());
		setAccelerateRight(buffer.get());

		setX(buffer.getShort());
		setY(buffer.getShort());
	}

	public int getPlayerId() {
		return playerId & 0xFF;
	}

	private void setPlayerId(byte pLID) {
		playerId = pLID;
	}

	private void setInfo(byte info) {
		this.info = info;
	}

	public int getSteer() {
		return steer & 0xFF;
	}

	private void setSteer(byte steer) {
		this.steer = steer;
	}

	public int getSpeed() {
		return speed & 0xFF;
	}

	private void setSpeed(byte speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction & 0xFF;
	}

	private void setDirection(byte direction) {
		this.direction = direction;
	}

	public int getHeading() {
		return heading & 0xFF;
	}

	private void setHeading(byte heading) {
		this.heading = heading;
	}

	public int getAccelerateForward() {
		return accelerateForward & 0xFF;
	}

	private void setAccelerateForward(byte accelerateForward) {
		this.accelerateForward = accelerateForward;
	}

	public int getAccelerateRight() {
		return accelerateRight & 0xFF;
	}

	private void setAccelerateRight(byte accelerateRight) {
		this.accelerateRight = accelerateRight;
	}

	public short getX() {
		return X;
	}

	private void setX(short x) {
		X = x;
	}

	public short getY() {
		return Y;
	}

	private void setY(short y) {
		Y = y;
	}

	public int getClutch() {
		return clutch & 0xFF;
	}

	private void setClutch(byte clutch) {
		this.clutch = clutch;
	}

	public int getHandbrake() {
		return handbrake & 0xFF;
	}

	private void setHandbrake(byte handbrake) {
		this.handbrake = handbrake;
	}

	public int getBrake() {
		return brake & 0xFF;
	}

	private void setBrake(byte brake) {
		this.brake = brake;
	}

	public int getThrottle() {
		return throttle & 0xFF;
	}

	private void setThrottle(byte throttle) {
		this.throttle = throttle;
	}

	public int getGear() {
		return gear & 0xFF;
	}

	private void setGear(byte gear) {
		this.gear = gear;
	}

	/**
	 * this car is in the way of a driver who is a lap ahead
	 *
	 * @return
	 */
	public boolean carIsInTheWay() {
		return (info & CompCar.BLUE) > 0;
	}

	/**
	 * this car is slow or stopped and in a dangerous place
	 *
	 * @return
	 */
	public boolean carIsSlower() {
		return (info & CompCar.YELLOW) > 0;
	}

	/**
	 * this car is lagging
	 *
	 * @return
	 */
	public boolean carIsLagging() {
		return (info & CompCar.LAGGING) > 0;
	}

}
