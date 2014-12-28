package code.Young_Explicit.nimbus.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.ConverseManager;

public class Command_tppos implements CommandExecutor, Listener {

	ConverseManager cm = Nimbus.getInstance().getConverseManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.tppos")) {
			p.sendMessage("No Permission Message");
			return false;
		}

		if (args.length < 3) {
			p.sendMessage("Incorrect Usage Message");
			return false;
		}
		if (isNumber(args[0]) && isNumber(args[1]) && isNumber(args[2])) {
			p.teleport(new Location(p.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
			p.sendMessage("Teleport Message X: " + p.getLocation().getX() + " Y: " + p.getLocation().getY() + " Z: " + p.getLocation().getZ());
		} else {
			p.sendMessage("Not Valid Coordinates Message");
			return false;
		}

		return true;
	}

	public boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
