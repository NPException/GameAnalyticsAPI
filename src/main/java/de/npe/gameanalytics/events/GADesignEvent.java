/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;


import de.npe.gameanalytics.Analytics;
import de.npe.gameanalytics.util.JSON;


/**
 * @author NPException
 */
public class GADesignEvent extends GAEventWithID {

	private final String area;

	private final Float value;

	public GADesignEvent(Analytics an, String eventID, String area, Number value) {
		super(an, eventID);
		this.area = area;
		this.value = value == null ? null : Float.valueOf(value.floatValue());
	}

	@Override
	public String category() {
		return "design";
	}

	@Override
	public void toJSON(StringBuilder sb) {
		if (area != null) {
			sb.append("\"area\":\"").append(JSON.escape(area)).append("\",");
		}
		if (value != null) {
			sb.append("\"value\":").append(value.floatValue()).append(",");
		}
		super.toJSON(sb);
	}
}
