package code.Young_Explicit.nimbus.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import code.Young_Explicit.nimbus.Nimbus;

@Deprecated
public class BungeeCordListener implements PluginMessageListener {

	// Player doesn't matter because we are receiving it from our own channel
	@Override
	public void onPluginMessageReceived(String channel, Player arg1, byte[] message) {
		if (channel.equals("Pure")) {
			String[] mes = message.toString().split("|");
			if (mes[1].equals("kick")) { // We kick the player
				Player pl = Bukkit.getPlayer(UUID.fromString(mes[0]));
				if (pl != null) {
					pl.sendPluginMessage(Nimbus.getInstance(), "Pure", (pl.getUniqueId() + "|banned").getBytes());
					pl.kickPlayer("§cYour account has been suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal."); // We
																																									// will
																																									// be
																																									// listening
																																									// for
																																									// this
																																									// on
																																									// the
																																									// BungeeCord
																																									// Proxy
																																									// ourselves.
				}
			}
		}
	}
}