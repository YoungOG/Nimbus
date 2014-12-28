package code.Young_Explicit.nimbus.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;
import code.Young_Explicit.nimbus.utils.IPUtils;

public class Command_banip implements CommandExecutor {

	BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (!p.hasPermission("nimbus.banip")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length < 2) {
			p.sendMessage(String.format("§cUsage: /%s <IP Address> <reason>", label));
			return true;
		}
		String ip = args[0];

		if (IPUtils.isValidIP(ip)) {
			bm.banAddress(ip, p, StringUtils.join(args, ' ', 1, args.length));
			return true;
		} else {
			p.sendMessage(ip + " does not seem to be a valid IP address!");
			return true;
		}
	}
}
