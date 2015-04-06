package org.openbakery.jinsim.response;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class UnhandledPacketTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construct an exception for an unknown packet type with a message describing the reason.
	 *
	 * @param msg a brief message about why this exception was thrown
	 */
	public UnhandledPacketTypeException(String msg) {
		super(msg);
	}

}
