package code.Young_Explicit.nimbus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Command_top implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player p = (Player) sender;
		
		if (p.hasPermission("breakmc.top")) {
			p.sendMessage("Incorrect Permission");
			return true;
		}

		if (!p.isDead()) {
			p.setHealth(0.0);
			return true;
		}
		return false;
	}
}
