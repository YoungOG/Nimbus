package code.Young_Explicit.nimbus.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;

public class Command_ban implements CommandExecutor {

	BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (!p.hasPermission("nimbus.ban")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length < 2) {
			p.sendMessage(String.format("§cUsage: /%s <player> <reason>", label));
			return true;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		bm.banPlayer(target, p, StringUtils.join(args, ' ', 1, args.length));

		return true;
	}
}
