package code.Young_Explicit.nimbus.objects;


public class BanAddress {

	String banner;
	String address;
	String reason;

	public BanAddress(String address, String banner, String reason) {
		this.address = address;
		this.banner = banner;
		this.reason = reason;
	}

	public String getBannedAddress() {
		return address;
	}

	public String getBanner() {
		return banner;
	}

	public void setBannedAddress(String address) {
		this.address = address;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
