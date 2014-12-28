package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Command_invsee implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.invsee")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			p.sendMessage("Incorrect Usage Message");
			return false;
		}
		if (Bukkit.getPlayer(args[0]) != null) {
			Player player = (Player) sender;
			player.openInventory(Bukkit.getPlayer(args[0]).getInventory());
			p.sendMessage("Invsee Message");
			return true;
		} else {
			p.sendMessage("Player Not Valid Message");
			return false;
		}
	}
}
