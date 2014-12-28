package code.Young_Explicit.nimbus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage("§7" + e.getPlayer().getName() + " has joined the game.");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			e.setDeathMessage(e.getEntity().getDisplayName() + " §7 has been slain by §r" + e.getEntity().getKiller().getDisplayName());
		}
	}
}
