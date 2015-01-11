package code.Young_Explicit.nimbus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import redis.clients.jedis.Jedis;
import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.DatabaseManager;
import code.Young_Explicit.nimbus.objects.Profile;
import code.Young_Explicit.nimbus.objects.TempBan;
import code.Young_Explicit.nimbus.objects.TempBanAddress;
import code.Young_Explicit.nimbus.utils.DateUtil;
import code.Young_Explicit.nimbus.utils.PubSubWriter;

import com.google.gson.Gson;

public class PlayerListeners implements Listener {

	DatabaseManager dm = Nimbus.getInstance().getDatabaseManager();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage("§7" + e.getPlayer().getName() + " has joined the game.");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
	
//	@EventHandler
//	public void onDeath(PlayerDeathEvent e) {
//		if (e.getEntity().getKiller() instanceof Player) {
//			e.setDeathMessage(e.getEntity().getDisplayName() + " §7 has been slain by §r" + e.getEntity().getKiller().getDisplayName());
//		}
//	}
	
	@EventHandler
	public void onPostLogin(final PlayerPreLoginEvent e) {
		String uuid = e.getUniqueId().toString();
		String ip = e.getAddress().getHostAddress().replace("/", "");
		
		Jedis j = dm.getPool().getResource();
		
		if (j.exists("{ipban}" + ip)) {
			e.disallow(Result.KICK_BANNED, "§cThis IP Address has been suspended from the BreakMC Network\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");
			return;
		}
		else if (j.exists("{tempipban}" + ip)) {
			TempBanAddress tab = new Gson().fromJson(j.get("{tempipban}" + ip), TempBanAddress.class);
			
			if (System.currentTimeMillis() >= tab.getBannedUntil()) {
				j.del("{tempipban}" + ip);
			}
			
			String blah = DateUtil.formatDateDiff(tab.getBannedUntil());
			e.disallow(Result.KICK_BANNED, "§cThis IP Address has been temporarily suspended from the BreakMC Network\n \n§cTime remaining: §f" + blah);
			return;
		}
		
		Profile prof = new Gson().fromJson(j.get("{network_profile}" + uuid), Profile.class);
		
		if (j.exists("{ban}" + uuid)) {
			e.disallow(Result.KICK_BANNED, "§cYour account has been suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");
			return;
		}
		
		else if (j.exists("{tempban}" + uuid)) {
			TempBan ban = new Gson().fromJson(j.get("{tempban}" + uuid), TempBan.class);
			
			if (System.currentTimeMillis() >= ban.getBannedUntil()) {
				j.del("{tempban}" + uuid);
				prof.setTempBanned(false);
				
				PubSubWriter writer = new PubSubWriter(j, "update");
				writer.publish("updateProfile|" + uuid);
				return;
			}
			
			String blah = DateUtil.formatDateDiff(ban.getBannedUntil());
			e.disallow(Result.KICK_BANNED, "§cYour account has been temporarily suspended from the BreakMC Network.\n \n§cTime Remaining: §f" + blah);
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}
}
