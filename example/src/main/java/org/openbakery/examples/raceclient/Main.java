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

package org.openbakery.examples.raceclient;

import java.io.IOException;

import org.openbakery.jinsim.Channel;
import org.openbakery.jinsim.Client;
import org.openbakery.jinsim.TCPChannel;
import org.openbakery.jinsim.request.InitRequest;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.MessageResponse;


/**
 * A simple example of how to use the {@link org.openbakery.UDPClient} class inside a console type application. This example does the
 * following:
 * <ul>
 * <li>Creates a Client object using the hostname, port and admin password to LFS</li>
 * <li>Tells the Client to call back to this object's implementation of the {@link org.openbakery.jinsim.response.InSimListener}
 * interface when it gets responses for; version information ("VER"), state change information ("STA"), text messages ("MSO"), and
 * OutGauge packets ("OGD").</li>
 * <li>Tells the Client to connect to LFS.</li>
 * <li>Sends a request for the LFS version information.</li>
 * <li>Sends a request to put LFS into windowed mode.</li>
 * <li>Sends a text message "/?" so that LFS gives a summary of commands available.</li>
 * <li>Sends a request to start OutGauge.</li>
 * <li>Sleeps for ten seconds to let some OutGauge packets come through.</li>
 * <li>Closes communication with LFS.</li>
 * </ul>
 * 
 * The {@link #packetReceived(InSimResponse)} method is an implementation of the
 * {@link org.openbakery.jinsim.response.InSimListener#packetReceived(InSimResponse)} interface. It will receive all of the responses that
 * were registered for. This example uses the same listener for all responses. Usually you would have different listeners for
 * different reponse types. See the source code for {@link org.openbakery.examples.gui.Main} for a better example of this usage.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
public class Main implements InSimListener {
    private Client client;
    String                  hostname;     // Address of the machine LFS is running on.
    int                     port;         // Port on which LFS is listening for InSim requests.
    String                  adminPassword; // Administration password for LFS (if applicable)

    /**
     * A simple constructor that doesn't do much error checking. The args have to enter in a predetermined order. See below.
     * 
     * @param args
     *            The array should have 2 or 3 members. They should be in order: hostname port password. The password is optional.
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
            Channel channel = new TCPChannel(hostname, port);
            
            client = new PrintEventsClient();
            client.connect(channel, adminPassword,  "RaceClient", InitRequest.RECEIVE_MULTI_CAR_INFO, 200, 0);
            
            while (true) {
            	Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
        	try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public void packetReceived(InSimResponse response) {
        if (response instanceof MessageResponse) {
            MessageResponse messageResponse = (MessageResponse) response;
            System.out.println(messageResponse.getMessage());
        }
    }

    /**
     * Standard main method to run this class.
     * 
     * @param args
     *            Rudimentary parsing is used. The first argument is the hostname, the second is the port and the (optional) third
     *            is the admin password.
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
