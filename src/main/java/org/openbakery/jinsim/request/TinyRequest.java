package org.openbakery.jinsim.request;

import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Tiny;

/**
 * Request information about any new players that have joined the race.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * @see org.openbakery.jinsim.response.NewPlayerResponse
 */
public class TinyRequest extends InSimRequest {
	
	private Tiny subtype;
    
    public TinyRequest(Tiny subtype) {
    	this(subtype, (byte)254);
    }
    
    public TinyRequest(Tiny subtype, byte requestInfo) {
    	super(PacketType.TINY, (byte)4);
    	setSubtype(subtype);
    	setRequestInfo(requestInfo);
    }

    public void assemble(ByteBuffer data) {
        super.assemble(data);
        data.put(subtype.getType());
    }

 
    public void setSubtype(Tiny subtype) {
        this.subtype = subtype;
    }
    
    public Tiny getSubtype() {
    	return subtype;
    }

    public String toString() {
        String retval = super.toString();

        retval += ", Value: " + subtype + "\n";

        return retval;
    }

}
