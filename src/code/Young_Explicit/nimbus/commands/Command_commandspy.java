package code.Young_Explicit.nimbus.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Command_commandspy implements CommandExecutor, Listener {

	ArrayList<UUID> commandspy = new ArrayList<UUID>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.commandspy")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (!commandspy.contains(p.getUniqueId())) {
			commandspy.add(p.getUniqueId());
			p.sendMessage("Entered Commandspy Mode Message");
		} else {
			commandspy.remove(p.getUniqueId());
			p.sendMessage("Left Commandspy Mode Message");
		}
		return false;
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (commandspy.contains(p.getUniqueId())) {
				p.sendMessage("CommandSpy: " + e.getPlayer().getName() + ": " + e.getMessage());
			}
		}
	}
}
