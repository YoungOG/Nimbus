package code.Young_Explicit.nimbus.objects;

public class TempBanAddress {

	String banner;
	String address;
	String reason;
	long bannedUntil;
	
	public TempBanAddress(String address, String banner, String reason, long bannedUntil) {
		this.address = address;
		this.banner = banner;
		this.reason = reason;
		this.bannedUntil = bannedUntil;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setBannedUntil(long bannedUntil) {
		this.bannedUntil = bannedUntil;
	}

	public Long getBannedUntil() {
		return bannedUntil;
	}
}
