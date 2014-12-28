package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_clearchat implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("nimbus.commandspy")) {
			sender.sendMessage("No Permissions Message");
			return false;
		}

		for (int i = 0; i < 200; i++) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage("");
			}
		}

		Bukkit.broadcastMessage("Chat Cleared Message");

		return false;
	}
}
