package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class MultiplayerBeginResponse extends InSimResponse {

	public static final String TYPE = "ISM";

	private byte host; // 0 = guest / 1 = host

	private String name; // the name of the host joined or started (32 len)

	MultiplayerBeginResponse() {
		super(PacketType.START_MULTIPLAYER);
	}

	public void construct(ByteBuffer buffer) {
		super.construct(buffer);
		buffer.position(buffer.position() + 1);
		setHost(buffer.get());
		buffer.position(buffer.position() + 3);
		setName(getString(buffer, 32));
	}

	public String toString() {
		String retval = super.toString();
		retval += "Type: " + (getHost() == 0 ? "guest" : "host") + "\n";
		retval += "Server: " + (getName().equals("") ? "Empty (not in mp mode)" : getName()) + "\n";
		return retval;
	}

	public int getHost() {
		return host & 0xFF;
	}

	public void setHost(byte host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
