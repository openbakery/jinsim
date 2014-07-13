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

package org.openbakery.examples.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openbakery.jinsim.SimpleClient;
import org.openbakery.jinsim.Tiny;
import org.openbakery.jinsim.UDPChannel;
import org.openbakery.jinsim.request.InSimRequest;
import org.openbakery.jinsim.request.SetDirectCameraRequest;
import org.openbakery.jinsim.request.TinyRequest;
import org.openbakery.jinsim.response.CameraPositionResponse;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.MessageResponse;
import org.openbakery.jinsim.response.StateResponse;
import org.openbakery.jinsim.response.VersionResponse;
import org.openbakery.jinsim.types.InSimVector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple JPanel that holds widgets to demonstrate how to use JInSim controlled through a GUI.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
public class JInSimTestPanel extends JPanel implements InSimListener, ChangeListener {
	private static final long serialVersionUID = 1L;

	private JTextArea errors = new JTextArea(); // Somewhere to dump errors

	private JSlider headingSlider = new JSlider(Short.MIN_VALUE, Short.MAX_VALUE);

	private JSlider pitchSlider = new JSlider(Short.MIN_VALUE, Short.MAX_VALUE);

	private JSlider rollSlider = new JSlider(Short.MIN_VALUE, Short.MAX_VALUE);

	private JTextArea versionText;

	private JTextArea stateText;

	private JTextArea messageText;

	private SimpleClient client;

	private SetDirectCameraRequest cameraPosRequest;

	private static Logger log = LoggerFactory.getLogger(JInSimTestPanel.class);

	/**
	 * Constructs a JPanel that contains:
	 * <ul>
	 * <li>A button to request version information</li>
	 * <li>A text area to display the returned version information</li>
	 * <li>A button to request state information</li>
	 * <li>A text area to display the returned state information</li>
	 * <li>A text area to display any messages from LFS</li>
	 * <li>A Quit button</li>
	 * <li>A text area to display any errors that happen while the application is running</li>
	 * 
	 * @param hostname
	 *          the hostname where the LFS process to connect to is running.
	 * @param port
	 *          the port on the host where LFS is listening for InSim requests.
	 * @param password
	 *          the admin password for LFS.
	 * @throws Exception
	 */
	public JInSimTestPanel(String hostname, int port, String password) throws Exception {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel cameraControlPanel = new JPanel();
		cameraControlPanel.setLayout(new BoxLayout(cameraControlPanel, BoxLayout.Y_AXIS));

		JPanel headingPanel = new JPanel();
		headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.X_AXIS));
		headingSlider.setEnabled(false);
		headingSlider.setPaintLabels(true);
		headingSlider.setPaintTicks(true);
		headingSlider.setMajorTickSpacing(32767);
		headingSlider.setLabelTable(headingSlider.createStandardLabels(66000, 0));
		headingSlider.addChangeListener(this);

		headingPanel.add(headingSlider);
		headingPanel.add(new JLabel("Heading"));
		cameraControlPanel.add(headingPanel);

		JPanel pitchPanel = new JPanel();
		pitchPanel.setLayout(new BoxLayout(pitchPanel, BoxLayout.X_AXIS));
		pitchSlider.setEnabled(false);
		pitchSlider.setPaintLabels(true);
		pitchSlider.setPaintTicks(true);
		pitchSlider.setMajorTickSpacing(32767);
		pitchSlider.setLabelTable(headingSlider.createStandardLabels(66000, 0));
		pitchSlider.addChangeListener(this);

		pitchPanel.add(pitchSlider);
		pitchPanel.add(new JLabel("Pitch"));
		cameraControlPanel.add(pitchPanel);

		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new BoxLayout(rollPanel, BoxLayout.X_AXIS));
		rollSlider.setEnabled(false);
		rollSlider.setPaintLabels(true);
		rollSlider.setPaintTicks(true);
		rollSlider.setMajorTickSpacing(32767);
		rollSlider.setLabelTable(headingSlider.createStandardLabels(66000, 0));
		rollSlider.addChangeListener(this);

		rollPanel.add(rollSlider);
		rollPanel.add(new JLabel("Roll"));
		cameraControlPanel.add(rollPanel);

		add(cameraControlPanel);

		JButton versionButton = new JButton("Version");
		versionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InSimRequest request = new TinyRequest(Tiny.VERSION);
				try {
					client.send(request);

					InSimRequest cameraRequest = new TinyRequest(Tiny.SEND_CAMERA_POSITION);
					client.send(cameraRequest);

				} catch (IOException e1) {
					errors.setText(e1.getMessage());
				}
			}
		});

		add(versionButton);

		versionText = new JTextArea();
		versionText.setSize(new Dimension(250, 100));
		add(versionText);

		JButton stateButton = new JButton("Request Info");
		stateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InSimRequest statePacket = new TinyRequest(Tiny.SEND_STATE_INFO);
				try {
					client.send(statePacket);
				} catch (IOException e1) {
					errors.setText(e1.getMessage());
				}
			}
		});

		add(stateButton);

		stateText = new JTextArea();
		stateText.setSize(new Dimension(250, 100));
		JScrollPane stateTextPane = new JScrollPane(stateText);
		stateTextPane.setPreferredSize(new Dimension(250, 100));

		add(stateTextPane);

		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.close();
					System.exit(0);
				} catch (Exception e1) {
					errors.setText(e1.getMessage());
				}
			}
		});

		messageText = new JTextArea();
		JScrollPane messageTextPane = new JScrollPane(messageText);
		messageTextPane.setPreferredSize(new Dimension(250, 100));

		add(messageTextPane);

		add(quitButton);

		add(errors);

		client = new SimpleClient();
		client.connect(new UDPChannel(hostname, port), password, "GUI");
		client.addListener(this);

		InSimRequest cameraRequest = new TinyRequest(Tiny.SEND_CAMERA_POSITION);
		client.send(cameraRequest);

	}

	public void packetReceived(InSimResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("packetReceived: " + response);
		}
		if (response instanceof CameraPositionResponse) {
			CameraPositionResponse cameraResponse = (CameraPositionResponse) response;
			cameraPosRequest = new SetDirectCameraRequest(cameraResponse);
			cameraPosRequest.setPosition(new InSimVector(0, 0, 0));
			cameraPosRequest.setFlags(SetDirectCameraRequest.ISS_VIEW_OVERRIDE);
			cameraPosRequest.setCameraType(StateResponse.CUSTOM_VIEW);
			cameraPosRequest.setTime((short) 500);

			headingSlider.setValue(cameraPosRequest.getHeading());
			headingSlider.setEnabled(true);
			pitchSlider.setValue(cameraPosRequest.getPitch());
			pitchSlider.setEnabled(true);
			rollSlider.setValue(cameraPosRequest.getRoll());
			rollSlider.setEnabled(true);
		} else if (response instanceof VersionResponse) {
			versionText.setText(response.toString());
		} else if (response instanceof StateResponse) {
			stateText.setText(response.toString());
		} else if (response instanceof MessageResponse) {
			messageText.append(((MessageResponse) response).getMessage() + "\n");
		} else {
			log.debug("Unknown response: " + response);
		}
	}

	public void stateChanged(ChangeEvent event) {
		if (cameraPosRequest == null) {
			errors.append("Cannot set camera!");
			return;
		}
		JSlider source = (JSlider) event.getSource();

		if (source == headingSlider) {
			cameraPosRequest.setHeading((short) (source.getValue() * -1));
		} else if (source == pitchSlider) {
			cameraPosRequest.setPitch((short) (source.getValue() * -1));
		} else if (source == rollSlider) {
			cameraPosRequest.setRoll((short) (source.getValue() * -1));
		}

		try {
			client.send(cameraPosRequest);
		} catch (IOException e1) {
			errors.setText(e1.getMessage());
		}

	}

}
