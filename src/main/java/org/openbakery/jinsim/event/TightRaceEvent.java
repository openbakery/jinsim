package org.openbakery.jinsim.event;

import org.openbakery.jinsim.types.CompCar;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class TightRaceEvent implements RaceEvent {
    protected CompCar leadCar;
    protected CompCar challengerCar;
    
    public TightRaceEvent(CompCar lead, CompCar challenger) {
        setLeadCar(lead);
        setChallengerCar(challenger);
    }

    public CompCar getLeadCar() {
        return leadCar;
    }

    private void setLeadCar(CompCar car) {
        this.leadCar = car;
    }

    public CompCar getChallengerCar() {
        return challengerCar;
    }

    private void setChallengerCar(CompCar challengerCar) {
        this.challengerCar = challengerCar;
    }

    public String toString() {
        String retval = "";
        
        retval += "Car " + getLeadCar().getPlayerId() + " is in a tight race with Car " + getChallengerCar().getPlayerId();
        
        return retval;
    }
}
