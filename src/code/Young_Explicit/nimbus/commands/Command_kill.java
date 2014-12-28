package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Command_kill implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.kill")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			p.setHealth(0);
			return true;
		}
		if (p.hasPermission("nimbus.kill.others")) {
			if (Bukkit.getPlayer(args[0]) != null) {
				Bukkit.getPlayer(args[0]).setHealth(0);
				p.sendMessage("Killed Other Player Message");
				return true;
			} else {
				p.sendMessage("Player Not Valid Message");
				return false;
			}
		} else {
			p.setHealth(0);
			return true;
		}
	}
}
