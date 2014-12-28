package code.Young_Explicit.nimbus.managers;

public class ServerManager {

	private boolean isChatMuted = false;

	public Boolean isChatMuted() {
		return isChatMuted;
	}

	public void setChatMuted(Boolean muted) {
		isChatMuted = muted;
	}
}
