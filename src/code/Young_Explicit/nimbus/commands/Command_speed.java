package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.ConverseManager;

public class Command_speed implements CommandExecutor, Listener {

	ConverseManager cm = Nimbus.getInstance().getConverseManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.speed")) {
			p.sendMessage("No Permission Message");
			return false;
		}

		if (args.length == 0) {
			p.sendMessage("Incorrect Usage Message");
			return false;
		}

		if (args.length <= 1) {
			int speed = 1;

			try {
				speed = Integer.parseInt(args[0]);
			} catch (NumberFormatException ex) {
				p.sendMessage("Not Valid Number Message");
				return true;
			}

			if (speed <= 9) {
				p.setFlySpeed(Float.parseFloat("0." + speed));
				p.sendMessage("Fly Speed Set Message: " + speed);
				return true;
			} else {
				p.sendMessage("Speed Not Valid Message 0-9");
				return false;
			}
		} else if (args.length >= 2) {
			if (!p.hasPermission("nimbus.speed.others")) {
				p.sendMessage("No Permission Message");
				return false;
			}

			Player target = Bukkit.getPlayer(args[1]);

			int speed = 1;

			try {
				speed = Integer.parseInt(args[0]);
			} catch (NumberFormatException ex) {
				p.sendMessage("Not Valid Number Message");
				return true;
			}

			if (speed <= 9) {
				p.sendMessage("Set Players Speed Message");
				target.setFlySpeed(Float.parseFloat("0." + speed));
			} else {
				p.sendMessage("Speed Not Valid Message 0-9");
				return false;
			}
		}
		return true;
	}
}
