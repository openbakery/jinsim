package org.openbakery.jinsim.request;

import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class InitRequest extends InSimRequest {

	public static short LOCAL = 4;
	public static short KEEP_MESSAGE_COLOR = 8;
	public static short RECEIVE_NODE_LAP = 16;
	public static short RECEIVE_MULTI_CAR_INFO = 32;
	public static short RECEIVE_COLLISION_EVENTS = 64;

	private int udpPort;                  // Local port that server communicates with
	private short flags;                 // InSim configuration
	private short interval;           // Number of seconds between tracking packets (NLP or MCI)
	private String password;              // Admin password
	private char prefix;
	private String name;
	private boolean requestVersion;

	public InitRequest() {
		super(PacketType.INSIM_INITIALIZE, (byte) 44);
	}


	public void assemble(ByteBuffer data) {
		if (requestVersion) {
			setRequestInfo((byte) 1);
		} else {
			setRequestInfo((byte) 0);
		}
		super.assemble(data);
		data.put((byte) 0); // Zero
		data.putShort((short) udpPort);
		data.putShort(flags);
		data.put((byte) 0); // spare
		data.put((byte) prefix);
		data.putShort(interval);
		assembleString(data, password, 16);
		assembleString(data, name, 16);
	}

	public String toString() {
		String retval = super.toString();

		retval += "Port: " + getPort() + "\n";
		retval += "Flags: " + getFlags() + "\n";
		retval += "Node lap interval: " + getInterval() + "\n";
		retval += "Password: " + getPassword() + "\n";

		return retval;
	}

	public short getFlags() {
		return flags;
	}

	public void setFlags(short flags) {
		this.flags = flags;
	}

	public void addFlag(int flag) {
		setFlags((byte) (getFlags() | flag));
	}

	public void removeFlag(int flag) {
		setFlags((byte) (getFlags() & ~flag));
	}

	public short getInterval() {
		return interval;
	}

	public void setInterval(short interval) {
		this.interval = interval;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return udpPort;
	}

	public void setUdpPort(int port) {
		this.udpPort = port;
	}


	public char getPrefix() {
		return prefix;
	}


	public void setPrefix(char prefix) {
		this.prefix = prefix;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setRequestVersion(boolean sendVersion) {
		this.requestVersion = sendVersion;
	}
}
