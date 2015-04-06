package org.openbakery.jinsim;

import org.openbakery.jinsim.request.InSimRequest;
import org.openbakery.jinsim.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

public class OutChannel implements Channel {

	private static final int BUFFER_SIZE = 1024;

	private static final int OUT_GAUGE_SIZE = 96;

	private static final int OUT_SIM_SIZE = 68;
	static Logger log = LoggerFactory.getLogger(UDPChannel.class);
	protected DatagramChannel datagramChannel;
	protected InetSocketAddress address;
	private Client client;
	private boolean running;

	public OutChannel(int port) throws IOException {
		this(new InetSocketAddress(port));
	}

	public OutChannel(InetSocketAddress inetSocketAddress) throws IOException {
		super();
		address = inetSocketAddress;
		datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		datagramChannel.socket().bind(new InetSocketAddress(address.getPort()));
	}

	public void close() throws IOException {
		running = false;
	}

	public int getPort() {
		return address.getPort();
	}

	public void send(InSimRequest packet) throws IOException {
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void run() {
		running = true;
		ResponseFactory packetFactory = ResponseFactory.getInstance();
		InSimResponse packetData = null;

		ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		while (running) {
			try {

				datagramChannel.receive(buffer);

				// if (log.isDebugEnabled()) {
				// log.debug("bytes read: " + buffer.position());
				// }

				if (buffer.position() == OUT_GAUGE_SIZE || buffer.position() == OUT_GAUGE_SIZE - 4) {
					buffer.flip();
					/*
					 * if (log.isDebugEnabled()) { byte[] bytes = new byte[buffer.limit()]; buffer.get(bytes); buffer.rewind(); String message = ("OUTGAUGE RECEIVE: bytes in buffer[" + (buffer.limit()) + "]: ["
					 * ); for (int i=0; i<buffer.limit(); i++) { message += (bytes[i] + ", "); } log.debug(message + "]"); }
					 */

					OutGaugeResponse reponse = new OutGaugeResponse();
					reponse.construct(buffer);
					client.notifyListeners(reponse);
				} else if (buffer.position() == OUT_SIM_SIZE || buffer.position() == OUT_SIM_SIZE - 4) {
					buffer.flip();

					// if (log.isDebugEnabled()) {
					// byte[] bytes = new byte[buffer.limit()];
					// buffer.get(bytes);
					// buffer.rewind();
					// String message = ("OUTSIM RECEIVE: bytes in buffer[" +
					// (buffer.limit()) + "]: [" );
					// for (int i=0; i<buffer.limit(); i++) {
					// message += (bytes[i] + ", ");
					// }
					// log.debug(message + "]");
					// }
					OutSimResponse response = new OutSimResponse();
					response.construct(buffer);
					client.notifyListeners(response);
				} else if (buffer.position() >= 2) {
					// if the package is a normal insim package then drop the
					// data

					int position = buffer.position();
					buffer.flip();
					int size = buffer.get() & 0xFF;
					int packetId = buffer.get() & 0xFF;
					if (packetId == 26) {
						log.debug("possible error?");
					}
					PacketType packetType = PacketType.getPacket(packetId);
					if (packetType != null && !packetType.isRelayResponse()) {
						log.debug("process packet: " + packetType);
						buffer.position(position);
						buffer.limit(size);
						while (buffer.position() < size) {
							datagramChannel.receive(buffer);
							Thread.sleep(10);
						}

						buffer.flip();
						buffer.position(1); // skip size
						try {
							packetData = packetFactory.getPacketData(buffer);
							client.notifyListeners(packetData);
						} catch (UnhandledPacketTypeException ex) {
							log.error("Unknown packet: " + ex.getMessage());
						}

						// if (log.isDebugEnabled()) {
						// log.debug("skip package: " + packetType);
						// }
					} else {
						buffer.position(position);
					}
				}
				buffer.clear();
				Thread.sleep(10);
			} catch (Exception e) {
				log.error("Something went wrong!", e);
			}
		}
	}

	public boolean isConnected() {
		return running;
	}

}
