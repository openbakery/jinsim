package org.openbakery.jinsim.request;

import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class ScreenModeRequest extends InSimRequest {

    private int set16Bit;   // set to choose 16-bit mode
    private int refreshRate; // refresh rate - zero for default
    private int width;      // Y Resolution, 0 = switch to windowed mode
    private int height;     // X Resolution, 0 = switch to windowed mode

    public ScreenModeRequest() {
    	super(PacketType.SCREEN_MODE, 20);
        setSet16Bit(0);
        setRefreshRate(0);
        setWidth(0);
        setHeight(0);
    }

    public void assemble(ByteBuffer data) {
        super.assemble(data);
        data.put((byte)0);
        data.putInt(getSet16Bit());
        data.putInt(getRefreshRate());
        data.putInt(getWidth());
        data.putInt(getHeight());
    }

    public String toString() {
      String retval = super.toString();
      
      retval += "16-bit: " + (getSet16Bit() == 1 ? "yes" : "no") + "\n";
      retval += "Refresh rate: " + getRefreshRate() + "\n";
      retval += "Resolution: " + getWidth() + "x" + getHeight() + "\n";
      
      return retval;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getSet16Bit() {
        return set16Bit;
    }

    public void setSet16Bit(int set16Bit) {
        this.set16Bit = set16Bit;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
