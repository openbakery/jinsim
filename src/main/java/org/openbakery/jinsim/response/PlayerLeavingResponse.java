package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class PlayerLeavingResponse extends PlayerResponse {

	PlayerLeavingResponse() {
		super(PacketType.PLAYER_LEAVE);
	}

}
