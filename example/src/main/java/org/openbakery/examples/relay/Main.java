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

package org.openbakery.examples.relay;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.openbakery.jinsim.RelayClient;
import org.openbakery.jinsim.Tiny;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.request.relay.HostListRequest;
import org.openbakery.jinsim.request.relay.SelectHostRequest;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.relay.HostListResponse;
import org.openbakery.jinsim.response.relay.RelayErrorResponse;
import org.openbakery.jinsim.types.HostInfo;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class Main implements InSimListener {
	private RelayClient client;

	private String adminPassword;

	private List<HostInfo> hostList = Collections.emptyList();

	/**
	 * A simple constructor that doesn't do much error checking. The args have
	 * to enter in a predetermined order. See below.
	 * 
	 * @param args
	 *            The array should have 2 or 3 members. They should be in order:
	 *            hostname port password. The password is optional.
	 */
	public Main(String[] args) {
		if (args.length > 0) {
			adminPassword = args[0];
		}
	}

	public void run() {

		try {

			client = new RelayClient();
			client.connect(adminPassword);

			client.addListener(this);

			HostListRequest request = new HostListRequest();
			client.send(request);

			Thread.sleep(1000);

			connectToFirstHost();

			Thread.sleep(1000);

			client.send(new TinyRequest(Tiny.SEND_STATE_INFO));

			Thread.sleep(2000);

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

	private void connectToFirstHost() {
		for (HostInfo info : hostList) {
			if (!info.isRequiresPassword()) {
				SelectHostRequest request = new SelectHostRequest(info
						.getHostname());
				try {
					client.send(request);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	/**
	 * Standard main method to run this class.
	 * 
	 * @param args
	 *            Rudimentary parsing is used. The first argument is the
	 *            hostname, the second is the port and the (optional) third is
	 *            the admin password.
	 */
	static public void main(String[] args) {
		if (args.length <= 1) {
			Main testConnect = new Main(args);
			testConnect.run();
		} else {
			System.out
					.println("usage: org.openbakery.insim.examples.relay.Main <admin password>");
		}
	}

	public void packetReceived(InSimResponse response) {

		if (response instanceof HostListResponse) {
			HostListResponse hostListResponse = (HostListResponse) response;
			for (HostInfo info : hostListResponse.getHostList()) {
				System.out.println(info);
			}
			hostList = hostListResponse.getHostList();
		} else if (response instanceof RelayErrorResponse) {
			System.out.println(response);
		} else {
			System.out.println(response);
		}

	}

}
