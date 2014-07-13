package org.openbakery.jinsim.types;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Information about cars from LFS. These are used in the {@link org.openbakery.jinsim.response.MultiCarInfoResponse} that comes from LFS
 * when it's requested. If {@link org.openbakery.jinsim.request.InitRequest#ISF_RACE_TRACKING} is set when communication is initiated,
 * {@link org.openbakery.jinsim.response.MultiCarInfoResponse} packets come at a specified interval.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * @see org.openbakery.request.MultiCarInfoRequest
 * @see org.openbakery.jinsim.response.MultiCarInfoResponse
 * @see org.openbakery.jinsim.request.InitRequest#ISF_RACE_TRACKING
 */

public class CompCar {

	static int BLUE = 1;
	static int YELLOW = 2;
	static int LAGGING = 32;
	private static int FIRST = 64;
	private static int LAST = 128;
	
	
    private short       node;           // current path node
    private short       lap;            // current lap
    private byte        playerId;       // player's assigned unique id
    private InSimVector position;
    private short       speed;          // speed (32768 = 100 m/s)
    private short       direction;      // direction of car's motion : (see note 1 below)
    private short       heading;        // direction of forward axis : (see note 1 below)
    private short       angularVelocity; // signed, rate of change of heading : (see note 2)
    private byte racePosition;
    private byte info;

    /**
     * Construct a CarInfo object using a ByteBuffer.
     * 
     * @param buffer
     *            The buffer to read the CarInfo object from.
     * @throws java.nio.BufferUnderflowException
     */
    public CompCar(ByteBuffer buffer) throws BufferUnderflowException {
        NodeLap nodeLap = new NodeLap(buffer);
        setNode(nodeLap.getNode());
        setLap(nodeLap.getLap());
        setPlayerId(nodeLap.getPlayerId());
        racePosition = nodeLap.getPosition();
        info = buffer.get();
        buffer.position(buffer.position()+1);
        setPosition(new InSimVector(buffer));
        setSpeed(buffer.getShort());
        setDirection(buffer.getShort());
        setHeading(buffer.getShort());
        setAngularVelocity(buffer.getShort());
    }

    public String toString() {
    	return "CompCar[Node=" + getNode() +
        	" lap=" + getLap() + 
        	" playerId=" + getPlayerId() +
        	" position=" + getPosition() + 
        	" speed=" + getSpeed() +
        	" direction=" + getDirection() +
        	" heading=" + getHeading() +
        	" angularVelocity: " + getAngularVelocity() + "]";
    }

    /**
     * @return The angular velocity of the specified car around the yaw axis. How fast the car is spinning. Or, how fast the heading
     *         is changing, if you like. Values go from "0" which means the car is not changing its heading to "8192", which means
     *         the car is changing heading ("spinning") at 180 degrees per second counterclockwise. Values also go in the negative
     *         direction down to "-8192" which means the car is spinning in a clockwise direction.
     */
    public short getAngularVelocity() {
        return angularVelocity;
    }

    private void setAngularVelocity(short angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    /**
     * Get the direction the car is traveling.
     * 
     * @return The direction the car is traveling. A value of "0" means the car is traveling in the same direction as the Y axis of
     *         the "world" coordinate system. The maximum value (as it is a short) is 36768, which indicates the car is traveling
     *         180 degrees from the "0" direction, or down the Y axis in a "negative" direction. As the values increase, the car is
     *         changing direction in a counterclockwise direction.
     */
    public short getDirection() {
        return direction;
    }
    
    public float getDirectionInDegrees() {
    	return (float) (direction/180.0);
    }

    private void setDirection(short direction) {
        this.direction = direction;
    }

    /**
     * Get the direction the car is facing (heading)
     * 
     * @return Which direction the car is facing. This may be different than the value returned by {@link #getDirection()} above. A
     *         value of "0" means the car is traveling in the same direction as the Y axis of the "world" coordinate system. The
     *         maximum value (as it is a short) is 36768, which indicates the car is traveling 180 degrees from the "0" direction,
     *         or down the Y axis in a "negative" direction. As the values increase, the car is changing direction in a
     *         counterclockwise direction.
     */
    public short getHeading() {
        return heading;
    }
    
    public float getHeadingInDegrees() {
    	return (float) (heading/180.0);
    }

    private void setHeading(short heading) {
        this.heading = heading;
    }

    /**
     * Get the current lap number for this car.
     * 
     * @return The current lap number as a short.
     */
    public short getLap() {
        return lap;
    }

    private void setLap(short lap) {
        this.lap = lap;
    }

    /**
     * Get the current node the car is in.
     * 
     * @return The "node" the car is currently in. The InSim.txt documentation doesn't say much about the definition of a "node". My
     *         guess that it is pieces of the track that compromise each "split". When you get to the end of a node, that's when it
     *         records your split time.
     */
    public short getNode() {
        return node;
    }

    private void setNode(short node) {
        this.node = node;
    }

    /**
     * Get the unique id for this car.
     * 
     * @return The unique id assigned to this car.
     */
    public byte getPlayerId() {
        return playerId;
    }

    private void setPlayerId(byte playerId) {
        this.playerId = playerId;
    }

    /**
     * Get the current speed of the car.
     * 
     * @return The current speed (velocity) of the car. The values go from "0" to "32768", where "32768" means the car is travelling
     *         at 100 meters per second.
     */
    public short getSpeed() {
        return speed;
    }

    private void setSpeed(short speed) {
        this.speed = speed;
    }

    public InSimVector getPosition() {
        return position;
    }

    public void setPosition(InSimVector position) {
        this.position = position;
    }

	public byte getRacePosition() {
		return racePosition;
	}

	/**
	 * this car is in the way of a driver who is a lap ahead
	 * @return
	 */
	public boolean carIsInTheWay() {
		return (info & BLUE) > 0;
	}
	
	/**
	 * this car is slow or stopped and in a dangerous place
	 * @return
	 */
	public boolean carIsSlower() {
		return (info & YELLOW) > 0;
	}
	
	/**
	 * this car is lagging
	 * @return
	 */
	public boolean carIsLagging() {
		return (info & LAGGING) > 0;
	}

	/**
	 * this is the first compcar in this set of MCI packets
	 * @return
	 */
	public boolean firstPacket() {
		return (info & FIRST) > 0;
	}
	
	/**
	 * this is the last compcar in this set of MCI packets
	 * @return
	 */
	public boolean lastPacket() {
		return (info & LAST) > 0;
	}

}
