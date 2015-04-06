package org.openbakery.jinsim;

import org.openbakery.jinsim.request.InSimRequest;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.response.ConnectionCloseResponse;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.ResponseFactory;
import org.openbakery.jinsim.response.UnhandledPacketTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class AbstractChannel implements Channel {
	protected static final int BUFFER_SIZE = 512;
	private static final long TIMEOUT = 30000;
	private static Logger log = LoggerFactory.getLogger(AbstractChannel.class);
	protected Client client;

	protected boolean running;

	protected ByteBuffer sendBuffer;

	public AbstractChannel() {
		sendBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		sendBuffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	/**
	 * Close down the receiver. This means you don't want to receive any more responses from the LFS server.
	 */
	synchronized public void close() throws IOException {
		running = false;
		client.notifyListeners(new ConnectionCloseResponse());
	}

	public void run() {
		running = true;

		ResponseFactory packetFactory = ResponseFactory.getInstance();
		ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		ByteBuffer sizeBuffer = ByteBuffer.allocate(1);

		long currentTime = System.currentTimeMillis();
		int size = 0;
		InSimResponse packetData = null;
		while (running) {
			try {

				// read the packet size first
				if (size == 0) {
					sizeBuffer.clear();
					int numberRead = -1;
					try {
						numberRead = receive(sizeBuffer);
					} catch (IOException ex) {
						// do nothing chase numberRead is already -1
					}
					if (numberRead == -1) {
						close();
						return;
					}

					if (numberRead > 0) {
						sizeBuffer.flip();
						size = (sizeBuffer.get() & 0xff) - 1;
						if (size < 0) {
							size = 0;
						} else {
							buffer.limit(size);
						}
					}
				}

				if (size != 0) {
					// now we know the packet size, so fill the buffer with all
					// packet data

					int numberRead = receive(buffer);
					if (numberRead == -1) {
						close();
						return;
					}
				}

				if (size > 0 && buffer.position() == size) {
					// the buffer contains all data of the packet so lets
					// process the packet
					buffer.flip();
					/*
					 * if (log.isDebugEnabled()) { byte[] bytes = new byte[buffer.limit()]; buffer.get(bytes); buffer.rewind(); String message = ("RECEIVE: bytes in buffer[" + (size+1) + "]: [" ); for (int i=0;
					 * i<buffer.limit(); i++) { message += (bytes[i] + ", "); } log.debug(message + "]");
					 * 
					 * }
					 */
					try {
						packetData = packetFactory.getPacketData(buffer);
					} catch (UnhandledPacketTypeException ex) {
						log.error("Unknown packet: {}", ex.getMessage());
					}
					buffer.flip();
					size = 0;
					client.notifyListeners(packetData);
				}

				if (currentTime + TIMEOUT < System.currentTimeMillis()) {
					client.send(new TinyRequest(Tiny.PING));
					currentTime = System.currentTimeMillis();
				}
				Thread.sleep(10);
			} catch (Exception e) {
				log.error("Something went wrong!", e);
			}
		}

	}

	public synchronized void send(InSimRequest packet) throws IOException {
		packet.assemble(sendBuffer);
		sendBuffer.flip();

		int size = packet.getSize();
		if (log.isDebugEnabled()) {
			byte[] bytes = new byte[size];
			sendBuffer.get(bytes);
			sendBuffer.flip();
			if (log.isDebugEnabled()) {
				String message = ("SEND: bytes in " + packet.getType() + " buffer: [");
				for (int i = 0; i < size; i++) {
					message += (bytes[i] + ", ");
				}
				log.debug(message + "]");
			}

		}
		send(sendBuffer);
	}

	protected abstract void send(ByteBuffer sendBuffer) throws IOException;

	public void setClient(Client client) {
		this.client = client;

	}

	protected abstract int receive(ByteBuffer buffer) throws IOException;

	public boolean isConnected() {
		return running;
	}

}
