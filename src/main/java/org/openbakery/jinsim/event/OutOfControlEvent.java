package org.openbakery.jinsim.event;

import org.openbakery.jinsim.types.CompCar;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class OutOfControlEvent implements RaceEvent {
	protected CompCar car;
	protected float angularDelta;

	public OutOfControlEvent(CompCar c, float delta) {
		setCar(c);
		setAngularDelta(delta);
	}

	public CompCar getCar() {
		return car;
	}

	private void setCar(CompCar car) {
		this.car = car;
	}

	public float getAngularDelta() {
		return angularDelta;
	}

	public void setAngularDelta(float angularDelta) {
		this.angularDelta = angularDelta;
	}

	public String toString() {
		String retval = "";

		retval += "Car " + getCar().getPlayerId() + " may be going out of control (angle: " + getAngularDelta() + " speed: " + getCar().getSpeed() + ")";
		return retval;
	}
}
