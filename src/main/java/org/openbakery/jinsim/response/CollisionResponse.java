package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.CarContact;

public class CollisionResponse extends InSimResponse {
	
	private float closingSpeed; 
	
	private short time;
	
	private CarContact carA;
	
	private CarContact carB;
	
	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);
		
		setClosingSpeed((buffer.getShort() & 0xFFF)/10f);
		setTime(buffer.getShort());
		setCarA(new CarContact(buffer));
		setCarB(new CarContact(buffer));
	}

	public CollisionResponse() {
		super(PacketType.CONTACT_CAR_CAR);
	}

	public void setClosingSpeed(float i) {
		this.closingSpeed = i;
	}

	public float getClosingSpeed() {
		return closingSpeed;
	}

	public void setTime(short time) {
		this.time = time;
	}

	public short getTime() {
		return time;
	}

	public void setCarA(CarContact carA) {
		this.carA = carA;
	}

	public CarContact getCarA() {
		return carA;
	}

	public void setCarB(CarContact carB) {
		this.carB = carB;
	}

	public CarContact getCarB() {
		return carB;
	}
	public String toString(){
		return "Closing speed - "+getClosingSpeed()+"\n"
		+"carA\n"+
		"####\n"+
		"brake - "+carA.getBrake()+"\n"+
		"accel - "+carA.getThrottle()+"\n"+
		"clutch - "+carA.getClutch()+"\n"+
		"carB\n"+
		"####\n"+
		"brake - "+carB.getBrake()+"\n"+
		"accel - "+carB.getThrottle()+"\n"+
		"clutch - "+carB.getClutch();
	}
}
