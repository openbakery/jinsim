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

package org.openbakery.jinsim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.openbakery.jinsim.event.OutOfControlEvent;
import org.openbakery.jinsim.event.RaceEvent;
import org.openbakery.jinsim.event.TightRaceEvent;
import org.openbakery.jinsim.response.InSimListener;
import org.openbakery.jinsim.response.InSimResponse;
import org.openbakery.jinsim.response.MultiCarInfoResponse;
import org.openbakery.jinsim.types.CompCar;
import org.openbakery.jinsim.types.InSimVector;

/**
 * Experimental client that reports on various interesting "race events". The
 * first events are:
 * 
 * "Tight Race" - When two cars are closer than 10 meters apart "Out of Control" -
 * When a car's heading and direction differ by more than 13 degrees
 * 
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.2
 * 
 */
abstract public class RaceEventClient extends SimpleClient implements
		InSimListener {

	// private static Log log = LogFactory.getLog(RaceEventClient.class);

	@Override
	public void connect(Channel channel, String name, String password)
			throws IOException {
		addListener(this);
		super.connect(channel, name, password);
	}

	@Override
	public void connect(Channel channel, String password, String name,
			short flags, int interval, int udpPort) throws IOException {
		addListener(this);
		super.connect(channel, password, name, flags, interval, udpPort);
	}

	public void packetReceived(InSimResponse response) {
		if (response instanceof MultiCarInfoResponse) {
			ArrayList<RaceEvent> events = new ArrayList<RaceEvent>();
			MultiCarInfoResponse mciResponse = (MultiCarInfoResponse) response;
			ArrayList<CompCar> carList = mciResponse.getCarInfoList();

			CompCar car1 = null;
			CompCar car2 = null;
			double closestDistance = Double.MAX_VALUE;

			for (ListIterator<CompCar> it = carList.listIterator(); it
					.hasNext();) {
				CompCar currCar = it.next();

				if (car1 == null) {
					car1 = currCar;
				}
				/*
				 * if (log.isDebugEnabled()) { log.debug("Car 1: " + car1); }
				 */
				if (car1.getSpeed() > 10) {
					float angDelta = Math.abs(car1.getDirectionInDegrees()
							- car1.getHeadingInDegrees());
					if (angDelta > 180) {
						angDelta = 360.0f - angDelta
								+ Math.abs(car1.getDirection());
					}

					// If heading and direction differ by 13 degrees or more,
					// the car is "out of control"
					if (angDelta > 13) {
						events.add(new OutOfControlEvent(car1, angDelta));
					}
				}

				InSimVector currPos = currCar.getPosition();
				for (Iterator<CompCar> jt = carList
						.listIterator(it.nextIndex()); jt.hasNext();) {
					CompCar comparisonCar = jt.next();

					if (currCar.getPlayerId() == comparisonCar.getPlayerId()) {
						continue;
					}

					InSimVector comparisonPos = comparisonCar.getPosition();
					double currDist = comparisonPos.delta(currPos);
					if (currDist < closestDistance) {
						closestDistance = currDist;
						car1 = currCar;
						car2 = comparisonCar;
					}
				}
			}

			if (closestDistance < 10 * 65536) {
				events.add(new TightRaceEvent(car1, car2));
			}

			fireEvents(events);
		}
	}

	abstract protected void fireTightRaceEvent(TightRaceEvent event);

	abstract protected void fireOutOfControlEvent(OutOfControlEvent event);

	private void fireEvents(ArrayList<RaceEvent> events) {

		for (RaceEvent raceEvent : events) {
			RaceEvent currEvent = raceEvent;

			if (currEvent instanceof TightRaceEvent) {
				fireTightRaceEvent((TightRaceEvent) currEvent);
			} else if (currEvent instanceof OutOfControlEvent) {
				fireOutOfControlEvent((OutOfControlEvent) currEvent);
			}
		}
	}

}
