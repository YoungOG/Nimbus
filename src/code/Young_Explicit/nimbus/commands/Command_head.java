package code.Young_Explicit.nimbus.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Command_head implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.head")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (args.length == 0) {
			p.sendMessage("Incorrect Usage Message");
			return false;
		} else {
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			meta.setOwner(args[0]);
			head.setItemMeta(meta);
			p.getInventory().addItem(head);
			p.sendMessage("Spawned Head Message Head:" + args[0]);
			return true;
		}
	}
}
