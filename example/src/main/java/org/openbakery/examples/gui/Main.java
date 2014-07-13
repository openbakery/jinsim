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

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A bare-bones class to construct and show a JFrame that contains widgets to control JInSim.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
public class Main {
    private String hostname;
    private int    port;
    private String adminPassword;

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
     * Create a JFrame and put a JInSimTestPanel in it. Most of the work is done in JInSimTestPanel.
     */
    public void run() {
        try {
            JFrame mainFrame = new JFrame("JInSim");

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JInSimTestPanel jinsimPanel = new JInSimTestPanel(hostname, port, adminPassword);

            mainFrame.getContentPane().add(jinsimPanel);
            mainFrame.setSize(new Dimension(500, 600));
            mainFrame.setLocation(300, 50);
            mainFrame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args -
     *            unused
     */
    static public void main(String[] args) {
        if (args.length >= 2) {
            Main testConnect = new Main(args);
            testConnect.run();
        } else {
            System.out.println("usage: org.openbakery.insim.examples.gui.Main <hostname> <port> <admin password>");
        }
    }
}
