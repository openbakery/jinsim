package org.openbakery.jinsim.response.relay;

import org.openbakery.jinsim.PacketType;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class RelayErrorResponse extends InSimRelayResponse {

	private RelayError error;

	public RelayErrorResponse() {
		super(PacketType.RELAY_ERROR);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		error = RelayError.getById(buffer.get());
	}

	public RelayError getError() {
		return error;
	}

	public String toString() {
		return "RelayErrorResponse[error=" + error + "]";
	}

	enum RelayError {
		INVALID_PACKET(1),
		INVALID_PACKET_TYPE(2),
		WRONG_HOSTNAME(3),
		WRONG_ADMIN_PASSWORD(4),
		WRONG_SPECTATOR_PASSWORD(5);

		private int id;

		RelayError(int id) {
			this.id = id;
		}

		static RelayError getById(byte id) {
			int givenId = id & 0xFF;
			for (RelayError error : RelayError.values()) {
				if (error.id == givenId) {
					return error;
				}
			}
			throw new IllegalArgumentException("Given id is not a valid relay error id: " + id);
		}


	}
}
