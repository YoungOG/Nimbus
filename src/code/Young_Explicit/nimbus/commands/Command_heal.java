package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Command_heal implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.heal")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			p.setHealth(((Damageable) p).getMaxHealth());
			p.setFoodLevel(20);
			p.setFireTicks(0);
			p.sendMessage("Healed Message");
			return true;
		} else {
			if (Bukkit.getPlayer(args[0]) != null) {
				Bukkit.getPlayer(args[0]).setHealth(((Damageable) Bukkit.getPlayer(args[0])).getMaxHealth()); // badahbingbadahboom
				Bukkit.getPlayer(args[0]).setFoodLevel(20);
				Bukkit.getPlayer(args[0]).setFireTicks(0);
				p.sendMessage("Healed Other Player Message");
				return true;
			} else {
				p.sendMessage("Player Not Valid Message");
				return false;
			}
		}
	}
}
