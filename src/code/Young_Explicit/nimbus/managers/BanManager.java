package code.Young_Explicit.nimbus.managers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

	public void mutePlayer(Player muted, CommandSender muter) {
		muted.sendMessage("§cYou have been muted for: ");
	}

	public void kickPlayer(Player kicked, CommandSender kicker, String reason) {
		kicked.kickPlayer("§cYou have been kicked from the server:§\n \n§c" + reason);
		if (kicker instanceof Player) {
			Bukkit.broadcastMessage("§e" + kicked.getName() + " has been kicked by " + ((Player) kicker).getDisplayName() + "§e.");
		} else {
			Bukkit.broadcastMessage("§e" + kicked.getName() + " has been kicked by §4" + kicker.getName() + "§e.");
		}
	}

	public void banPlayer(Player banned, CommandSender banner, String reason) {
		UUID bannedUUID = banned.getUniqueId();

		Ban ban = new Ban(bannedUUID, banner.getName(), reason);
		
		Profile prof = new Gson().fromJson(dm.getJedis().get("{network_profile}" + bannedUUID.toString()), Profile.class);

		prof.setPermBanned(true);

		dm.getJedis().set("{network_profile}" + bannedUUID.toString(), new Gson().toJson(prof));
		dm.getJedis().set("{ban}" + bannedUUID.toString(), new Gson().toJson(ban));
		
		PubSubWriter writer = new PubSubWriter(dm.getJedis(), "update");
		writer.publish("updateProfile|" + bannedUUID.toString());

		banned.kickPlayer("§cYour account has been suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");

	    if (banner instanceof Player) {
	    	Bukkit.broadcastMessage("§e" + banned.getName() + " has been banned by " + ((Player)banner).getDisplayName() + "§e.");
	    } else {
	    	Bukkit.broadcastMessage("§e" + banned.getName() + " has been banned by §4" + banner.getName() + "§e.");
	    }
	}

	public void banAddress(String address, CommandSender banner, String reason) {
		BanAddress aban = new BanAddress(address, banner.getName(), reason);

		Set<String> keys = dm.getJedis().keys("{network_profile}*");

		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String s = it.next();
			Profile prof = new Gson().fromJson(dm.getJedis().get(s), Profile.class);
			for (String addresses : prof.getIps()) {
				if (addresses.equals(address)) {
					prof.setPermBanned(true);

					dm.getJedis().set("{network_profile}" + prof.getUniqueId(), new Gson().toJson(prof));
					dm.getJedis().set("{ban}" + prof.getUniqueId().toString(), new Gson().toJson(aban));
					dm.getJedis().publish("update", "updateProfile|" + prof.getUniqueId().toString());
				}
			}
		}
		
		if (banner instanceof Player) {
			Bukkit.broadcast("§eIP ddress" + address + " has been banned by " + ((Player) banner).getDisplayName() + "§e." , "nimbus.ban.viewip");
		} else {
			Bukkit.broadcast("§eIP ddress" + address + " has been banned by §4" + banner.getName() + "§e.", "nimbus.ban.viewip");
		}

		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getAddress().getAddress().getHostAddress().replace("/", "").equals(address)) {
				all.kickPlayer("§cThis IP Address has been suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");
			}
		}
	}

	public void tempbanAddress(String address, CommandSender banner, String time, String reason) {
		try {
			long bannedUntil = DateUtil.parseDateDiff(time, true);
			TempBanAddress aban = new TempBanAddress(address, banner.getName(), reason, bannedUntil);

			Set<String> keys = dm.getJedis().keys("{network_profile}*");

			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String s = it.next();
				Profile prof = new Gson().fromJson(dm.getJedis().get(s), Profile.class);
				for (String addresses : prof.getIps()) {
					if (addresses.equals(address)) {
						prof.setPermBanned(true);

						dm.getJedis().set("{network_profile}" + prof.getUniqueId(), new Gson().toJson(prof));
						dm.getJedis().set("{tempban}" + prof.getUniqueId().toString(), new Gson().toJson(aban));
						dm.getJedis().publish("update", "updateProfile|" + prof.toString());
					}
				}
			}
			
			if (banner instanceof Player) {
				Bukkit.broadcast("§eIP ddress" + address + " has been temporarily banned by " + ((Player) banner).getDisplayName() + "§e." , "nimbus.ban.viewip");
			} else {
				Bukkit.broadcast("§eIP ddress" + address + " has been temporarily banned by §4" + banner.getName() + "§e.", "nimbus.ban.viewip");
			}

			Bukkit.broadcast("§a" + address + " has been banned by " + banner.getName(), "nimbus.ban.viewip");

			for (Player all : Bukkit.getOnlinePlayers()) {
				if (all.getAddress().getAddress().getHostAddress().replace("/", "").equals(address)) {
					all.kickPlayer("§cThis IP Address has been suspended from the BreakMC Network.\n \n§cVisit BreakMC.com to purchase an unban or submit an appeal.");
				}
			}

		} catch (Exception ex) {
			banner.sendMessage("§cAn error occurred while parsing the time!\nError: " + ex.getLocalizedMessage() + "\nIf you see no problem with your time format, nag rbrick or Young_Explicit!");
			ex.printStackTrace();
		}
	}

	public void tempBan(Player banned, CommandSender banner, String time, String reason) {
		try {
			long bannedUntil = DateUtil.parseDateDiff(time, true);

			TempBan tban = new TempBan(banned.getUniqueId(), banner.getName(), reason, bannedUntil);
			Profile prof = new Gson().fromJson(dm.getJedis().get("{network_profile}" + banned.getUniqueId().toString()), Profile.class);

			dm.getJedis().set("{tempban}" + banned.getUniqueId().toString(), new Gson().toJson(tban));
			prof.setTempBanned(true);
			prof.setTempBans(prof.getTempBans() + 1);
			dm.getJedis().set("{network_profile}" + banned.getUniqueId().toString(), new Gson().toJson(prof));
			dm.getJedis().publish("update", "updateProfile|" + banned.toString());

			banned.kickPlayer(reason);
		} catch (Exception ex) {
			banner.sendMessage("§cAn error occurred while parsing the time!\nError: " + ex.getLocalizedMessage() + "\nIf you see no problem with your time format, nag rbrick or Young_Explicit!");
			ex.printStackTrace();
		}
	}

	public void unban(CommandSender unbanner, String name) {
		Set<String> keys = dm.getJedis().keys("{network_profile}*");

		Iterator<String> it = keys.iterator();
		Profile tProf = null;
		while (it.hasNext()) {
			String s = it.next();
			Profile prof = new Gson().fromJson(dm.getJedis().get(s), Profile.class);
			if (prof.getPlayerName().toLowerCase().equals(name.toLowerCase())) {
		        tProf = prof;
			}
		}
		
		if (tProf != null) {
			if (tProf.isPermBanned() || tProf.isTempBanned() || dm.getJedis().exists("{ban}" + tProf.getUniqueId().toString()) || dm.getJedis().exists("{tempban}" + tProf.getUniqueId().toString())) {
				tProf.setPermBanned(false);
				tProf.setTempBanned(false);
				
				if (dm.getJedis().exists("{ban}" + tProf.getUniqueId())) {
					dm.getJedis().del("{ban}" + tProf.getUniqueId().toString());
				}
					
				if (dm.getJedis().exists("{tempban}" + tProf.getUniqueId().toString())) {
					dm.getJedis().del("{tempban}" + tProf.getUniqueId().toString());
				}
				
				dm.getJedis().publish("update", "updateProfile|" + tProf.toString());
			}
		}
	}
	
	public void unbanAddress(CommandSender unbanner, String address) {
		Set<String> keys = dm.getJedis().keys("{network_profile}*");

		Iterator<String> it = keys.iterator();
		HashSet<Profile> profs = new HashSet<>();
		while (it.hasNext()) {
			String s = it.next();
			Profile prof = new Gson().fromJson(dm.getJedis().get(s), Profile.class);
			if (prof.getIps().contains(address)) {
		         profs.add(prof); 
			}
		}
		
		if (!(profs.size() > 0)) {
			for (Profile tProf : profs) {
				if (tProf.isPermBanned() || tProf.isTempBanned() || dm.getJedis().exists("{ban}" + tProf.getUniqueId().toString()) || dm.getJedis().exists("{tempban}" + tProf.getUniqueId().toString())) {
					tProf.setPermBanned(false);
					tProf.setTempBanned(false);
					
					if (dm.getJedis().exists("{ban}" + tProf.getUniqueId())) {
						dm.getJedis().del("{ban}" + tProf.getUniqueId().toString());
					}
					
					if (dm.getJedis().exists("{tempban}" + tProf.getUniqueId().toString())) {
						dm.getJedis().del("{tempban}" + tProf.getUniqueId().toString());
					}
					
					dm.getJedis().publish("update", "updateProfile|" + tProf.toString());
					
					unbanner.sendMessage("§aProfile: " + tProf.getUniqueId());
				}
			}
		} else {
			unbanner.sendMessage("§cCould not find any accounts affiliated with the IP '" + address + "'.");
		}
	}
	
	public void checkBan(CommandSender p, String name) {
		Set<String> keys = dm.getJedis().keys("{network_profile}*");

		Iterator<String> it = keys.iterator();
		Profile cbProf = null;
		while (it.hasNext()) {
			String s = it.next();
			 Profile prof = new Gson().fromJson(dm.getJedis().get(s), Profile.class);
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
				TempBan tb = new Gson().fromJson(dm.getJedis().get("{tempban}" + cbProf.getUniqueId().toString()), TempBan.class);
				p.sendMessage("§7Ban Time remaining: §c" + DateUtil.formatDateDiff(tb.getBannedUntil()));
			}
			
			if (cbProf.isPermBanned()) {
				Ban b = new Gson().fromJson(dm.getJedis().get("{ban}" + cbProf.getUniqueId().toString()), Ban.class);
				p.sendMessage("§7Account Ban Reason: §c" + b.getReason());
			} else if (cbProf.isTempBanned()) {
				TempBan tb = new Gson().fromJson(dm.getJedis().get("{tempban}" + cbProf.getUniqueId().toString()), TempBan.class);
				p.sendMessage("§7Account Ban Reason: §c" + tb.getReason());
			}
		} else {
			p.sendMessage("§cProfile for '" + name + "' not found.");
		}
	}
}