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
 * Portions created by Rob Heiser are Copyright (C) 2006
 * Rob Heiser. All Rights Reserved.
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
import org.openbakery.jinsim.request.InitRequest;
import org.openbakery.jinsim.request.SmallRequest;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.response.InSimResponse;

/**
 * Primary class to use when creating a client that connects to a LFS. After
 * constructing the class and setting any options, call connect() to connect to
 * the chosen InSim machine and port.
 * 
 * @author Rob Heiser
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.5
 * 
 */
public abstract class Client {

	private Channel channel;

	protected InitRequest initRequest;

	private boolean requestVersion;

	private short interval;

	private int udpPort;

	private OutChannel outChannel;

	public short getInterval() {
		return interval;
	}

	public void setInterval(short interval) {
		this.interval = interval;
	}

	/**
	 */
	public Client() {
	}

	public void connect(Channel channel, String password, String name)
			throws IOException {
		connect(channel, password, name, (short) 0, 0, 0);
	}

	public void connect(Channel channel, String password, String name,
			short flags, int interval, int udpPort) throws IOException {
		connect(channel, password, name, flags, interval, udpPort, (char) 0);
	}

	public void connect(Channel channel, String password, String name,
			short flags, int interval, int udpPort, char prefix)
			throws IOException {
		this.channel = channel;
		this.udpPort = udpPort;
		channel.setClient(this);
		Thread channelThread = new Thread(channel);
		channelThread.start();

		initRequest = new InitRequest();
		initRequest.setRequestVersion(requestVersion);

		if (udpPort > 0 && udpPort < 65536) {
			initRequest.setUdpPort((short) udpPort);
		} else {
			initRequest.setUdpPort((short) 0);
		}
		initRequest.setPassword(password);
		initRequest.setName(name);
		initRequest.setFlags(flags);
		initRequest.setInterval((short) interval);
		initRequest.setPrefix(prefix);
		send(initRequest);

	}

	private void enableOutChannel() throws IOException {
		if (outChannel == null) {
			outChannel = new OutChannel(udpPort);
			Thread outGaugeThread = new Thread(outChannel);
			outChannel.setClient(this);
			outGaugeThread.start();
		}
	}

	public void enableOutGauge(int interval) throws IOException {
		enableOutChannel();
		SmallRequest request = new SmallRequest(Small.START_SENDING_GAUGES,
				interval);
		send(request);
	}

	public void enableOutSim(int interval) throws IOException {
		enableOutChannel();
		SmallRequest request = new SmallRequest(Small.START_SENDING_POSITION,
				interval);
		send(request);
	}

	public void disableOutGauge() throws IOException {
		enableOutGauge(0);
	}

	public void disableOutSim() throws IOException {
		enableOutSim(0);
	}

	/**
	 * Closes the connection to LFS, and stops listening for responses.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (channel != null && channel.isConnected()) {
			if (outChannel != null) {
				disableOutGauge();
				disableOutSim();
				outChannel.close();
			}
            send(new TinyRequest(Tiny.CLOSE));
			channel.close();
		}
	}

	/**
	 * Sends a request to LFS.
	 * 
	 * @param packet
	 *            A request to send to LFS, which may have a corresponding reply
	 *            or may not. If you are expecting a reply you should add a
	 *            listener for the reply type before sending the request. The
	 *            response will be handled asynchronously, so pairing requests
	 *            with replies is difficult, if not impossible.
	 * 
	 * @throws IOException
	 * @see sf.net.jinsim.request.InSimRequest
	 */
	public void send(InSimRequest packet) throws IOException {
		channel.send(packet);
	}

	public abstract void notifyListeners(InSimResponse packetData);

	public void setRequestVersion(boolean requestVersion) {
		this.requestVersion = requestVersion;
	}

	public boolean isConnected() {
		if (channel != null) {
			return channel.isConnected();
		}
		return false;
	}

}
