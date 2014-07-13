package org.openbakery.jinsim;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class TCPChannel extends AbstractChannel {

	private Selector selector;
	private SocketChannel socketChannel;
	
    public TCPChannel(String host, int port) throws IOException {
    	this(new InetSocketAddress(host, port));
    }

    /**
     * Construct a Receiver that is ready to communicate with a LFS server at a particular address.
     * 
     * @param address
     *            A network address to send acknowledgement (ACK) packets to.
     * @throws IOException
     */
    public TCPChannel(InetSocketAddress address) throws IOException {
    	super();
 		socketChannel = SocketChannel.open(address);
 		socketChannel.configureBlocking(false);
    	selector = Selector.open();
    	socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }
	
	@Override
	protected synchronized int receive(ByteBuffer buffer) throws IOException {
		return socketChannel.read(buffer);
	}

	public int getPort() {
		return socketChannel.socket().getPort();
	}

	@Override
	protected synchronized void send(ByteBuffer sendBuffer) throws IOException {
		socketChannel.write(sendBuffer);
	}

	

}
