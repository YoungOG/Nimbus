package code.Young_Explicit.nimbus.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;

import code.Young_Explicit.nimbus.Nimbus;

public class ServerManager {

	private static int time = 300;
	
	private boolean isChatMuted = false;

	public Boolean isChatMuted() {
		return isChatMuted;
	}

	public void setChatMuted(Boolean muted) {
		isChatMuted = muted;
	}
	
	public static void restart() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Nimbus.getInstance(), new Runnable() {
			public void run() {
				if (time == 300) {
					Bukkit.broadcastMessage("§7[§c!§7] §4The server is restarting in §c5 §4minutes.");
				}
				
				if (time > 0) {
					time--;
				}
				
				if (time == 240) {
					Bukkit.broadcastMessage("§7[§c!§7] §4The server is restarting in §c4 §4minutes.");
				}

				if (time == 180) {
					Bukkit.broadcastMessage("§7[§c!§7] §4The server is restarting in §c3 §4minutes.");
				}
				
				if (time == 120) {
					Bukkit.broadcastMessage("§7[§c!§7] §4The server is restarting in §c2 §4minutes.");
				}
				
				if (time == 60) {
					Bukkit.broadcastMessage("§7[§c!§7] §4The server is restarting in §c1 §4minute.");
				}
				
				if (time == 30) {
					Bukkit.broadcastMessage("§7[§c!§7] §4The server is restarting in §c30 §4seconds.");
				}
				
				if (time == 15) {
					Bukkit.broadcastMessage("§7[§c!§7] §4The server is restarting in §c15 §4seconds.");
				}
				
				if (time == 0) {
					for (World w : Bukkit.getWorlds()) {
						w.save();
					}
					
					Bukkit.savePlayers();
					
					Bukkit.shutdown();
				}
			}
		}, 0L, 20);
	}
}
