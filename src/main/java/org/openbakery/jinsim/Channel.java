package org.openbakery.jinsim;

import org.openbakery.jinsim.request.InSimRequest;

import java.io.IOException;

public interface Channel extends Runnable {

	public int getPort();

	public void close() throws IOException;

	public void send(InSimRequest packet) throws IOException;

	public void setClient(Client client);

	public boolean isConnected();

}
