package org.openbakery.jinsim.response.relay;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.response.InSimResponse;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
abstract public class InSimRelayResponse extends InSimResponse {

	public InSimRelayResponse(PacketType packetType) {
		super(packetType);
	}


}
