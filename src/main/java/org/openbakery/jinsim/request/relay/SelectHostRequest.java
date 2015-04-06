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

package org.openbakery.jinsim.request.relay;

import org.openbakery.jinsim.PacketType;

import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class SelectHostRequest extends InSimRelayRequest {
	private String hostname;
	private String spectatorPassword = "";
	private String adminPassword = "";

	public SelectHostRequest(String hostname, String spectatorPassword, String adminPassword) {
		super(PacketType.RELAY_SELECT_HOST, (byte) 68);
		this.hostname = hostname;
		this.spectatorPassword = spectatorPassword;
		this.adminPassword = adminPassword;
	}

	public SelectHostRequest(String hostname) {
		this(hostname, "", "");
	}


	public void assemble(ByteBuffer buffer) {
		super.assemble(buffer);
		buffer.position(buffer.position() + 1);
		assembleString(buffer, hostname, 32);
		assembleString(buffer, adminPassword, 16);
		assembleString(buffer, spectatorPassword, 16);
	}

	public String toString() {
		return "SelectHostRequest[hostname=" + hostname + "]";
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setSpectatorPassword(String spectatorPassword) {
		this.spectatorPassword = spectatorPassword;
	}

}
