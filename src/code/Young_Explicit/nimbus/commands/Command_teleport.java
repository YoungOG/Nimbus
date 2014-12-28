package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.ConverseManager;

public class Command_teleport implements CommandExecutor, Listener {

	ConverseManager cm = Nimbus.getInstance().getConverseManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.teleport")) {
			p.sendMessage("No Permission Message");
			return false;
		}

		if (args.length == 0) {
			p.sendMessage("Incorrect Usage Command");
			return false;
		}

		if (Bukkit.getPlayer(args[0]) != null) {
			p.teleport(Bukkit.getPlayer(args[0]));
			p.sendMessage("Teleport Message");
			return true;
		} else {
			p.sendMessage("Player Is Not Valid");
			return false;
		}
	}
}
