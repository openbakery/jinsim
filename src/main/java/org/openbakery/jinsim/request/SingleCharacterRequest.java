package org.openbakery.jinsim.request;

import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

public class SingleCharacterRequest extends InSimRequest {

    static public final byte NONE       = 0x00;
    static public final byte SHIFT      = 0x01;
    static public final byte CTRL       = 0x02;
    static public final byte SHIFT_CTRL = 0x03;

	private char character;
	private byte flags;
	
	public SingleCharacterRequest() {
		super(PacketType.SINGLE_CHARACTER, (byte)8);
	}
	
	public SingleCharacterRequest(char character) {
		this();
		this.character = character;
	}
	
	
	public void assemble(ByteBuffer data) {
        super.assemble(data);
        data.put((byte)0);
        data.put((byte)character);
        data.put(flags);
        data.put((byte)0);
        data.put((byte)0);
    }


	public void setCharacter(char character, byte flags) {
		this.character = character;
		this.flags = flags;
	}

}
