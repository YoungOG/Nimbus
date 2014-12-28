package code.Young_Explicit.nimbus.objects;

import java.util.List;
import java.util.UUID;

public class Profile {

	UUID id;
	String playerName, address, currentServer, lastServer;
	List<String> alts, ips;
	boolean isOnline, isRegistered, isMuted, isPermBanned, isTempBanned;
	int pin, votes, logins, mutes, kicks, tempbans;
	long time;

	public Profile(UUID id, String playerName, String address, List<String> alts, List<String> ips, String currentServer, String lastServer, Boolean isOnline, Boolean isRegistered, Boolean isMuted, Boolean isTempBanned, Boolean isPermBanned, Integer pin, Integer votes, Integer logins, Integer mutes, Integer kicks, Integer tempbans, Long time) {
		this.id = id;
		this.playerName = playerName;
		this.address = address;
		this.alts = alts;
		this.ips = ips;
		this.currentServer = currentServer;
		this.lastServer = lastServer;
		this.isOnline = isOnline;
		this.isRegistered = isRegistered;
		this.isTempBanned = isTempBanned;
		this.isPermBanned = isPermBanned;
		this.pin = pin;
		this.votes = votes;
		this.logins = logins;
		this.mutes = mutes;
		this.kicks = kicks;
		this.tempbans = tempbans;
		this.time = time;
	}

	public UUID getUniqueId() {
		return id;
	}

	public void setUniqueId(UUID id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getAlts() {
		return alts;
	}

	public List<String> getIps() {
		return ips;
	}

	public String getCurrentServer() {
		return currentServer;
	}

	public void setCurrentServer(String currentServer) {
		this.currentServer = currentServer;
	}

	public String getLastServer() {
		return lastServer;
	}

	public void setLastServer(String lastServer) {
		this.lastServer = lastServer;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public boolean isMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	public boolean isTempBanned() {
		return isTempBanned;
	}

	public void setTempBanned(boolean isTempBanned) {
		this.isTempBanned = isTempBanned;
	}

	public boolean isPermBanned() {
		return isPermBanned;
	}

	public void setPermBanned(boolean isPermBanned) {
		this.isPermBanned = isPermBanned;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getLogins() {
		return logins;
	}

	public void setLogins(int logins) {
		this.logins = logins;
	}

	public int getKicks() {
		return kicks;
	}

	public void setKicks(int kicks) {
		this.kicks = kicks;
	}

	public int getMutes() {
		return mutes;
	}

	public void setMutes(int mutes) {
		this.mutes = mutes;
	}

	public int getTempBans() {
		return tempbans;
	}

	public void setTempBans(int tempbans) {
		this.tempbans = tempbans;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setAlts(List<String> alts) {
		this.alts = alts;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}
}
