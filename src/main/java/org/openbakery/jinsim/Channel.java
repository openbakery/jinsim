package org.openbakery.jinsim;

import java.io.IOException;

import org.openbakery.jinsim.request.InSimRequest;

public interface Channel extends Runnable {

	public int getPort();
	
	public void close() throws IOException;
	
	public void send(InSimRequest packet) throws IOException;
	
	public void setClient(Client client);
	
	public boolean isConnected();
	
}
