/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is the JInSim Library.
 *
 * The Initial Developer of the Original Code is Rob Heiser.
 *
 * Portions created by the Initial Developer are Copyright (C) 2006
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 *   Rob Heiser <jinsim@kerf.org>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.openbakery.examples.helloworld;

import java.io.IOException;

import org.openbakery.jinsim.Channel;
import org.openbakery.jinsim.SimpleClient;
import org.openbakery.jinsim.Tiny;
import org.openbakery.jinsim.UDPChannel;
import org.openbakery.jinsim.request.MessageRequest;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.response.ConnectionLeaveResponse;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.MessageResponse;
import org.openbakery.jinsim.response.NewConnectionResponse;

/**
 * This is the "Hello World!" example for using JInSim. This is the bare minimum you need to do to see JInSim work. If you
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
public class Main implements InSimListener {
	private SimpleClient client;

	String hostname; // Address of the machine LFS is running on.

	int port; // Port on which LFS is listening for InSim requests.

	String adminPassword; // Administration password for LFS (if applicable)

	/**
	 * A simple constructor that doesn't do much error checking. The args have to enter in a predetermined order. See below.
	 * 
	 * @param args
	 *          The array should have 2 or 3 members. They should be in order: hostname port password. The password is optional.
	 */
	public Main(String[] args) {
		hostname = args[0];
		port = Integer.parseInt(args[1]);

		if (args.length > 2) {
			adminPassword = args[2];
		}
	}

	/**
	 * Generic run method. The actions it takes are intended to show a simple way of dealing with requests and responses from LFS.
	 */
	public void run() {

		try {
			// Create a Client instance to communicate with, telling it the
			// host name, port and admin password for LFS.

			client = new SimpleClient();

			Channel channel = new UDPChannel(hostname, port);

			// Let the Client know that we want to be called back when LFS send back a message
			// that's been written to the screen. An "MSO" response will come back when you send
			// a "MST" request (the TypeMessageRequest below).
			client.addListener(this);

			// Connect to LFS, intializing InSim communications and begin listening for responses
			// back from LFS.
			client.connect(channel, adminPassword, "HelloWorldTest");

			// Create a new message request to send to LFS. These are simple packets that contain
			// the type "MST" and a message up to 64 characters long.

			MessageRequest msgRequest = new MessageRequest();
			msgRequest.setMessage("Hello World!");
			client.send(msgRequest);

			client.send(new TinyRequest(Tiny.PING));
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void packetReceived(InSimResponse response) {
		// We've only registered to receive "MSO" responses, so that's all we will get, but we
		// should check the type anyway before casting the response to a MessageResponse and
		// using it.
		if (response instanceof MessageResponse) {
			MessageResponse msgResponse = (MessageResponse) response;

			// Print out the message from LFS. This will be similar to what you see on screen. The
			// "^L" before the "Hello World!" is a character set specifier that you wouldn't see
			// in LFS.
			System.out.println("Driver: " + msgResponse.getDriverName() + " says: " + msgResponse.getMessage());

		} else if (response instanceof NewConnectionResponse) {
			System.out.println(response);
		} else if (response instanceof ConnectionLeaveResponse) {
			System.out.println(response);
		}
	}

	/**
	 * Standard main method to run this class.
	 * 
	 * @param args
	 *          Rudimentary parsing is used. The first argument is the hostname, the second is the port and the (optional) third is the admin password.
	 */
	static public void main(String[] args) {
		if (args.length >= 2) {
			Main testConnect = new Main(args);
			testConnect.run();
		} else {
			System.out.println("usage: org.openbakery.insim.examples.console.Main <hostname> <port> <admin password>");
		}
	}

}
