package code.Young_Explicit.nimbus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;
import code.Young_Explicit.nimbus.managers.ServerManager;

public class Command_restart implements CommandExecutor {

	BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (!p.hasPermission("nimbus.restart")) {
			p.sendMessage("§cPermission denied.");
			return false;
		}

		ServerManager.restart();

		return true;
	}
}
