package code.Young_Explicit.nimbus.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class Command_hat implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("nimbus.hat")) {
			p.sendMessage("No Permissions Message");
			return false;
		}

		if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
			ItemStack old = p.getInventory().getHelmet();
			p.getInventory().setHelmet(p.getItemInHand());
			p.setItemInHand(old);
			p.sendMessage("Hat Set Message");
			return true;
		} else {
			p.sendMessage("Item Null Message");
			return false;
		}
	}
}
