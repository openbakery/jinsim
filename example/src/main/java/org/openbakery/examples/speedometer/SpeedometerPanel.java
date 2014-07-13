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

package org.openbakery.examples.speedometer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.openbakery.jinsim.SimpleClient;
import org.openbakery.jinsim.UDPChannel;
import org.openbakery.jinsim.request.InitRequest;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.NodeLapInfoResponse;
import org.openbakery.jinsim.response.OutGaugeResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.2
 */
public class SpeedometerPanel extends JPanel implements InSimListener {

	private static Logger log = LoggerFactory.getLogger(SpeedometerPanel.class);

	private static final long serialVersionUID = 1L;

	private BufferedImage speedometerImage;

	private BufferedImage needleImage;

	private double speed;

	/**
	 * A panel that draws a speedometer based on the speed reported by OutGauge packets.
	 * 
	 * @param hostname
	 *          hostname of the computer running LFS
	 * @param port
	 *          port that LFS is listening for InSim packets on
	 * @param password
	 *          admin password in LFS
	 * @throws ImageFormatException
	 * @throws IOException
	 */
	public SpeedometerPanel(String hostname, int port, String password) throws IOException {
		SimpleClient client = new SimpleClient();

		// register for Errors and OutGaugeResponses
		client.addListener(this);

		client.connect(new UDPChannel(hostname, port), password, "Speedometer", InitRequest.RECEIVE_NODE_LAP, 500, port + 1);
		client.enableOutGauge(1);

		// These are the graphics
		speedometerImage = ImageIO.read(new File("resources/speedometer.jpg"));
		needleImage = ImageIO.read(new File("resources/needle.png"));
		Dimension size = new Dimension(speedometerImage.getWidth(), speedometerImage.getHeight());
		setSize(size);
		setPreferredSize(size);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		// draw the background gauge image
		g2.drawImage(speedometerImage, 0, 0, null);

		// Figure out how many degrees we have to rotate the needle. It's
		// (unfortunately)
		// drawn 90 degrees off, so we add 90 to the number of degrees to rotate
		// so that it
		// sits at 0 when speed is 0. The gauge is a 270 degree gauge that goes
		// to 220 mph.
		// So, each degree is (270.0/220.0) degrees.

		double degreesRotation = (270.0 / 220.0) * speed + 90;

		// Java2D works in radians, and gives us a way to do the conversion.
		double radiansToRotate = Math.toRadians(degreesRotation);

		// Save the original transform to restore it later.
		AffineTransform savedTransform = g2.getTransform();

		// Rotate before we draw the needle. Then draw it.
		g2.rotate(radiansToRotate, speedometerImage.getWidth() / 2, speedometerImage.getHeight() / 2);
		g2.drawImage(needleImage, speedometerImage.getWidth() / 2, speedometerImage.getHeight() / 2, null);

		// Restore the transform for the next drawing.
		g2.setTransform(savedTransform);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openbakery.jinsim.response.InSimListener#packetReceived(org.openbakery.jinsim.response .InSimResponse)
	 */
	public void packetReceived(InSimResponse response) {
		if (response instanceof OutGaugeResponse) {

			OutGaugeResponse outGaugeResponse = (OutGaugeResponse) response;
			// if (log.isDebugEnabled()) {
			// log.debug(outGaugeResponse);
			// }
			// Set the speed when we get an OutGauge packet. Convert from m/s to
			// mph.
			speed = outGaugeResponse.getSpeed() * 2.2360248447205;
			repaint();
		} else if (response instanceof NodeLapInfoResponse) {
			log.debug("{}", response);
		} else {
			log.debug("{}", response);
		}
	}
}
