package org.openbakery.jinsim.request;

import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class SetCarCameraRequest extends InSimRequest {

    private byte cameraType; // InGameCam (as reported in StatePack)
    private byte uniqueId;   // Overrides playerIndex if set

    public SetCarCameraRequest() {
        super(PacketType.SET_CAR_CAMERA, 8);
        setCameraType((byte) 0);
        setUniqueId((byte) 0);
    }

    public void assemble(ByteBuffer data) {
        super.assemble(data);
        data.put((byte)0);
 
        data.put(uniqueId);
        data.put(cameraType);
        data.put((byte)0);
        data.put((byte)0);
        
    }

    public String toString() {
         return "SetCarCameraRequest[player=" + uniqueId + ", type=" +cameraType + "]";
    }

    public void setCameraType(byte cameraType) {
        this.cameraType = cameraType;
    }

    public void setUniqueId(byte uniqueId) {
        this.uniqueId = uniqueId;
    }

}
