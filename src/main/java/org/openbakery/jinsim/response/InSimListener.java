package org.openbakery.jinsim.response;

import org.openbakery.jinsim.UDPChannel;

/**
 * Classes that implement InSimListener can register to receive notification when any type of InSim, OutSim or OutGuage packet is
 * sent from LFS.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * @see org.openbakery.jinsim.UDPClient#addListener(String, InSimListener)
 */
public interface InSimListener {

    /**
     * This method will receive the notification from the {@link org.openbakery.UDPClient} class (through the {@link UDPChannel} class.
     * You can cast the response to the correct response type and get the information you need from there.
     * 
     * @param response
     *            will contain the packet from LFS.
     */
    public void packetReceived(InSimResponse response);
}
