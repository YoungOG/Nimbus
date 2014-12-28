package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_clearinventory implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.clearinventory")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			p.getInventory().clear();
			p.sendMessage("Inventory Cleared Message");
			return true;
		} else {
			if (Bukkit.getPlayer(args[0]) != null) {
				Player target = Bukkit.getPlayer(args[0]);
				if (args.length >= 2) {
					if (args[1].equalsIgnoreCase("-a")) {
						target.getInventory().clear();
						target.getInventory().setArmorContents(null);
						p.sendMessage("Cleared Players Inventory And Armor Message");
						return true;
					} else {
						target.getInventory().clear();
						p.sendMessage("Cleared Players Inventory Message");
						return true;
					}
				} else {
					target.getInventory().clear();
					p.sendMessage("Cleared Players Inventory Message");
					return true;
				}
			} else {
				if (args[0].equalsIgnoreCase("-a")) {
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					p.sendMessage("Cleared Inventory And Armor Message");
					return true;
				}
			}
		}
		return false;
	}
}
