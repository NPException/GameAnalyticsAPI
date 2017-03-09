/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;

import de.npe.gameanalytics.Analytics;
import de.npe.gameanalytics.util.JSON;


/**
 * Event class for collecting stats about the users system
 *
 * @author NPException
 *
 */
public class GAUserEvent extends GAEvent {

	private String gender; // The gender of the user (M/F).

	private Integer birthYear; // The year the user was born.

	private String facebookID; // The Facebook ID of the user, in clear.

	private String iosID; // The IDFA of the user, in clear.

	private String androidID; // The Android ID of the user, in clear.

	private String adtruthID; // The AdTruth ID of the user, in clear.

	private String platform; // The platform that this user plays the game on.

	private String device; // The device that this user plays the game on.

	private String osMajor; // The major version of the OS that this user plays on.

	private String osMinor; // The minor version of the OS that this user plays on.

	private String installPublisher; // The name of the ad publisher.

	private String installSite; // The website or app where the ad for your game was shown.

	private String installCampaign; // The name of your ad campaign this user comes from.

	private String installAdGroup; // The name of the ad group this user comes from.

	private String installAd; // The name of the ad this user comes from.

	private String installKeyword; // A keyword to associate with this user and the campaign ad.

	public GAUserEvent(Analytics an) {
		super(an);
	}

	public GAUserEvent genderMale() {
		gender = "M";
		return this;
	}

	public GAUserEvent genderFemale() {
		gender = "F";
		return this;
	}

	public GAUserEvent birthYear(int birthYear) {
		this.birthYear = Integer.valueOf(birthYear);
		return this;
	}

	public GAUserEvent facebookID(String facebookID) {
		this.facebookID = facebookID;
		return this;
	}

	public GAUserEvent iosID(String iosID) {
		this.iosID = iosID;
		return this;
	}

	public GAUserEvent androidID(String androidID) {
		this.androidID = androidID;
		return this;
	}

	public GAUserEvent adtruthID(String adtruthID) {
		this.adtruthID = adtruthID;
		return this;
	}

	public GAUserEvent platform(String platform) {
		this.platform = platform;
		return this;
	}

	public GAUserEvent device(String device) {
		this.device = device;
		return this;
	}

	public GAUserEvent osMajor(String osMajor) {
		this.osMajor = osMajor;
		return this;
	}

	public GAUserEvent osMinor(String osMinor) {
		this.osMinor = osMinor;
		return this;
	}

	public GAUserEvent installPublisher(String installPublisher) {
		this.installPublisher = installPublisher;
		return this;
	}

	public GAUserEvent installSite(String installSite) {
		this.installSite = installSite;
		return this;
	}

	public GAUserEvent installCampaign(String installCampaign) {
		this.installCampaign = installCampaign;
		return this;
	}

	public GAUserEvent installAdGroup(String installAdGroup) {
		this.installAdGroup = installAdGroup;
		return this;
	}

	public GAUserEvent installAd(String installAd) {
		this.installAd = installAd;
		return this;
	}

	public GAUserEvent installKeyword(String installKeyword) {
		this.installKeyword = installKeyword;
		return this;
	}

	@Override
	public String category() {
		return "user";
	}

	@Override
	public void toJSON(StringBuilder sb) {
		if (gender != null) {
			sb.append("\"gender\":\"").append(JSON.escape(gender)).append("\",");
		}
		if (birthYear != null) {
			sb.append("\"birth_year\":").append(birthYear.intValue()).append(",");
		}
		if (facebookID != null) {
			sb.append("\"facebook_id\":\"").append(JSON.escape(facebookID)).append("\",");
		}
		if (iosID != null) {
			sb.append("\"ios_id\":\"").append(JSON.escape(iosID)).append("\",");
		}
		if (androidID != null) {
			sb.append("\"android_id\":\"").append(JSON.escape(androidID)).append("\",");
		}
		if (adtruthID != null) {
			sb.append("\"adtruth_id\":\"").append(JSON.escape(adtruthID)).append("\",");
		}
		if (platform != null) {
			sb.append("\"platform\":\"").append(JSON.escape(platform)).append("\",");
		}
		if (device != null) {
			sb.append("\"device\":\"").append(JSON.escape(device)).append("\",");
		}
		if (osMajor != null) {
			sb.append("\"os_major\":\"").append(JSON.escape(osMajor)).append("\",");
		}
		if (osMinor != null) {
			sb.append("\"os_minor\":\"").append(JSON.escape(osMinor)).append("\",");
		}
		if (installPublisher != null) {
			sb.append("\"install_publisher\":\"").append(JSON.escape(installPublisher)).append("\",");
		}
		if (installSite != null) {
			sb.append("\"install_site\":\"").append(JSON.escape(installSite)).append("\",");
		}
		if (installCampaign != null) {
			sb.append("\"install_campaign\":\"").append(JSON.escape(installCampaign)).append("\",");
		}
		if (installAdGroup != null) {
			sb.append("\"install_adgroup\":\"").append(JSON.escape(installAdGroup)).append("\",");
		}
		if (installAd != null) {
			sb.append("\"install_ad\":\"").append(JSON.escape(installAd)).append("\",");
		}
		if (installKeyword != null) {
			sb.append("\"install_keyword\":\"").append(JSON.escape(installKeyword)).append("\",");
		}
		super.toJSON(sb);
	}
}
