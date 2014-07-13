package org.openbakery.examples.queue;

import java.io.IOException;

import org.openbakery.jinsim.Channel;
import org.openbakery.jinsim.QueueClient;
import org.openbakery.jinsim.TCPChannel;
import org.openbakery.jinsim.Tiny;
import org.openbakery.jinsim.request.TinyRequest;


/**
 * This example demonstrates how to use the QueueClient for multiple insim listeners
 * 
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.5
 * 
 */
public class Main {
    private QueueClient client;
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
        	client = new QueueClient();
            
            Channel channel = new TCPChannel(hostname, port);
            SimpleInSimListener firstListener = new SimpleInSimListener("====> FIRST");
            SimpleInSimListener secondListener = new SimpleInSimListener("====> SECOND");
            client.addListener(firstListener);
            client.addListener(secondListener);
    		client.setRequestVersion(false);

            client.connect(channel, adminPassword, "QueueTest");

            client.send(new TinyRequest(Tiny.VERSION));
            
            client.send(new TinyRequest(Tiny.VERSION), firstListener);
            client.send(new TinyRequest(Tiny.VERSION), firstListener);
            client.send(new TinyRequest(Tiny.VERSION), firstListener);
            
            client.send(new TinyRequest(Tiny.VERSION), secondListener);
            
            client.send(new TinyRequest(Tiny.VERSION), firstListener);
            client.send(new TinyRequest(Tiny.VERSION), firstListener);
            client.send(new TinyRequest(Tiny.VERSION), firstListener);
            
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


     static public void main(String[] args) {
        if (args.length >= 2) {
            Main testConnect = new Main(args);
            testConnect.run();
        } else {
            System.out.println("usage: org.openbakery.insim.queue.Main <hostname> <port> <admin password>");
        }
    }

}
