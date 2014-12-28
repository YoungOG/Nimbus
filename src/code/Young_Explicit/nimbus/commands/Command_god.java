package code.Young_Explicit.nimbus.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Command_god implements CommandExecutor, Listener {

	ArrayList<UUID> godmode = new ArrayList<UUID>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.god")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			if (!godmode.contains(p.getUniqueId())) {
				godmode.add(p.getUniqueId());
				p.sendMessage("Godmode Enabled Message");
			} else {
				godmode.remove(p.getUniqueId());
				p.sendMessage("Godmode Disabled Message");
			}
			return true;
		} else {
			if (Bukkit.getPlayer(args[0]) != null) {
				if (!godmode.contains(Bukkit.getPlayer(args[0]).getUniqueId())) {
					godmode.add(Bukkit.getPlayer(args[0]).getUniqueId());
					Bukkit.getPlayer(args[0]).sendMessage("Godmode Enabled Message");
				} else {
					godmode.remove(Bukkit.getPlayer(args[0]).getUniqueId());
					Bukkit.getPlayer(args[0]).sendMessage("Godmode Disabled Message");
				}
				return true;
			} else {
				p.sendMessage("Player Not Valid Message");
				return false;
			}
		}
	}
}
