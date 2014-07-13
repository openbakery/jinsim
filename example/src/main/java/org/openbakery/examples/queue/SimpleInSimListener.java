package org.openbakery.examples.queue;

import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;

public class SimpleInSimListener implements InSimListener {

	private String name;

	public SimpleInSimListener(String name) {
		this.name = name;
	}
	
	public void packetReceived(InSimResponse response) {
		System.out.println(name + ": " + response);

	}

}
