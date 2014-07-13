package org.openbakery.jinsim.request;

import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.Sound;

public class MessageToConnectionRequest extends InSimRequest {

	private int connectionId;
	private String message;
  private Sound sound;

	private MessageToConnectionRequest() {
		super(PacketType.MESSAGE_TO_CONNECTION, 136);
    this.sound = Sound.SILENT;
	}
	
	public MessageToConnectionRequest(int connectionId, String message) {
		this();
		this.connectionId = connectionId;
		this.message = message;
	}

    /**
     * Sends message to all connections
     * @param message
     */
  public MessageToConnectionRequest(String message) {
		this(255, message);
	}
	
	public void assemble(ByteBuffer buffer) {
        super.assemble(buffer);
        buffer.put(sound.getId());
        buffer.put((byte)connectionId);
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.put((byte)0);
        assembleString(buffer, message, 128);
    }

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public void setMessage(String message) {
    if (message == null) {
      throw new IllegalArgumentException("Message must not be null");
    }
		this.message = message;
	}

  public void setSound(Sound sound) {
    if (sound == null) {
      throw new IllegalArgumentException("Sound must not be null");
    }
    this.sound = sound;
  }
}
