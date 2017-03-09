/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;

import de.npe.gameanalytics.Analytics;
import de.npe.gameanalytics.util.JSON;


/**
 * @author NPException
 */
public class GAErrorEvent extends GAEvent {

	public enum Severity {
		critical,
		error,
		warning,
		info,
		debug
	}

	private final String message;

	private final String severity;

	public GAErrorEvent(Analytics an, Severity severity, String message) {
		super(an);
		this.severity = severity.name();
		this.message = message;
	}

	@Override
	public String category() {
		return "error";
	}

	@Override
	public void toJSON(StringBuilder sb) {
		sb.append("\"message\":\"").append(JSON.escape(message)).append("\",");
		sb.append("\"severity\":\"").append(JSON.escape(severity)).append("\",");
		super.toJSON(sb);
	}
}
