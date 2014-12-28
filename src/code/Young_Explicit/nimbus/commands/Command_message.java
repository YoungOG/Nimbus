package code.Young_Explicit.nimbus.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import code.Young_Explicit.nimbus.Nimbus;

public class Command_message implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (args.length >= 2) {
			if (Bukkit.getPlayer(args[0]) != null) {
				Player target = Bukkit.getPlayer(args[0]);
				if (p.hasPermission("nimbus.messagebypass")) {
					String message = StringUtils.join(args, " ", 1, args.length);
					Nimbus.getInstance().getConverseManager().converse(message, p, target);
				} else {
					// if
					// (Init.profileManager.getPlayerProfile(player).isMuted())
					// {
					// MessageManager.message(player, false, ChatColor.RED +
					// "You are currently muted!");
					// return true;
					// }
					// if
					// (Init.profileManager.getPlayerProfile(target).isToggledPM())
					// {
					// MessageManager.message(player, false, ChatColor.RED +
					// "This player has his pms toggled!");
					// return true;
					// }
					String message = StringUtils.join(args, " ", 1, args.length);
					Nimbus.getInstance().getConverseManager().converse(message, p, target);
				}
			}
		} else {
			p.sendMessage("Incorrect Usage Message");
			return false;
		}
		return true;
	}
}
