package code.Young_Explicit.nimbus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;

public class Command_checkban implements CommandExecutor {

	BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (!p.hasPermission("nimbus.checkban")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length != 1) {
			p.sendMessage(String.format("�cUsage: /%s <player> <reason>", label));
			return true;
		}
		
		bm.checkBan(p, args[0]);

		return true;
	}
}