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

package org.openbakery.jinsim;

import java.io.IOException;

import org.openbakery.jinsim.request.InSimRequest;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.request.relay.InSimRelayRequest;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer <rene@openbakery.org>
 * @since 0.001
 */
public class RelayClient extends SimpleClient {
  private final String      relayHostname = "isrelay.lfs.net";
  private final int         relayPort     = 47474;
  
  private TCPChannel channel;
  
  /**
   * @param pw
   * @throws IOException
   */
  public RelayClient() throws IOException {
  }

  public void connect(String password) throws IOException {
    channel = new TCPChannel(relayHostname, relayPort);
	channel.setClient(this);
	Thread channelThread = new Thread(channel);
	channelThread.start();
  }

  /**
   * Closes the connection to the relay, and stops listening for responses.
   * @throws IOException 
   */
  public void close() throws IOException {
	  channel.close();
  }


  /**
   * Sends a request to the relay.
   * 
   * @param packet
   * 
   * @throws IOException
   * @see org.openbakery.jinsim.InSimRequest
   */
  public void send(InSimRequest packet) throws IOException {
	checkPacket(packet);
  	channel.send(packet);
  }
  
  private void checkPacket(InSimRequest packet) throws IOException {
	if (packet instanceof InSimRelayRequest) {
		return;
	}
	if (packet instanceof TinyRequest) {
		TinyRequest tinyRequest = (TinyRequest)packet;
		switch (tinyRequest.getSubtype()) {
			case NONE:
			case VERSION:
			case PING:
			case SEND_CAMERA_POSITION:
			case SEND_STATE_INFO:
			case GET_TIME_IN_HUNDREDS:
			case MULTIPLAYER_INFO:
			case ALL_CONNECTIONS:
			case REORDER:
			case RESTART:
			case VOTE_CANCEL:
				return;
			default:
				break;
		}
	}
	switch (packet.getType()) {
		case SINGLE_CHARACTER:
		case MESSAGE:
		case MESSAGE_TO_CONNECTION:
		case MESSAGE_EXTENDED:
		case MESSAGE_TO_LOCAL:
		case BUTTON_FUNCTION:
		case BUTTON:
			return;
		default:
			break;
	}
	throw new IOException("Cannot send packet to relay case this type is not supported by the relay: " + packet);
  }

}
