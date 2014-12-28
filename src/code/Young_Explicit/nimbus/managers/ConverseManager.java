package code.Young_Explicit.nimbus.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ConverseManager {

	private HashMap<String, String> conversations = new HashMap<String, String>();

	public void converse(String message, Player starter, Player target) {
		// if (ignoredLists.containsKey(starter.getName())) {
		// if (ignoredLists.get(starter.getName()).contains(target.getName())) {
		// ignoredLists.get(starter.getName()).remove(target.getName());
		// }
		// }
		// if (Init.profileManager.getPlayerProfile(starter).isToggledPM()) {
		// Init.profileManager.getPlayerProfile(starter).setToggledPMs(false);
		// }
		conversations.put(starter.getName(), target.getName());
		conversations.put(target.getName(), starter.getName());
		// for (Player player : Bukkit.getOnlinePlayers()) {
		// ProfileManager.PlayerProfile profile =
		// Init.profileManager.getPlayerProfile(player);
		// if (profile.isInSocialSpy()) {
		// MessageManager.message(player, false, "<SocialSpy: " +
		// starter.getName() + " >> " + target.getName() + " : " + s);
		// }
		// }
		// MessageManager.log("<Conversation: " + starter.getName() + " >> " +
		// target.getName() + " : " + s);
		starter.sendMessage("Me >> " + target.getName() + ": " + message);
		if (starter.hasPermission("nimbus.messagebypass")) {
			target.sendMessage(starter.getName() + " >> me: " + message);
		} else {
			// if (ignoredLists.containsKey(target.getName())) {
			// if
			// (!ignoredLists.get(target.getName()).contains(starter.getName()))
			// {
			// MessageManager.message(target, false, ChatColor.WHITE + "(" +
			// starter.getDisplayName() + ChatColor.GOLD + "" + ChatColor.BOLD +
			// " >> " + ChatColor.RESET + ChatColor.GRAY + "me" +
			// ChatColor.WHITE + ") " + ChatColor.GRAY + s);
			// }
			// } else {
			// MessageManager.message(target, false, ChatColor.WHITE + "(" +
			// starter.getDisplayName() + ChatColor.GOLD + "" + ChatColor.BOLD +
			// " >> " + ChatColor.RESET + ChatColor.GRAY + "me" +
			// ChatColor.WHITE + ") " + ChatColor.GRAY + s);
			// }
		}
	}

	public void reply(Player p, String message) {
		if (getConverseWith(p) != null) {
			converse(message, p, getConverseWith(p));
		} else {
			p.sendMessage("Not In Conversation");
		}
	}

	public Player getConverseWith(Player player) {
		if (conversations.containsKey(player.getName())) {
			if (Bukkit.getPlayer(conversations.get(player.getName())) != null) {
				return Bukkit.getPlayer(conversations.get(player.getName()));
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public HashMap<String, String> getConversations() {
		return conversations;
	}
}
