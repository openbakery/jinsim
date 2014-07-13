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

package org.openbakery.examples.camera;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
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
import org.openbakery.jinsim.response.StateResponse;
import org.openbakery.jinsim.types.InSimVector;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class CameraControlPanel extends JPanel implements InSimListener {

    private static final long      serialVersionUID = 1L;

    private SetDirectCameraRequest camera;
    final JSlider                  headingSlider    = new JSlider(Short.MIN_VALUE, Short.MAX_VALUE);
    final JSlider                  pitchSlider      = new JSlider(Short.MIN_VALUE, Short.MAX_VALUE);
    final JSlider                  rollSlider       = new JSlider(Short.MIN_VALUE, Short.MAX_VALUE);

    long                           lastupdate;
    private SimpleClient client;

    /**
     * @param hostname
     * @param port
     * @param adminPassword
     * @throws IOException
     */
    public CameraControlPanel(String hostname, int port, String adminPassword) throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        client = new SimpleClient();
        client.addListener(this);
        client.connect(new UDPChannel(hostname, port), adminPassword, "Camera");

        InSimRequest setStateRequest = new TinyRequest(Tiny.SEND_STATE_INFO);
        client.send(setStateRequest);



        JPanel cameraControlPanel = new JPanel();
        cameraControlPanel.setLayout(new BoxLayout(cameraControlPanel, BoxLayout.Y_AXIS));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.X_AXIS));
        headingSlider.setEnabled(false);
        headingSlider.setPaintLabels(true);
        headingSlider.setPaintTicks(true);
        headingSlider.setMajorTickSpacing(32767);
        headingSlider.setLabelTable(headingSlider.createStandardLabels(66000, 0));
        headingSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (camera == null || (System.currentTimeMillis() - lastupdate < 100)) {
                    return;
                }

                lastupdate = System.currentTimeMillis();
                camera.setHeading((short) (source.getValue() * -1));

                try {
                    client.send(camera);
                } catch (IOException e1) {
                }
            }
        });

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
        pitchSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (camera == null) {
                    return;
                }

                camera.setPitch((short) (source.getValue() * -1));

                try {
                    client.send(camera);
                } catch (IOException e1) {
                }
            }
        });

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
        rollSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (camera == null) {
                    return;
                }

                camera.setRoll((short) (source.getValue() * -1));

                try {
                    client.send(camera);
                } catch (IOException e1) {
                }
            }
        });

        rollPanel.add(rollSlider);
        rollPanel.add(new JLabel("Roll"));
        cameraControlPanel.add(rollPanel);

        add(cameraControlPanel);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	InSimRequest closePacket = new TinyRequest(Tiny.CLOSE);
                try {
                    client.send(closePacket);
                    client.close();
                    System.exit(0);
                } catch (Exception e1) {
                }
            }
        });

        add(quitButton);

        

        InSimRequest cameraPosRequest = new TinyRequest(Tiny.SEND_CAMERA_POSITION);
        client.send(cameraPosRequest);
        
        validate();
    }

    public void packetReceived(InSimResponse response) {
        if (response instanceof CameraPositionResponse) {
            CameraPositionResponse cameraResponse = (CameraPositionResponse) response;
            camera = new SetDirectCameraRequest(cameraResponse);
            camera.setPosition(new InSimVector(0, 0, 0));
            camera.setFlags(SetDirectCameraRequest.ISS_VIEW_OVERRIDE);
            camera.setCameraType(StateResponse.CUSTOM_VIEW);
            camera.setTime((short) 500);

            headingSlider.setValue(camera.getHeading());
            headingSlider.setEnabled(true);
            pitchSlider.setValue(camera.getPitch()+1);
            pitchSlider.setEnabled(true);
            rollSlider.setValue(camera.getRoll()+1);
            rollSlider.setEnabled(true);
        }
    }

}
