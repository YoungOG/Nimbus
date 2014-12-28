package code.Young_Explicit.nimbus.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.ConverseManager;

public class Command_reply implements CommandExecutor, Listener {

	ConverseManager cm = Nimbus.getInstance().getConverseManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (args.length == 0) {
			p.sendMessage(cm.getConverseWith(p) == null ? "Not In Conversation Message" : "In Conversation With: " + cm.getConverseWith(p));
			return true;
		}

		if (args.length > 0) {
			if (cm.getConverseWith(p) != null) {
				if (p.hasPermission("nimbus.messagebypass")) {
					String message = StringUtils.join(args, " ", 0, args.length);
					// if (profile.isToggledPM()) {
					// profile.setToggledPMs(false);
					// }
					cm.reply(p, message);
					return true;
				} else {
					Player target = cm.getConverseWith(p);
					// if
					// (Init.profileManager.getPlayerProfile(target).isToggledPM())
					// {
					// MessageManager.message(player, false, ChatColor.RED +
					// "This player has his messages toggled off!");
					// return true;
					// }
					String message = StringUtils.join(args, " ", 0, args.length);
					cm.converse(message, p, target);
					return true;
				}
			} else {
				p.sendMessage("Not In Conversation Message");
				return false;
			}
		}
		return true;
	}
}
