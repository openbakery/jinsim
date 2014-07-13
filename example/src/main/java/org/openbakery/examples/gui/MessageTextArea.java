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

import javax.swing.JEditorPane;

import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.MessageResponse;

/**
 * A text area to hold messages from LFS for the gui example.
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 * 
 */
public class MessageTextArea extends JEditorPane implements InSimListener {
    private static final long serialVersionUID = 1L;
    private String            rawData          = "";

    protected MessageTextArea() {
        setEditable(false);
        setContentType("text/html");
    }

    public void packetReceived(InSimResponse packet) {
        if (packet instanceof MessageResponse) {
            MessageResponse data = (MessageResponse) packet;
            System.out.println(data.getMessage());
            rawData += data.getMessage() + "</font><br>";
            setText(prettyPrint(rawData));
        }
    }

    private String prettyPrint(String msg) {
        StringBuffer prettyMsg = new StringBuffer();
        for (int i = 0; i < msg.length(); i++) {
            if (!(msg.charAt(i) == '\0')) {
                if ((msg.length() > i + 1) && (msg.charAt(i) == '^') && (msg.charAt(i + 1) == 'L')) {
                    i++;
                    prettyMsg.append("<font color=\"#00FF00\">");
                } else if (msg.charAt(i) == '\n') {
                    prettyMsg.append("<br>");
                } else {
                    prettyMsg.append("" + msg.charAt(i));
                }
            }
        }

        return prettyMsg.toString();
    }

}
