package org.openbakery.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.types.InSimTime;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 * @since 0.001
 */
public class LapTimeResponse extends PlayerResponse {
		
	
    private InSimTime time;     // lap time
    private InSimTime totalTime;
  	private int lapsDone;
  	private int flags;
  	private int penalty;
  	private int numberPitStops;
    
    
    LapTimeResponse() {
        super(PacketType.LAP);
    }


    public void construct(ByteBuffer buffer) throws BufferUnderflowException {
    	super.construct(buffer);
    	setTime(new InSimTime(buffer));
        totalTime = new InSimTime(buffer);
        setLapsDone(buffer.getShort());
        setFlags(buffer.getShort());
        buffer.position(buffer.position()+1);
        setPenalty(buffer.get());
        setNumberPitStops(buffer.get());
        buffer.position(buffer.position()+1);
    }

    public String toString() {
        return super.toString() + 
        ", Laps done: " + getLapsDone() + 
        ", Flags: " + getFlags() + 
        ", Penalty: " + getPenalty() +
        ", Number of Pitstops: " + getNumberPitStops() +
        ", Time: " + getTime() +
        ", Total Time: " + totalTime;
    }

    public InSimTime getTime() {
        return time;
    }

    public void setTime(InSimTime time) {
        this.time = time;
    }

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getLapsDone() {
		return lapsDone;
	}

	public void setLapsDone(int lapsDone) {
		this.lapsDone = lapsDone;
	}

	public int getNumberPitStops() {
		return numberPitStops;
	}

	public void setNumberPitStops(int numberPitStops) {
		this.numberPitStops = numberPitStops;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}


	public InSimTime getTotalTime() {
		return totalTime;
	}

}
