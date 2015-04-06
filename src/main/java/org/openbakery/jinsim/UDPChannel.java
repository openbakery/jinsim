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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Thread that waits for responses from the LFS server, and routes them to any objects that are listening for them.
 *
 * @author Rob Heiser (jinsim@kerf.org)
 * @see org.openbakery.jinsim.UDPClient
 * @see org.openbakery.jinsim.InSimResponse
 * @since 0.001
 */
public class UDPChannel extends AbstractChannel {

	private static Logger log = LoggerFactory.getLogger(UDPChannel.class);
	protected DatagramChannel datagramChannel;
	protected InetSocketAddress address;
	private ByteBuffer cacheBuffer = ByteBuffer.allocate(1024);
	private MulticastSocket multicastSocket;

	public UDPChannel(String host, int port, boolean multicast) throws IOException {
		this(new InetSocketAddress(host, port), multicast);
	}

	public UDPChannel(String host, int port) throws IOException {
		this(host, port, false);
	}

	/**
	 * Construct a Receiver that is ready to communicate with a LFS server at a particular address.
	 *
	 * @param channel   The DatagramChannel that the communication will take place on.
	 * @param address   A network address to send acknowledgement (ACK) packets to.
	 * @param multicast Receiver will relay the packets received to a multicast address if multicast is true.
	 * @throws IOException
	 */
	public UDPChannel(InetSocketAddress address, boolean multicast) throws IOException {
		super();

		datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		datagramChannel.socket().bind(null);
		// datagramChannel.connect(address);
		this.address = address;

		if (multicast) {
			InetAddress group = InetAddress.getByName("223.223.223.223");
			multicastSocket = new MulticastSocket();
			multicastSocket.joinGroup(group);
		}

	}

	public int getPort() {
		return datagramChannel.socket().getLocalPort();
	}

	@Override
	protected synchronized int receive(ByteBuffer buffer) throws IOException {

		if (cacheBuffer.position() > 0 && cacheBuffer.hasRemaining()) {
			buffer.put(cacheBuffer);
			return buffer.position();
		} else {
			byte[] data = new byte[buffer.limit()];
			cacheBuffer.clear();
			SocketAddress address = datagramChannel.receive(cacheBuffer);
			int size = cacheBuffer.position();
			if (address != null) {
				cacheBuffer.flip();
				int remaining = cacheBuffer.remaining();
				if (remaining > data.length) {
					remaining = data.length;
				}
				cacheBuffer.get(data, 0, remaining);
				buffer.put(data, 0, remaining);
				return data.length;
			}
			return size;
		}
		/*
		 * SocketAddress address = datagramChannel.receive(buffer); if (address != null) { System.out.println("has data: " + buffer.position()); return buffer.position(); } return 0;
		 */
		/*
		 * DatagramSocket socket = datagramChannel.socket();
		 * 
		 * receive(DatagramPacket p)
		 */
	}

	@Override
	public synchronized void close() throws IOException {
		super.close();
		datagramChannel.close();
	}

	@Override
	protected synchronized void send(ByteBuffer sendBuffer) throws IOException {
		datagramChannel.send(sendBuffer, address);
	}

}
