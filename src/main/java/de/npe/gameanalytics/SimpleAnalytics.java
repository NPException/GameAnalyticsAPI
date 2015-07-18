/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics;

import net.minecraft.client.Minecraft;

/**
 * @author NPException
 *
 */
public class SimpleAnalytics extends Analytics {

	private boolean isClient;

	private final String gameKey;
	private final String secretKey;
	private final String build;

	public SimpleAnalytics(String build, String gameKey, String secretKey) {
		this.gameKey = gameKey;
		this.secretKey = secretKey;
		this.build = build;
	}

	public boolean isClient() {
		return isClient;
	}

	public void markClientSide() {
		isClient = true;
	}

	@Override
	public boolean isActive() {
		return Minecraft.getMinecraft().isSnooperEnabled();
	}

	@Override
	public String gameKey() {
		return gameKey;
	}

	@Override
	public String secretKey() {
		return secretKey;
	}

	@Override
	public String build() {
		return build;
	}

	@Override
	public String userPrefix() {
		return isClient ? "user" : "server";
	}
}
