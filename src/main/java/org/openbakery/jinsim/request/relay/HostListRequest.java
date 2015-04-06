package org.openbakery.jinsim.request.relay;

import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

public class HostListRequest extends InSimRelayRequest {

	public HostListRequest() {
		super(PacketType.RELAY_HOST_LIST, (byte) 4);
	}

	@Override
	public void assemble(ByteBuffer byteBuffer) {
		super.assemble(byteBuffer);
		byteBuffer.position(byteBuffer.position() + 1);
	}

}
