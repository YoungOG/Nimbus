package code.Young_Explicit.nimbus.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import redis.clients.jedis.Jedis;
import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.objects.Ban;
import code.Young_Explicit.nimbus.objects.BanAddress;
import code.Young_Explicit.nimbus.objects.Profile;
import code.Young_Explicit.nimbus.objects.TempBan;
import code.Young_Explicit.nimbus.objects.TempBanAddress;
import code.Young_Explicit.nimbus.utils.DateUtil;
import code.Young_Explicit.nimbus.utils.PubSubWriter;

import com.google.gson.Gson;

public class BanManager {

	private DatabaseManager dm = Nimbus.getInstance().getDatabaseManager();

	public void banName(String name, CommandSender banner, String reason) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);
		
		Jedis j = dm.getPool().getResource();
		
		if (!j.exists("{ban}" + op.getUniqueId())) {
			Ban ban = new Ban(op.getUniqueId(), banner.getName(), reason);
			Profile prof = new Gson().fromJson(j.get("{network_profile}" + op.getUniqueId().toString()), Profile.class);
	
			if (prof != null) {
				prof.setPermBanned(true);
	
				j.set("{network_profile}" + op.getUniqueId().toString(), new Gson().toJson(prof));
				j.set("{ban}" + op.getUniqueId().toString(), new Gson().toJson(ban));
	
				PubSubWriter writer = new PubSubWriter(j, "update");
				writer.publish("updateProfile|" + op.getUniqueId().toString());
	
				if (banner instanceof Player) {
					Bukkit.broadcastMessage("§e" + name + " has been banned by " + ((Player)banner).getDisplayName() + "§e.");
				} else {
					Bukkit.broadcastMessage("§e" + name + " has been banned by §4" + banner.getName() + "§e.");
				}
			} else {
				banner.sendMessage("§cA profile could not be found for " + name);
				return;
			}
		} else {
			banner.sendMessage("§cError: That player is already banned.");
			return;
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}

	public void banPlayer(Player banned, CommandSender banner, String reason) {
		UUID bannedUUID = banned.getUniqueId();

		Jedis j = dm.getPool().getResource();
		
		if (!j.exists("{ban}" + bannedUUID.toString())) {
			Ban ban = new Ban(bannedUUID, banner.getName(), reason);

			Profile prof = new Gson().fromJson(j.get("{network_profile}" + bannedUUID.toString()), Profile.class);

			prof.setPermBanned(true);

			j.set("{network_profile}" + bannedUUID.toString(), new Gson().toJson(prof));
			j.set("{ban}" + bannedUUID.toString(), new Gson().toJson(ban));

			PubSubWriter writer = new PubSubWriter(j, "update");
			writer.publish("updateProfile|" + bannedUUID.toString());

			banned.kickPlayer("§cYour account has been suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");

		    if (banner instanceof Player) {
		    	Bukkit.broadcastMessage("§e" + banned.getName() + " has been banned by " + ((Player) banner).getDisplayName() + "§e.");
		    } else {
		    	Bukkit.broadcastMessage("§e" + banned.getName() + " has been banned by §4" + banner.getName() + "§e.");
		    }	
		} else {
			banner.sendMessage("§cError: That player is already banned.");
			return;
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}

	public void banAddress(String address, CommandSender banner, String reason) {
		Jedis j = dm.getPool().getResource();
		
		if (!j.exists("{ipban}" + address)) {
			BanAddress aban = new BanAddress(address, banner.getName(), reason);
			
			j.set("{ipban}" + address, new Gson().toJson(aban));
			
			if (banner instanceof Player) {
				Bukkit.broadcast("§eIP Address " + address + " has been banned by " + ((Player) banner).getDisplayName() + "§e." , "nimbus.banip.view");
			} else {
				Bukkit.broadcast("§eIP Address " + address + " has been banned by §4" + banner.getName() + "§e.", "nimbus.banip.view");
			}

			for (Player all : Bukkit.getOnlinePlayers()) {
				if (all.getAddress().getAddress().getHostAddress().replace("/", "").equals(address)) {
					all.kickPlayer("§cThis IP Address has been suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");
				}
			}
		} else {
			banner.sendMessage("§cError: That IP Address is already banned.");
			return;
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}

	public void tempbanAddress(String address, CommandSender banner, String time, String reason) {
		Jedis j = dm.getPool().getResource();

		if (!j.exists("{tempipban}" + address)) {
			try {
				long bannedUntil = DateUtil.parseDateDiff(time, true);
				TempBanAddress aban = new TempBanAddress(address, banner.getName(), reason, bannedUntil);
				
				j.set("{tempipban}" + address, new Gson().toJson(aban));
				
				if (banner instanceof Player) {
					Bukkit.broadcast("§eIP Address " + address + " has been temporarily banned by " + ((Player) banner).getDisplayName() + "§e." , "nimbus.banip.view");
				} else {
					Bukkit.broadcast("§eIP Address " + address + " has been temporarily banned by §4" + banner.getName() + "§e.", "nimbus.banip.view");
				}

				for (Player all : Bukkit.getOnlinePlayers()) {
					if (all.getAddress().getAddress().getHostAddress().replace("/", "").equals(address)) {
						all.kickPlayer("§cThis IP Address has been temporarily suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");
					}
				}

			} catch (Exception ex) {
				banner.sendMessage("§cError: The time you entered is not valid.");
			}	
		} else {
			banner.sendMessage("§cError: That IP Address is already banned.");
			return;
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}

	public void tempBan(Player banned, CommandSender banner, String time, String reason) {
		Jedis j = dm.getPool().getResource();
		
		if (!j.exists("{tempban}" + banned.getUniqueId())) {
			try {
				long bannedUntil = DateUtil.parseDateDiff(time, true);
				
				TempBan tban = new TempBan(banned.getUniqueId(), banner.getName(), reason, bannedUntil);
				Profile prof = new Gson().fromJson(j.get("{network_profile}" + banned.getUniqueId().toString()), Profile.class);
	
				j.set("{tempban}" + banned.getUniqueId().toString(), new Gson().toJson(tban));
				prof.setTempBanned(true);
				prof.setTempBans(prof.getTempBans() + 1);
				j.set("{network_profile}" + banned.getUniqueId().toString(), new Gson().toJson(prof));
	
				PubSubWriter writer = new PubSubWriter(j, "update");
				writer.publish("updateProfile|" + banned.getUniqueId().toString());
				
				 if (banner instanceof Player) {
					 Bukkit.broadcastMessage("§e" + banned.getName() + " has been temporarily banned by " + ((Player) banner).getDisplayName() + "§e.");
				 } else {
					 Bukkit.broadcastMessage("§e" + banned.getName() + " has been temporarily banned by §4" + banner.getName() + "§e.");
				 }	
				
				banned.kickPlayer("§cYour account has been temporarily suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");
			} catch (Exception ex) {
				banner.sendMessage("§cError: The time you entered is not valid.");
			}
		} else {
			banner.sendMessage("§cError: That player is already banned.");
			return;
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}

	public void tempBanName(String name, CommandSender banner, String time, String reason) {
		Jedis j = dm.getPool().getResource();
		
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);
		if (!j.exists("{tempban}" + op.getUniqueId())) {
			try {
				long bannedUntil = DateUtil.parseDateDiff(time, true);
	
				TempBan tban = new TempBan(op.getUniqueId(), banner.getName(), reason, bannedUntil);
				Profile prof = new Gson().fromJson(j.get("{network_profile}" + op.getUniqueId().toString()), Profile.class);
	
				j.set("{tempban}" + op.getUniqueId().toString(), new Gson().toJson(tban));
				prof.setTempBanned(true);
				prof.setTempBans(prof.getTempBans() + 1);
				j.set("{network_profile}" + op.getUniqueId().toString(), new Gson().toJson(prof));
	
				PubSubWriter writer = new PubSubWriter(j, "update");
				writer.publish("updateProfile|" + op.getUniqueId().toString());
			} catch (Exception ex) {
				banner.sendMessage("§cError: The time you entered is not valid.");
			}
		} else {
			banner.sendMessage("§cError: That player is already banned.");
			return;
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}

	public void unban(CommandSender unbanner, String name) {
		Jedis j = dm.getPool().getResource();
		
		Set<String> keys = j.keys("{network_profile}*");

		Iterator<String> it = keys.iterator();
		Profile tProf = null;
		while (it.hasNext()) {
			String s = it.next();
			Profile prof = new Gson().fromJson(j.get(s), Profile.class);
			if (prof.getPlayerName().toLowerCase().equals(name.toLowerCase())) {
		        tProf = prof;
			}
		}
		
		if (tProf != null) {
			if (tProf.isPermBanned() || tProf.isTempBanned() || j.exists("{ban}" + tProf.getUniqueId().toString()) || j.exists("{tempban}" + tProf.getUniqueId().toString())) {
				tProf.setPermBanned(false);
				tProf.setTempBanned(false);

				if (j.exists("{ban}" + tProf.getUniqueId())) {
					j.del("{ban}" + tProf.getUniqueId().toString());
				}
					
				if (j.exists("{tempban}" + tProf.getUniqueId().toString())) {
					j.del("{tempban}" + tProf.getUniqueId().toString());
				}
				
				unbanner.sendMessage("§eYou have unbanned: §b" + name);

				j.set("{network_profile}" + tProf.getUniqueId().toString(), new Gson().toJson(tProf));

				PubSubWriter writer = new PubSubWriter(j, "update");
				writer.publish("updateProfile|" + tProf.getUniqueId().toString());
			} else {
				unbanner.sendMessage("§cError: That player is not currently banned.");
				return;
			}
		} else {
			unbanner.sendMessage("§cError: That player does not exist.");
			return;
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}
	
	public void unbanAddress(CommandSender unbanner, String address) {
		Jedis j = dm.getPool().getResource();
		
		if (j.exists("{ipban}" + address)) {
			j.del("{ipban}" + address);
			
			unbanner.sendMessage("§eYou have unbanned: §b" + address);
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}
	
	public void checkBan(CommandSender p, String name) {
		Jedis j = dm.getPool().getResource();
		
		Set<String> keys =j.keys("{network_profile}*");

		Iterator<String> it = keys.iterator();
		Profile cbProf = null;
		while (it.hasNext()) {
			String s = it.next();
			 Profile prof = new Gson().fromJson(j.get(s), Profile.class);
			if (prof.getPlayerName().toLowerCase().equals(name.toLowerCase())) {
		         cbProf = prof; 
		         break;
			}  else {
				continue;
			}
		}
		
		if (cbProf != null) {
			p.sendMessage("§c** §7" + cbProf.getPlayerName() + " §c**");
			p.sendMessage("§7Account Banned: " + (cbProf.isPermBanned() ? "§ctrue - "  + (cbProf.isPermBanned() && !cbProf.isTempBanned() ? "§cPermanent" : "§cTemporary") : "§afalse"));
			if (cbProf.isTempBanned()) {
				TempBan tb = new Gson().fromJson(j.get("{tempban}" + cbProf.getUniqueId().toString()), TempBan.class);
				p.sendMessage("§7Ban Time remaining: §c" + DateUtil.formatDateDiff(tb.getBannedUntil()));
			}
			
			if (cbProf.isPermBanned()) {
				Ban b = new Gson().fromJson(j.get("{ban}" + cbProf.getUniqueId().toString()), Ban.class);
				p.sendMessage("§7Account Ban Reason: §c" + b.getReason());
				p.sendMessage("§7Banned by: §c" + b.getBanner());
			} else if (cbProf.isTempBanned()) {
				TempBan tb = new Gson().fromJson(j.get("{tempban}" + cbProf.getUniqueId().toString()), TempBan.class);
				p.sendMessage("§7Account Ban Reason: §c" + tb.getReason());
				p.sendMessage("§7Banned by: §c" + tb.getBanner());
			}
		} else {
			p.sendMessage("§cProfile for '" + name + "' not found.");
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}
	
	public void checkStaff(CommandSender p, String name) {
		Jedis j = dm.getPool().getResource();
		
		Set<String> bans = j.keys("{ban}*");
		Set<String> tempbans = j.keys("{tempban}*");
		Set<String> ipbans = j.keys("{ipban}*");
		Set<String> tempipbans = j.keys("{tempipban}*");

		p.sendMessage("§eLooking for all bans issued by: §b" + name + "§e...");
		
		int bancount = 0;
		ArrayList<Ban> banList = new ArrayList<Ban>();
		ArrayList<TempBan> tempbanList = new ArrayList<TempBan>();
		ArrayList<BanAddress> ipbanList = new ArrayList<BanAddress>();
		ArrayList<TempBanAddress> tempipbanList = new ArrayList<TempBanAddress>();
		
		Iterator<String> it1 = bans.iterator();
		while (it1.hasNext()) {
			String s = it1.next();
			 Ban b = new Gson().fromJson(j.get(s), Ban.class);
			 if (b.getBanner().equalsIgnoreCase(name)) {
				 bancount++;
				 banList.add(b);
			}
		}
		
		Iterator<String> it2 = tempbans.iterator();
		while (it2.hasNext()) {
			String s = it2.next();
			 TempBan b = new Gson().fromJson(j.get(s), TempBan.class);
			 if (b.getBanner().equalsIgnoreCase(name)) {
				 bancount++;
				 tempbanList.add(b);
			}
		}
		
		Iterator<String> it3 = ipbans.iterator();
		while (it3.hasNext()) {
			String s = it3.next();
			 BanAddress b = new Gson().fromJson(j.get(s), BanAddress.class);
			 if (b.getBanner().equalsIgnoreCase(name)) {
				 bancount++;
				 ipbanList.add(b);
			}
		}
		
		Iterator<String> it4 = tempipbans.iterator();
		while (it4.hasNext()) {
			String s = it4.next();
			 TempBanAddress b = new Gson().fromJson(j.get(s), TempBanAddress.class);
			 if (b.getBanner().equalsIgnoreCase(name)) {
				 bancount++;
				 tempipbanList.add(b);
			}
		}
		
		if (bancount > 0) {
			p.sendMessage("§eFound a total of §b" + bancount + " §ebans issued by this player.");
			p.sendMessage("§eNote: Hover over the ban to view more information.");
			
			for (Ban b : banList) {
				FancyMessage message = new FancyMessage("§eName: §b" + getName(b.getBannedUUID()) + " §eType: §bPermanent §eReason: §b" + b.getReason());
				message.tooltip(
						"§eName: §b" + getName(b.getBannedUUID()) +
						"\n§eBanner: §b" + b.getBanner() +
						"\n§eReason: §b" + b.getReason() +
						"\n§eIssued: §bComing Soon");
				message.send(p);
			}
			
			for (TempBan b : tempbanList) {
				FancyMessage message = new FancyMessage("§eName: §b" + getName(b.getBannedUUID()) + " §eType: §bTemporary §eReason: §b" + b.getReason() + " §eLenght: §b" + DateUtil.formatDateDiff(b.getBannedUntil()));
				message.tooltip(
						"§eName: §b" + getName(b.getBannedUUID()) +
						"\n§eBanner: §b" + b.getBanner() +
						"\n§eReason: §b" + b.getReason() +
						"\n§eBan Lenght: §b" + DateUtil.formatDateDiff(b.getBannedUntil()) +
						"\n§eIssued: §bComing Soon");
				message.send(p);
			}
			
			for (BanAddress b : ipbanList) {
				FancyMessage message = new FancyMessage("§eAddress: §b" + b.getBannedAddress() + " §eType: §bPermanent §eReason: §b" + b.getReason());
				message.tooltip(
						"§eName: §b" + b.getBannedAddress() +
						"\n§eBanner: §b" + b.getBanner() +
						"\n§eReason: §b" + b.getReason() +
						"\n§eIssued: §bComing Soon");
				message.send(p);
			}
			
			for (TempBanAddress b : tempipbanList) {
				FancyMessage message = new FancyMessage("§eAddress: §b" + b.getAddress() + " §eType: §bTemporary §eReason: §b" + b.getReason() + " §eLenght: §b" + DateUtil.formatDateDiff(b.getBannedUntil()));
				message.tooltip(
						"§eName: §b" + b.getAddress() +
						"\n§eBanner: §b" + b.getBanner() +
						"\n§eReason: §b" + b.getReason() +
						"\n§eBan Lenght: §b" + DateUtil.formatDateDiff(b.getBannedUntil()) +
						"\n§eIssued: §bComing Soon");
				message.send(p);
			}
		} else {
			p.sendMessage("§cError: This player has not issued any bans.");
		}
		
		dm.getPool().returnResource(j);
		dm.getPool().destroy();
	}

	public String getName(UUID uuid) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		if (player == null) {
			try {
				throw new Exception("OfflinePlayer: \"" + uuid.toString() + "\" not found!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return player.getName();
	}
}