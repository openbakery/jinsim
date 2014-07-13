package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class ConnectionPlayerRenameResponse extends RaceTrackingResponse {

		
    private String newName; // new name (24 len)
    private String plate;   // number plate - NO ZERO AT END! (8 len)

    ConnectionPlayerRenameResponse() {
        super(PacketType.CONNECTION_PLAYER_RENAMED);
    }

    public void construct(ByteBuffer buffer) throws BufferUnderflowException {
    	super.construct(buffer);
    	setNewName(getString(buffer, 24));
    	setPlate(getString(buffer, 8));
    }

    public String toString() {
        String retval = super.toString();
        retval += "New name: " + getNewName();
        retval += "Plate: " + getPlate();
        return retval;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }


    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

}
