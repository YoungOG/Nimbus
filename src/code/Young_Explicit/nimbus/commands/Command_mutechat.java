package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import code.Young_Explicit.nimbus.Nimbus;

public class Command_mutechat implements CommandExecutor, Listener {

	Nimbus instance = Nimbus.getInstance();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.mutechat")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (!instance.getServerManager().isChatMuted()) {
			instance.getServerManager().setChatMuted(true);
			Bukkit.broadcastMessage("Chat Muted Message");
			return true;
		} else {
			instance.getServerManager().setChatMuted(false);
			Bukkit.broadcastMessage("Chat UnMuted Message");
			return true;
		}
	}
}
