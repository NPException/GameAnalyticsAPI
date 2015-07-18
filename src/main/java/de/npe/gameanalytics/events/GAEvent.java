/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;

import com.google.gson.annotations.SerializedName;

import de.npe.gameanalytics.Analytics;
import de.npe.gameanalytics.Analytics.KeyPair;


/**
 * Base event class. Contains values that are mandatory for all event types.
 *
 * @author NPException
 *
 */
public abstract class GAEvent {
	public final transient KeyPair keyPair;

	@SerializedName("user_id")
	private final String userID;

	@SerializedName("session_id")
	private final String sessionID;

	@SerializedName("build")
	private final String build;

	GAEvent(Analytics an) {
		keyPair = an.keyPair();
		userID = an.getUserID();
		sessionID = an.getSessionID();
		build = an.build();
	}

	public abstract String category();
}
