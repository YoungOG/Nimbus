package code.Young_Explicit.nimbus.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;

public class Command_tempban implements CommandExecutor {

	BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (!p.hasPermission("nimbus.tempban")) {
			p.sendMessage("§cPermission denied.");
			return false;
		}

		if (args.length < 3) {
			p.sendMessage(String.format("§cUsage: /%s <player> <time> <reason>", label));
			return true;
		}

		if (Bukkit.getPlayer(args[0]) != null) {
			Player target = Bukkit.getPlayer(args[0]);
			bm.tempBan(target, p, args[1], StringUtils.join(args, ' ', 2, args.length));
		} else {
			bm.tempBanName(args[0], p, args[1], StringUtils.join(args, ' ', 2, args.length));
		}

		return true;
	}

}
