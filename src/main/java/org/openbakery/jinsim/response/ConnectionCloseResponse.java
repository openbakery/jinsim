package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

public class ConnectionCloseResponse extends InSimResponse {

	public ConnectionCloseResponse() {
		super(PacketType.CLOSE);
	}

}
