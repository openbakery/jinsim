package org.openbakery.examples.nodelap;

import java.io.IOException;

import org.openbakery.jinsim.Channel;
import org.openbakery.jinsim.SimpleClient;
import org.openbakery.jinsim.TCPChannel;
import org.openbakery.jinsim.Tiny;
import org.openbakery.jinsim.request.MessageExtendedRequest;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.NodeLapInfoResponse;

public class NodeLapExample implements InSimListener {
    private SimpleClient client;
    private String hostname;
    private int port; 
    private String adminPassword;

    public NodeLapExample(String[] args) {
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
            client.connect(channel, adminPassword, "NodeLapExample");

            MessageExtendedRequest msgRequest = new MessageExtendedRequest();
            msgRequest.setMessage("NodeLapExample");
            client.send(msgRequest);
            
            int i = 0;
            while(i<10) {
                client.send(new TinyRequest(Tiny.NODE_LAP));
                Thread.sleep(500);
                i++;
            }
            
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
        if (response instanceof NodeLapInfoResponse) {
        	NodeLapInfoResponse nodeLap = (NodeLapInfoResponse)response;
            System.out.println(nodeLap);
        } 
    }

     static public void main(String[] args) {
        if (args.length >= 2) {
        	NodeLapExample testConnect = new NodeLapExample(args);
            testConnect.run();
        } else {
            System.out.println("usage: org.openbakery.insim.nodelap.NodeLapExample <hostname> <port> <admin password>");
        }
    }

}
