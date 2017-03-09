/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;

import java.security.InvalidParameterException;

import de.npe.gameanalytics.Analytics;
import de.npe.gameanalytics.util.JSON;


/**
 * Base event class for events that have an event_id. (business and design
 * events)
 *
 * @author NPException
 */
abstract class GAEventWithID extends GAEvent {

	private final String eventID;

	GAEventWithID(Analytics an, String eventID) {
		super(an);

		if (eventID == null || eventID.isEmpty()) {
			throw new InvalidParameterException("eventID must not be null or empty");
		}

		this.eventID = eventID;
	}

	@Override
	public void toJSON(StringBuilder sb) {
		sb.append("\"event_id\":\"").append(JSON.escape(eventID)).append("\",");
		super.toJSON(sb);
	}
}
