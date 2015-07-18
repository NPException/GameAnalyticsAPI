/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import de.npe.gameanalytics.Analytics.KeyPair;
import de.npe.gameanalytics.events.GAEvent;


/**
 * @author NPException
 *
 */
final class EventHandler {
	private static boolean init = true;

	/**
	 * Map containing all not yet sent events.<br>
	 * <br>
	 * Map: KeyPair -> Map: category -> event list
	 */
	private static final Map<KeyPair, Map<String, List<GAEvent>>> events = new HashMap<>(8);

	private static synchronized Map<String, List<GAEvent>> getEventsForGame(KeyPair keyPair) {
		Map<String, List<GAEvent>> gameEvents = events.get(keyPair);
		if (gameEvents == null) {
			gameEvents = new HashMap<>();
			events.put(keyPair, gameEvents);
		}
		return gameEvents;
	}

	static void add(GAEvent event) {
		Map<String, List<GAEvent>> gameEvents = getEventsForGame(event.keyPair);
		synchronized (gameEvents) {
			List<GAEvent> categoryEvents = gameEvents.get(event.category());
			if (categoryEvents == null) {
				categoryEvents = new ArrayList<>(2);
				gameEvents.put(event.category(), categoryEvents);
			}
			categoryEvents.add(event);
		}
		init();
	}

	static void sendEventImmediately(GAEvent event) {
		RESTHelper.sendSingleEvent(event);
	}

	private static void init() {
		if (!init)
			return;

		synchronized (EventHandler.class) {
			if (!init)
				return;
			init = false;
		}

		final int sleepTime = APIProps.PUSH_INTERVAL_SECONDS * 1000;

		Thread sendThread = new Thread("GA-DataSendThread") {
			@Override
			public void run() {
				while (true) {
					try {
						sleep(sleepTime);
						sendData();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		sendThread.setDaemon(true);
		sendThread.start();
	}

	private static synchronized void sendData() {
		Set<KeyPair> keyPairs = events.keySet();
		for (KeyPair keyPair : keyPairs) {
			Map<String, List<GAEvent>> gameEvents = getEventsForGame(keyPair);
			synchronized (gameEvents) {
				if (gameEvents.isEmpty()) {
					continue;
				}

				for (Map.Entry<String, List<GAEvent>> entry : gameEvents.entrySet()) {
					List<GAEvent> categoryEvents = entry.getValue();
					if (categoryEvents.isEmpty()) {
						continue;
					}

					RESTHelper.sendData(keyPair, entry.getKey(), categoryEvents);
					categoryEvents.clear();
				}
			}
		}
	}

	private static class RESTHelper {
		private RESTHelper() {}

		private static final Gson gson = new Gson();

		private static final String contentType = "application/json";
		private static final String accept = "application/json";

		static void sendSingleEvent(GAEvent event) {
			try {
				sendData(event.keyPair, event.category(), Arrays.asList(event));
			} catch (Exception e) {
				System.err.println("Tried to send single event, but failed.");
			}
		}

		static void sendData(KeyPair keyPair, String category, List<GAEvent> events) {
			HttpPost request = createPostRequest(keyPair, category, events);

			try (CloseableHttpClient httpClient = HttpClients.createDefault();
					CloseableHttpResponse response = httpClient.execute(request)) {
				String responseContent = readResponseContent(response);
				System.out.println("Sent event of category \"" + category + "\", response: " + responseContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private static HttpPost createPostRequest(KeyPair keyPair, String category, List<GAEvent> events) {
			String uri = APIProps.GA_API_URL + APIProps.GA_API_VERSION + "/" + keyPair.gameKey + "/" + category;
			// Create POST request
			HttpPost request = new HttpPost(uri);

			String content = gson.toJson(events);
			byte[] authData = (content + keyPair.secretKey).getBytes();
			String hashedAuthData = DigestUtils.md5Hex(authData);
			request.setHeader("Authorization", hashedAuthData);
			request.setHeader("Accept", accept);

			try {
				// Prepare the request content
				StringEntity entity = new StringEntity(content);
				entity.setContentType(contentType);
				request.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				// should not happen, I think
			}

			return request;
		}

		private static String readResponseContent(HttpResponse response) throws ClientProtocolException, IOException {
			// Read the whole body
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			// Read the info
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			return sb.toString();
		}
	}
}
