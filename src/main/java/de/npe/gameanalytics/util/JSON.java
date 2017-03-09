package de.npe.gameanalytics.util;

import de.npe.gameanalytics.SimpleAnalytics;
import de.npe.gameanalytics.events.GADesignEvent;

/**
 * NPException, 05.06.2016.
 */
public class JSON {
	private JSON() {
		// HELPER CLASS
	}

	public static final String escape(String content) {
		StringBuilder sb = new StringBuilder(content);

		for (int i = 0; i < sb.length(); i++) {
			char insert = 0;
			switch (sb.charAt(i)) {
				case '\b':
					insert = 'b';
					break;
				case '\f':
					insert = 'f';
					break;
				case '\n':
					insert = 'n';
					break;
				case '\r':
					insert = 'r';
					break;
				case '\t':
					insert = 't';
					break;
				case '"':
					insert = '"';
					break;
				case '\\':
					insert = '\\';
					break;
			}

			if (insert != 0) {
				sb.setCharAt(i++, '\\');
				sb.insert(i, insert);
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		new GADesignEvent(new SimpleAnalytics("testbuild", "game-key", "secret"), "fancyEvent:test", "nether", Integer.valueOf(15)).toJSON(sb);
		sb.append('}');
	}

	public interface JSONObject {
		void toJSON(StringBuilder sb);
	}
}
