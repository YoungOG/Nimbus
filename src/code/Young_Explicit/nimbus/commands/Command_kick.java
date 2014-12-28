package code.Young_Explicit.nimbus.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import code.Young_Explicit.nimbus.Nimbus;
import code.Young_Explicit.nimbus.managers.BanManager;

public class Command_kick implements CommandExecutor {

	private BanManager bm = Nimbus.getInstance().getBanManager();

	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		if (args.length > 1) {
			if (Bukkit.getPlayer(args[0]) != null) {
				bm.kickPlayer(Bukkit.getPlayer(args[1]), p, StringUtils.join(args, ' ', 2, args.length));
			} else {
				p.sendMessage("§cIncorrect Player");
				return false;
			}
		} else {
			p.sendMessage("§cIncorrect Usage");
			return false;
		}
		return false;
	}
}
