package code.Young_Explicit.nimbus.objects;

import java.util.UUID;

public class Ban {

	UUID bannedUUID;
	String banner;
	String reason;

	public Ban(UUID bannedUUID, String banner, String reason) {
		this.bannedUUID = bannedUUID;
		this.banner = banner;
		this.reason = reason;
	}

	public UUID getBannedUUID() {
		return bannedUUID;
	}

	public String getBanner() {
		return banner;
	}

	public void setBannedUUID(UUID bannedUUID) {
		this.bannedUUID = bannedUUID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
