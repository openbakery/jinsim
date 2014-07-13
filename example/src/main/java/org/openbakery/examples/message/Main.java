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

package org.openbakery.examples.message;

import java.io.IOException;

import org.openbakery.jinsim.Channel;
import org.openbakery.jinsim.SimpleClient;
import org.openbakery.jinsim.Sound;
import org.openbakery.jinsim.TCPChannel;
import org.openbakery.jinsim.request.MessageExtendedRequest;
import org.openbakery.jinsim.request.MessageToLocalComputer;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.MessageResponse;


/**
 * This is the "Hello World!" example for using JInSim. This is the bare minimum you need to
 * do to see JInSim work. If you 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
public class Main implements InSimListener {
    private SimpleClient client;
    private String hostname;
    private int port; 
    private String adminPassword;

    public Main(String[] args) {
        hostname = args[0];
        port = Integer.parseInt(args[1]);

        if (args.length > 2) {
            adminPassword = args[2];
        }
    }

    public void run() {

        try {
        	client = new SimpleClient();
            
            Channel channel = new TCPChannel(hostname, port);
            client.addListener(this);
            client.connect(channel, adminPassword, "MessageTest");

            MessageExtendedRequest msgRequest = new MessageExtendedRequest();
            msgRequest.setMessage("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
            client.send(msgRequest);

            client.send(new MessageToLocalComputer("Foobar", Sound.MESSAGE));
            
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
        if (response instanceof MessageResponse) {
            MessageResponse msgResponse = (MessageResponse) response;
            System.out.println(msgResponse.getMessage());
        }
    }

     static public void main(String[] args) {
        if (args.length >= 2) {
            Main testConnect = new Main(args);
            testConnect.run();
        } else {
            System.out.println("usage: org.openbakery.insim.message.Main <hostname> <port> <admin password>");
        }
    }

}
