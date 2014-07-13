package org.openbakery.examples.button;

import java.io.IOException;

import org.openbakery.jinsim.Channel;
import org.openbakery.jinsim.SimpleClient;
import org.openbakery.jinsim.UDPChannel;
import org.openbakery.jinsim.request.ButtonRequest;
import org.openbakery.jinsim.response.ButtonClickedResponse;
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
	private boolean isClicked = false;

    public Main(String[] args) {
        hostname = args[0];
        port = Integer.parseInt(args[1]);

        if (args.length > 2) {
            adminPassword = args[2];
        }
    }

    public void run() {
    	System.out.println("Run button test");
        try {
        	client = new SimpleClient();
        	
            
            Channel channel = new UDPChannel(hostname, port);
            client.addListener(this);
            client.connect(channel, adminPassword, "ButtonTest");

            ButtonRequest buttonRequest = new ButtonRequest();
            buttonRequest.setButtonStyle(ButtonRequest.BUTTON_STYLE_GREEN | ButtonRequest.BUTTON_STYLE_LIGHT | ButtonRequest.BUTTON_STYLE_CLICK);
            buttonRequest.setText("My first Button");
            buttonRequest.setConnectionId(255);
            buttonRequest.setClickId((byte)1);
            buttonRequest.setRequestInfo((byte)1);
            buttonRequest.setLeft((byte)75);
            buttonRequest.setTop((byte)30);
            buttonRequest.setWidth((byte)50);
            buttonRequest.setHeight((byte)5);
            client.send(buttonRequest);
            while (!isClicked) {
            	Thread.sleep(100);
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
        if (response instanceof MessageResponse) {
            MessageResponse msgResponse = (MessageResponse) response;
            System.out.println(msgResponse.getMessage());
        } else if (response instanceof ButtonClickedResponse) {
        	ButtonClickedResponse buttonClickResponse = (ButtonClickedResponse)response;
        	isClicked = true;
            System.out.println("Left Click: " + buttonClickResponse.isLeftClick());
            System.out.println("Right Click: " + buttonClickResponse.isRightClick());
            System.out.println("Ctrl: " + buttonClickResponse.isCtrl());
            System.out.println("Shift: " + buttonClickResponse.isShift());
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
