package code.Young_Explicit.nimbus.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Command_gamemode implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.gamemode")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (cmd.getName().equalsIgnoreCase("gmc")) {
			p.setGameMode(GameMode.CREATIVE);
			p.sendMessage("Gamemode Changed Message " + p.getGameMode().toString());
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("gms")) {
			p.setGameMode(GameMode.SURVIVAL);
			p.sendMessage("Gamemode Changed Message " + p.getGameMode().toString());
			return true;
		}

		if (args.length == 0) {
			p.sendMessage("Incorrect Usage Message");
		}

		String modeArg = args[0];

		String playerArg = p.getName();

		if (args.length == 2) {
			if (p.hasPermission("nimbus.gamemode.others")) {
				playerArg = args[1];
			} else {
				p.sendMessage("No Permissions Message");
			}
		}

		p = Bukkit.getPlayer(playerArg);

		if (p != null) {
			int value = -1;
			try {
				value = Integer.parseInt(modeArg);
			} catch (NumberFormatException ex) {
				p.sendMessage("Incorrect Number Format Message");
				return false;
			}

			GameMode mode = GameMode.getByValue(value);

			if (mode == null) {
				if (modeArg.equalsIgnoreCase("creative") || modeArg.equalsIgnoreCase("c")) {
					mode = GameMode.CREATIVE;
				} else if (modeArg.equalsIgnoreCase("adventure") || modeArg.equalsIgnoreCase("a")) {
					mode = GameMode.ADVENTURE;
				} else {
					mode = GameMode.SURVIVAL;
				}
			}
			if (mode != p.getGameMode()) {
				p.setGameMode(mode);

				if (mode != p.getGameMode()) {
				} else {
					if (p == sender) {
						p.sendMessage("Gamemode Changed Message " + mode.toString());
					} else {
						p.sendMessage("Gamemode Changed Message  " + mode.toString());
					}
				}
			}
		} else {
			return false;
		}
		return false;
	}
}
