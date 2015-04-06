package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.CompCar;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class MultiCarInfoResponse extends InSimResponse {

	private byte numberCompCarPackages;

	private ArrayList<CompCar> carInfoList = new ArrayList<CompCar>(); // car info for each player, max 8 per packet (8 CarInfo objects)

	MultiCarInfoResponse() {
		super(PacketType.MULIT_CAR_INFO);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		numberCompCarPackages = buffer.get();

		for (int i = 0; i < numberCompCarPackages; i++) {
			carInfoList.add(new CompCar(buffer));
		}

	}

	public String toString() {
		String value = super.toString();

		value += "numberCompCarPackages: " + numberCompCarPackages;

		for (CompCar compCar : carInfoList) {
			value += "Car: " + compCar + ", ";
		}
		return value;
	}

	public void addCarInfo(CompCar info) {
		carInfoList.add(info);
	}

	public CompCar getCarInfoAt(int n) {
		return (CompCar) carInfoList.get(n);
	}

	public ArrayList<CompCar> getCarInfoList() {
		return carInfoList;
	}

}
