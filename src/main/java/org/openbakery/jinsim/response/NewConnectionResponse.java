package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

public class NewConnectionResponse extends RaceTrackingResponse {

	public boolean isRemote() {
		return remote;
	}

	private String username;

	private String playername;

	private boolean isAdmin;

	private byte numberOfConnections;

	private boolean remote;

	public NewConnectionResponse() {
		super(PacketType.NEW_CONNECTION);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		username = getString(buffer, 24);
		playername = getString(buffer, 24);
		isAdmin = (buffer.get() > 0);
		numberOfConnections = buffer.get();
		remote = (buffer.get() > 0);
		buffer.position(buffer.position() + 1);
	}

	@Override
	public String toString() {
		return super.toString() + ", username: " + username + ", playername: " + playername + ", isAdmin: " + isAdmin + ", numberOfConnection: " + numberOfConnections + ", isRemote: " + isRemote();
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public byte getNumberOfConnections() {
		return numberOfConnections;
	}

	public String getPlayerName() {
		return playername;
	}

	public String getUsername() {
		return username;
	}

}
