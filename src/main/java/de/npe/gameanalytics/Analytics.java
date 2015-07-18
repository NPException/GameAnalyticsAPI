/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import de.npe.gameanalytics.events.GADesignEvent;
import de.npe.gameanalytics.events.GAErrorEvent;
import de.npe.gameanalytics.events.GAErrorEvent.Severity;
import de.npe.gameanalytics.events.GAEvent;


/**
 * This API can be used to collect analytics data about your mod, using the
 * service at <a href="http://www.gameanalytics.com/">GameAnalytics.com</a>.<br>
 * <br>
 * Even though this API was initially designed to be used by MinecraftMods, it
 * can be used for every other Java game as well as soon as you rip out the
 * <b>package-info.java</b>.
 *
 * @author NPException
 *
 */
public abstract class Analytics {
	public abstract boolean active();

	public abstract String gameKey();

	public abstract String secretKey();

	public abstract String build();

	public abstract String userPrefix();

	/////////////////////////////////////////////////////
	// Methods you don't need to override necessarily, //
	// but can if you want to do something specific    //
	/////////////////////////////////////////////////////

	private String sessionID;
	private String userID;

	public synchronized String getUserID() {
		if (userID == null) {
			userID = "unknown";
			try {
				Properties config = loadConfig();
				UUID uuid;
				try {
					uuid = UUID.fromString(config.getProperty("user_id"));
				} catch (Exception e) {
					uuid = UUID.randomUUID();
					config.setProperty("user_id", uuid.toString());
					saveConfig(config);
				}

				userID = uuid.toString();
			} catch (Exception e) {
				System.err.println("Error when loading analytics config");
				e.printStackTrace(System.err);
			}
		}
		return userPrefix() + userID;
	}

	public synchronized String getSessionID() {
		if (sessionID == null) {
			sessionID = String.valueOf(System.currentTimeMillis());
		}
		return sessionID;
	}

	protected String getAnaylitcsConfigRootPath() {
		return ".analytics";
	}

	protected Properties loadConfig() throws IOException {
		synchronized (getClass()) {
			Properties config = new Properties();
			File file = getConfigFile();
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			try (FileInputStream fis = new FileInputStream(file)) {
				config.load(fis);
			}
			return config;
		}
	}

	protected void saveConfig(Properties config) throws IOException {
		synchronized (getClass()) {
			File file = getConfigFile();
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			try (FileOutputStream fos = new FileOutputStream(file)) {
				config.store(fos, null);
			}
		}
	}

	private File getConfigFile() {
		return new File(getAnaylitcsConfigRootPath(), getClass().getCanonicalName() + ".properties");
	}

	////////////////////////////
	// Methods to send events //
	////////////////////////////

	public final void event(GAEvent event) {
		if (active()) {
			EventHandler.add(event);
		}
	}

	public final void eventError(Severity severity, String message) {
		if (active()) {
			switch (severity) {
				case critical:
				case error:
					EventHandler.sendEventImmediately(new GAErrorEvent(this, severity, message));
					break;
				case warning:
				case info:
				case debug:
					EventHandler.add(new GAErrorEvent(this, severity, message));
					break;
				default:
					System.err.println("Unhandled severity for analytics: " + severity.name());
					break;
			}
		}
	}

	public final void eventDesign(String eventID) {
		if (active()) {
			EventHandler.add(new GADesignEvent(this, eventID, null, null));
		}
	}

	public final void eventDesign(String eventID, String area) {
		if (active()) {
			EventHandler.add(new GADesignEvent(this, eventID, area, null));
		}
	}

	public final void eventDesign(String eventID, Number value) {
		if (active()) {
			EventHandler.add(new GADesignEvent(this, eventID, null, value));
		}
	}

	public final void eventDesign(String eventID, String area, Number value) {
		if (active()) {
			EventHandler.add(new GADesignEvent(this, eventID, area, value));
		}
	}

	/////////////////////////////////////////////////////////////////
	// internal stuff. you should not need to bother about this :) //
	/////////////////////////////////////////////////////////////////

	public final KeyPair keyPair = new KeyPair();

	public final class KeyPair {
		public final String gameKey = gameKey();
		public final String secretKey = secretKey();

		@Override
		public int hashCode() {
			return gameKey.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof KeyPair))
				return false;
			KeyPair other = (KeyPair) obj;
			return gameKey.equals(other.gameKey) && secretKey.equals(other.secretKey);
		}
	}
}
