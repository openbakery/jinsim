package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class OutGaugeResponse extends InSimResponse {

	static public final int OG_TURBO = 8192;      // show turbo gauge
	static public final int OG_KM = 16384;     // if not set - user prefers MILES
	static public final int OG_BAR = 32768;     // if not set - user prefers PSI
	static public final int DL_SHIFT = 0;
	static public final int DL_FULLBEAM = 1;
	static public final int DL_HANDBRAKE = 2;
	static public final int DL_PITSPEED = 3;
	static public final int DL_TC = 4;
	static public final int DL_SIGNAL_L = 5;
	static public final int DL_SIGNAL_R = 6;
	static public final int DL_SIGNAL_ANY = 7;
	static public final int DL_OILWARN = 8;
	static public final int DL_BATTERY = 9;
	static public final int DL_ABS = 10;
	/**
	 * Atmospheric pressure constant to convert from BAR to pounds pressure
	 */
	private static final float ATMOSPHERIC_PRESSURE = 14.7f;
	/**
	 * Constant for converting from m/s to mph
	 */
	private static final float METERS_PER_SECOND_TO_MPH_CONSTANT = 2.2360248447205f;
	private static final float METERS_PER_SECOND_TO_KMH_CONSTANT = 3.6f;
	private static Logger log = LoggerFactory.getLogger(OutGaugeResponse.class);
	private int time;                 // time in milliseconds (to check order)
	private String car;               // Car name (4 len)
	private int flags;                // Combination of OG_ flags above
	private byte gear;                // Reverse:0, Neutral:1, First:2...
	private float speed;              // M/S
	private float rpm;                // RPM
	private float turbo;              // BAR
	private float waterTemperatur;    // C
	private float fuel;               // 0 to 1
	private float oilPressure;        // BAR
	private float oilTemp;            // C
	private int dashLights;           // Dash lights available
	private int showLights;           // Dash lights currently switched on
	private float throttle;           // 0 to 1
	private float brake;              // 0 to 1
	private float clutch;             // 0 to 1
	private String display1;          // Usually Fuel (16 len)
	private String display2;          // Usually Settings (16 len)
	private int id;                   // (optional ID - if specified in cfg.txt)

	public OutGaugeResponse() {
		super(PacketType.OUT_GAUGE);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {

		buffer.rewind();

		setTime(buffer.getInt());

		setCar(getString(buffer, 4));
		setFlags(buffer.getShort());
		setGear(buffer.get());
		buffer.position(buffer.position() + 1);
		setSpeed(buffer.getFloat());
		setRpm(buffer.getFloat());
		setTurbo(buffer.getFloat());
		setWaterTemperatur(buffer.getFloat());
		setFuel(buffer.getFloat());
		setOilPressure(buffer.getFloat());
		setOilTemp(buffer.getFloat());
		setDashLights(buffer.getInt());
		setShowLights(buffer.getInt());
		setThrottle(buffer.getFloat());
		setBrake(buffer.getFloat());
		setClutch(buffer.getFloat());
		setDisplay1(getString(buffer, 16));
		setDisplay2(getString(buffer, 16));

		if (buffer.hasRemaining()) {
			setId(buffer.getInt());
		}
	}

	@Override
	public String toString() {
		return "OutGaugeResponse[" +
						"time=" + time +
						" car=" + getCar() +
						" flags=" + getFlags() +
						" gear=" + getGear() +
						" speed=" + getSpeed() +
						" rpm=" + getRpm() +
						" turbo=" + getTurbo() +
						" waterTemperature=" + getWaterTemperatur() +
						" fuel=" + getFuel() +
						" oilPressure=" + getOilPressure() +
						" oilTemp=" + getOilTemp() +
						" dashLights=" + getDashLights() +
						" showLights=" + getShowLights() +
						" throttle=" + getThrottle() +
						" brake=" + getBrake() +
						" clutch=" + getClutch() +
						" display1=" + getDisplay1() +
						" display2=" + getDisplay2() +
						" id=" + getId() + "]";
	}

	/**
	 * @return Brake value, 0 to 1.
	 */
	public float getBrake() {
		return brake;
	}

	protected void setBrake(float brake) {
		this.brake = brake;
	}

	/**
	 * @return Car string, UFR, XFG, FBM, etc.
	 */
	public String getCar() {
		return car;
	}

	protected void setCar(String car) {
		this.car = car;
	}

	/**
	 * @return Clutch value, 0 to 1.
	 */
	public float getClutch() {
		return clutch;
	}

	protected void setClutch(float clutch) {
		this.clutch = clutch;
	}

	/**
	 * @return Display1 string, usually fuel.
	 */
	public String getDisplay1() {
		return display1;
	}

	protected void setDisplay1(String display1) {
		this.display1 = display1;
	}

	/**
	 * @return Display2 string, usually settings.
	 */
	public String getDisplay2() {
		return display2;
	}

	protected void setDisplay2(String display2) {
		this.display2 = display2;
	}

	/**
	 * @return Flags, see OG_x
	 */
	public int getFlags() {
		return flags;
	}

	protected void setFlags(short flags) {
		this.flags = flags & 0xFFFF;
	}

	/**
	 * @return Fuel value, 0 to 1.
	 */
	public float getFuel() {
		return fuel;
	}

	protected void setFuel(float fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return Current gear, Reverse:0, Neutral:1, First:2...
	 */
	public byte getGear() {
		return gear;
	}

	protected void setGear(byte gear) {
		this.gear = gear;
	}

	/**
	 * @return ID, optional - only if OutGauge ID is specified.
	 */
	public int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * @return OilPressure, BAR.
	 */
	public float getOilPressure() {
		return oilPressure;
	}

	protected void setOilPressure(float oilPressure) {
		this.oilPressure = oilPressure;
	}

	/**
	 * @return Engine RPM.
	 */
	public float getRpm() {
		return rpm;
	}

	protected void setRpm(float rpm) {
		this.rpm = rpm;
	}

	/**
	 * @return Speed in meters per second.
	 */
	public float getSpeed() {
		return speed;
	}

	protected void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return Speed in miles per hour.
	 */
	public float getSpeedInMilesPerHour() {
		return speed * METERS_PER_SECOND_TO_MPH_CONSTANT;
	}

	/**
	 * @return Speed in kilometers per hour.
	 */
	public float getSpeedInKilometersPerHour() {
		return speed * METERS_PER_SECOND_TO_KMH_CONSTANT;
	}

	/**
	 * @return Throttle value, 0 to 1.
	 */
	public float getThrottle() {
		return throttle;
	}

	protected void setThrottle(float throttle) {
		this.throttle = throttle;
	}

	/**
	 * @return Time in milliseconds (to check order)
	 */
	public int getTime() {
		return time;
	}

	protected void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return Turbo value in BAR.
	 */
	public float getTurbo() {
		return turbo;
	}

	/**
	 * @return Turbo value in PSI.
	 */
	public float getTurboInPoundsPressure() {
		return turbo * ATMOSPHERIC_PRESSURE;
	}

	/**
	 * @return Engine temperature in Celcius.
	 */
	public float getWaterTemperatur() {
		return waterTemperatur;
	}

	protected void setWaterTemperatur(float waterTemp) {
		this.waterTemperatur = waterTemp;
	}

	/**
	 * @return Oil temperature in Celcius.
	 */
	public float getOilTemp() {
		return oilTemp;
	}

	protected void setOilTemp(float oilTemp) {
		this.oilTemp = oilTemp;
	}

	/**
	 * @return Dash lights available, see DL_x.
	 */
	public int getDashLights() {
		return dashLights;
	}

	protected void setDashLights(int dashLights) {
		this.dashLights = dashLights;
	}

	/**
	 * @return Dash lights currently switched on.
	 */
	public int getShowLights() {
		return showLights;
	}

	protected void setShowLights(int showLights) {
		this.showLights = showLights;
	}

	/**
	 * @return Show turbo gauge.
	 */
	public boolean isTurbo() {
		return ((flags & OG_TURBO) != 0);
	}

	protected void setTurbo(float turbo) {
		this.turbo = turbo;
	}

	/**
	 * @return False if user prefers MILES.
	 */
	public boolean isKM() {
		return ((flags & OG_KM) != 0);
	}

	/**
	 * @return False if user prefers PSI.
	 */
	public boolean isBAR() {
		return ((flags & OG_BAR) != 0);
	}

	/**
	 * @return True if shift light is on.
	 */
	public boolean isShiftLight() {
		return ((showLights & (1 << DL_SHIFT)) == 1);
	}

	/**
	 * @return True if full beam is on.
	 */
	public boolean isFullBeam() {
		return ((showLights & (1 << DL_FULLBEAM)) == 1);
	}

	/**
	 * @return True if hand brake is on.
	 */
	public boolean isHandBrake() {
		return ((showLights & (1 << DL_HANDBRAKE)) == 1);
	}

	/**
	 * @return True if pit speed limiter is on.
	 */
	public boolean isPitSpeed() {
		return ((showLights & (1 << DL_PITSPEED)) == 1);
	}

	/**
	 * @return True if traction control is active.
	 */
	public boolean isTC() {
		return ((showLights & (1 << DL_TC)) == 1);
	}

	/**
	 * @return True if left turn signal is on.
	 */
	public boolean isSignalLeft() {
		return ((showLights & (1 << DL_SIGNAL_L)) == 1);
	}

	/**
	 * @return True if right turn signal is on.
	 */
	public boolean isSignalRight() {
		return ((showLights & (1 << DL_SIGNAL_R)) == 1);
	}

	/**
	 * @return True if shared turn signal is on.
	 */
	public boolean isSignalAny() {
		return ((showLights & (1 << DL_SIGNAL_ANY)) == 1);
	}

	/**
	 * @return True if oil pressure warning is on.
	 */
	public boolean isOilWarn() {
		return ((showLights & (1 << DL_OILWARN)) == 1);
	}

	/**
	 * @return True if battery warning is on.
	 */
	public boolean isBattery() {
		return ((showLights & (1 << DL_BATTERY)) == 1);
	}

	/**
	 * @return True if ABS is active.
	 */
	public boolean isABS() {
		return ((showLights & (1 << DL_ABS)) == 1);
	}


	/**
	 * @return True if car has shift light.
	 */
	public boolean hasShiftLight() {
		return ((dashLights & (1 << DL_SHIFT)) == 1);
	}

	/**
	 * @return True if car has full beam.
	 */
	public boolean hasFullBeam() {
		return ((dashLights & (1 << DL_FULLBEAM)) == 1);
	}

	/**
	 * @return True if car has handbrake.
	 */
	public boolean hasHandBrake() {
		return ((dashLights & (1 << DL_HANDBRAKE)) == 1);
	}

	/**
	 * @return True if car has pit speed limiter.
	 */
	public boolean hasPitSpeed() {
		return ((dashLights & (1 << DL_PITSPEED)) == 1);
	}

	/**
	 * @return True if car has traction control.
	 */
	public boolean hasTC() {
		return ((dashLights & (1 << DL_TC)) == 1);
	}

	/**
	 * @return True if car has left turn signal.
	 */
	public boolean hasSignalLeft() {
		return ((dashLights & (1 << DL_SIGNAL_L)) == 1);
	}

	/**
	 * @return True if car has right turn signal.
	 */
	public boolean hasSignalRight() {
		return ((dashLights & (1 << DL_SIGNAL_R)) == 1);
	}

	/**
	 * @return True if car has shared turn signal.
	 */
	public boolean hasSignalAny() {
		return ((dashLights & (1 << DL_SIGNAL_ANY)) == 1);
	}

	/**
	 * @return True if car has oil pressure warning.
	 */
	public boolean hasOilWarn() {
		return ((dashLights & (1 << DL_OILWARN)) == 1);
	}

	/**
	 * @return True if car has battery warning.
	 */
	public boolean hasBattery() {
		return ((dashLights & (1 << DL_BATTERY)) == 1);
	}

	/**
	 * @return True if car has ABS.
	 */
	public boolean hasABS() {
		return ((dashLights & (1 << DL_ABS)) == 1);
	}
}
