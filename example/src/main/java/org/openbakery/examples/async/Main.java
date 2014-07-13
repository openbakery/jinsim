package org.openbakery.examples.async;

import java.io.IOException;

import org.openbakery.jinsim.QueueClient;
import org.openbakery.jinsim.TCPChannel;
import org.openbakery.jinsim.Tiny;
import org.openbakery.jinsim.request.InSimRequest;
import org.openbakery.jinsim.request.MessageRequest;
import org.openbakery.jinsim.request.ScreenModeRequest;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;


/**
 * A simple example of how to use the {@link org.openbakery.UDPClient} class inside a console type application. This example
 * does the following:
 * <ul>
 * <li>Creates a Client object using the hostname, port and admin password to LFS</li>
 * <li>Tells the Client to call back to this object's implementation of the {@link org.openbakery.jinsim.response.InSimListener}
 * interface when it gets responses for; version information ("VER"), state change information ("STA"), text messages ("MSO"), and
 * OutGauge packets ("OGD").</li>
 * <li>Tells the Client to connect to LFS.</li>
 * <li>Sends a request for the LFS version information.</li>
 * <li>Sends a request to put LFS into windowed mode.</li>
 * <li>Sends a text message "/?" so that LFS gives a summary of commands available.</li>
 * <li>OutGauge and OutSim are enabled.</li>
 * <li>Sleeps for ten seconds to let some OutGauge and OutSim packets come through.</li>
 * <li>Closes communication with LFS.</li>
 * </ul>
 * 
 * The {@link #packetReceived(InSimResponse)} method is an implementation of the
 * {@link org.openbakery.jinsim.response.InSimListener#packetReceived(InSimResponse)} interface. It will receive all of the responses that
 * were registered for. This example uses the same listener for all responses. Usually you would have different listeners for
 * different reponse types. See the source code for {@link org.openbakery.examples.gui.Main} for a better example of this usage.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
public class Main implements InSimListener {
    private QueueClient client;
    String hostname;     // Address of the machine LFS is running on.
    int port;         // Port on which LFS is listening for InSim requests.
    String adminPassword; // Administration password for LFS (if applicable)

    /**
     * A simple constructor that doesn't do much error checking. The args have to enter in a predetermined order. See below.
     * 
     * @param args
     *            The array should have 2 or 3 members. They should be in order: hostname port password. The password is optional.
     */
    public Main(String[] args) {
        hostname = args[0];
        port = Integer.parseInt(args[1]);

        if (args.length > 2) {
            adminPassword = args[2];
        }
    }

    /**
     * Generic run method. The actions it takes are intended to show a simple way of dealing with requests and responses from LFS.
     */
    public void run() {

        try {

            client = new QueueClient();

            client.addListener(this);
            client.connect(new TCPChannel(hostname, port), adminPassword, "async", (short)0, 0, port+1);
            client.enableOutGauge(500);
            //client.enableOutSim(500);

            long startTime = System.currentTimeMillis();
            InSimRequest versionRequest = new TinyRequest(Tiny.VERSION);
            client.send(versionRequest);
            
            ScreenModeRequest modeRequest = new ScreenModeRequest();
            modeRequest.setHeight(0); // 0 in height and width will put LFS into windowed mode
            modeRequest.setWidth(0);
            client.send(modeRequest);

            InSimRequest stateRequest = new TinyRequest(Tiny.SEND_STATE_INFO);
            client.send(stateRequest);

            MessageRequest helpRequest = new MessageRequest("/?");
            client.send(helpRequest);

            MessageRequest coloredMessageRequest = new MessageRequest( MessageRequest.RED + "Red, " + MessageRequest.WHITE + "White and  " + MessageRequest.BLUE + "Blue");
            client.send(coloredMessageRequest);
            
            InSimRequest timeRequest = new TinyRequest(Tiny.GET_TIME_IN_HUNDREDS);
            client.send(timeRequest);
            
            System.out.println("Took: " + (System.currentTimeMillis() - startTime) + "ms");
            Thread.sleep(10000); // Sleep for ten seconds to let a few OutGauge packets come through.

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public void packetReceived(InSimResponse response) {
        System.out.println(response.toString());
    }

    /**
     * Standard main method to run this class.
     * 
     * @param args
     *            Rudimentary parsing is used. The first argument is the hostname, the second is the port and the (optional) third
     *            is the admin password.
     */
    static public void main(String[] args) {
        if (args.length >= 2) {
            Main testConnect = new Main(args);
            testConnect.run();
        } else {
            System.out.println("usage: org.openbakery.insim.examples.console.Main <hostname> <port> <admin password>");
        }
    }

}
