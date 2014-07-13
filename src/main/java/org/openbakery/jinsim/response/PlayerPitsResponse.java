package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class PlayerPitsResponse extends	PlayerResponse {

    PlayerPitsResponse() {
    	super(PacketType.PLAYER_PIT);
    }

}
