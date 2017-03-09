/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;


import de.npe.gameanalytics.Analytics;
import de.npe.gameanalytics.Analytics.KeyPair;
import de.npe.gameanalytics.util.JSON;


/**
 * Base event class. Contains values that are mandatory for all event types.
 *
 * @author NPException
 */
public abstract class GAEvent implements JSON.JSONObject {
	public final transient KeyPair keyPair;

	private final String userID;

	private final String sessionID;

	private final String build;

	GAEvent(Analytics an) {
		keyPair = an.keyPair();
		userID = an.getUserID();
		sessionID = an.getSessionID();
		build = an.build();
	}

	public abstract String category();

	@Override
	public void toJSON(StringBuilder sb) {
		sb.append("\"user_id\":\"").append(JSON.escape(userID)).append("\",");
		sb.append("\"session_id\":\"").append(JSON.escape(sessionID)).append("\",");
		sb.append("\"build\":\"").append(JSON.escape(build)).append("\"");
	}

	private transient String toString;

	@Override
	public String toString() {
		if (toString == null) {
			StringBuilder sb = new StringBuilder();
			sb.append('{');
			toJSON(sb);
			sb.append('}');
			sb.append(" + ").append(keyPair);
			toString = sb.toString();
		}
		return toString;
	}
}
