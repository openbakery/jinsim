package org.openbakery.jinsim.request;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Small;

import java.nio.ByteBuffer;

public class SmallRequest extends InSimRequest {

	private Small small;
	private int value;

	public SmallRequest(Small small) {
		this(small, 0);
	}

	public SmallRequest(Small small, int value) {
		super(PacketType.SMALL, (byte) 8);
		this.small = small;
		requestInfo = (byte) 254;
		this.value = value;
	}

	public void assemble(ByteBuffer data) {
		super.assemble(data);
		data.put(small.getType());
		data.putInt(value);
	}


	public String toString() {
		return super.toString() + ", subtype: " + small + ", value: " + value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
