package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class VersionResponse extends InSimResponse {

	public static final String TYPE = "VER";

	private String version;
	private String product;
	private short netVersion;

	VersionResponse() {
		super(PacketType.VERSION);
	}

	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		setVersion(getString(buffer, 8));
		setProduct(getString(buffer, 6));
		setNetVersion(buffer.getShort());
	}

	public String toString() {
		String retval = super.toString();
		retval += "Product: " + getProduct() + "\n";
		retval += "Version: " + getVersion() + "\n";
		retval += "Net Protocol Version: " + getNetVersion() + "\n";

		return retval;
	}

	public short getNetVersion() {
		return netVersion;
	}

	public void setNetVersion(short netVersion) {
		this.netVersion = netVersion;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
