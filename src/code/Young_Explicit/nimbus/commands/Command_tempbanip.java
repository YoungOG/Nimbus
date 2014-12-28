package code.Young_Explicit.nimbus.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;
import code.Young_Explicit.nimbus.utils.IPUtils;

public class Command_tempbanip implements CommandExecutor {

	BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (!p.hasPermission("nimbus.tempbanip")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length < 3) {
			p.sendMessage(String.format("§cUsage: /%s <IP Address> <time>", label));
			return true;
		}
		String ip = args[0];
		String time = args[1];

		if (IPUtils.isValidIP(ip)) {
			bm.tempbanAddress(ip, p, time, StringUtils.join(args, ' ', 2, args.length));
			return true;
		} else {
			p.sendMessage(ip + " does not seem to be a valid IP address!");
			return true;
		}
	}
}
