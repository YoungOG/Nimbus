package code.Young_Explicit.nimbus.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.ItemMeta;

public class Command_setname implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.setname")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			p.sendMessage("Incorrect Usage Message");
			return true;
		}

		if (args[0].equalsIgnoreCase("-n")) {
			if (p.getItemInHand() != null || p.getItemInHand().getType() != Material.AIR) {
				ItemMeta meta = p.getItemInHand().getItemMeta();
				String nametag;
				StringBuilder sb = new StringBuilder();
				// StringUtils.join
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				nametag = sb.toString();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nametag));
				p.getItemInHand().setItemMeta(meta);
				p.sendMessage("Item Rename Message Name: " + ChatColor.translateAlternateColorCodes('&', nametag));
				return true;
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("-colors")) {
			for (ChatColor c : ChatColor.values()) {
				p.sendMessage(c + "&" + c.getChar() + " - " + c.toString());
			}
			return true;
		}
		return true;
	}
}
