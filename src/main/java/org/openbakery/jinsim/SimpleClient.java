package org.openbakery.jinsim;

import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;

public class SimpleClient extends Client {


	private InSimListener listener;
	
	public void addListener(InSimListener listener) {
		if (this.listener != null) {
			throw new IllegalStateException("A listener has already be added!");
		}
		this.listener = listener;
	}
	
	public void removeListener(InSimListener listener) {
		this.listener = null;
	}
	
	public void notifyListeners(InSimResponse response) {
		listener.packetReceived(response);
	}


}
