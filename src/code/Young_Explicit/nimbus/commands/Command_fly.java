package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Command_fly implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.fly")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			p.setAllowFlight(p.getAllowFlight() ? false : true);
			p.setFlying(p.getAllowFlight() ? true : false);
			p.sendMessage("Fly Message: " + (p.getAllowFlight() ? "enabled" : "disabled"));
		} else {
			if (!p.hasPermission("nimbus.fly.others")) {
				p.sendMessage("No Permissions Message");
				return false;
			}

			Player target = Bukkit.getPlayer(args[0]);

			if (target != null) {
				target.setAllowFlight(target.getAllowFlight() ? false : true);
				target.setFlying(target.getAllowFlight() ? true : false);
				target.sendMessage("Fly Message: " + (p.getAllowFlight() ? "enabled" : "disabled"));
				p.sendMessage("Fly Enabled For Other Player Message: " + (target.getAllowFlight() ? "enabled" : "disabled"));
			} else {
				p.sendMessage("Player Not Valid Message");
				return false;
			}
		}
		return false;
	}
}
