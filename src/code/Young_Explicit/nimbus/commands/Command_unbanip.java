package code.Young_Explicit.nimbus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;
import code.Young_Explicit.nimbus.utils.IPUtils;

public class Command_unbanip implements CommandExecutor {

	BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (!p.hasPermission("nimbus.unbanip")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length < 1) {
			p.sendMessage(String.format("§cUsage: /%s <player> ", label));
			return true;
		}

		if (IPUtils.isValidIP(args[0])) {
			bm.unbanAddress(p, args[0]);
		} else {
			p.sendMessage("Not Valid IP");
		}

		return true;
	}
}
