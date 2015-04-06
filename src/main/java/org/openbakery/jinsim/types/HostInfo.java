package org.openbakery.jinsim.types;

import org.openbakery.jinsim.Track;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class HostInfo {

	private String hostname;
	private Track track;
	private boolean passwordRequired;
	private boolean licensed;
	private int numberConnections;


	public HostInfo(String name, Track track, boolean licensed,
									boolean requirePassword, byte numberConnections) {

		this.hostname = name;
		this.track = track;
		this.licensed = licensed;
		this.passwordRequired = requirePassword;
		this.numberConnections = numberConnections;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public boolean isRequiresPassword() {
		return passwordRequired;
	}

	public void setRequiresPassword(boolean requiresPassword) {
		this.passwordRequired = requiresPassword;
	}

	public boolean isLicensed() {
		return licensed;
	}

	public void setLicensed(boolean licensed) {
		this.licensed = licensed;
	}

	public int getNumberConnections() {
		return numberConnections;
	}

	public void setNumberConnections(int numberConnections) {
		this.numberConnections = numberConnections;
	}

	public String toString() {
		return "HostInfo[hostname=" + hostname + ", track=" + track + ", licenced=" + licensed + ", requiresPassword=" + passwordRequired +
						", numberConnections= " + numberConnections + "]";
	}

}
