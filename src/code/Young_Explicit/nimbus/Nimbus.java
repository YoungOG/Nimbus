package code.Young_Explicit.nimbus;

import org.bukkit.plugin.java.JavaPlugin;

import code.Young_Explicit.nimbus.commands.Command_ban;
import code.Young_Explicit.nimbus.commands.Command_banip;
import code.Young_Explicit.nimbus.commands.Command_checkban;
import code.Young_Explicit.nimbus.commands.Command_clearchat;
import code.Young_Explicit.nimbus.commands.Command_clearinventory;
import code.Young_Explicit.nimbus.commands.Command_commandspy;
import code.Young_Explicit.nimbus.commands.Command_fly;
import code.Young_Explicit.nimbus.commands.Command_gamemode;
import code.Young_Explicit.nimbus.commands.Command_god;
import code.Young_Explicit.nimbus.commands.Command_hat;
import code.Young_Explicit.nimbus.commands.Command_head;
import code.Young_Explicit.nimbus.commands.Command_heal;
import code.Young_Explicit.nimbus.commands.Command_invsee;
import code.Young_Explicit.nimbus.commands.Command_kick;
import code.Young_Explicit.nimbus.commands.Command_kill;
import code.Young_Explicit.nimbus.commands.Command_message;
import code.Young_Explicit.nimbus.commands.Command_reply;
import code.Young_Explicit.nimbus.commands.Command_setname;
import code.Young_Explicit.nimbus.commands.Command_speed;
import code.Young_Explicit.nimbus.commands.Command_suicide;
import code.Young_Explicit.nimbus.commands.Command_teleport;
import code.Young_Explicit.nimbus.commands.Command_teleporthere;
import code.Young_Explicit.nimbus.commands.Command_tempban;
import code.Young_Explicit.nimbus.commands.Command_tempbanip;
import code.Young_Explicit.nimbus.commands.Command_tppos;
import code.Young_Explicit.nimbus.commands.Command_unban;
import code.Young_Explicit.nimbus.commands.Command_unbanip;
import code.Young_Explicit.nimbus.listeners.PlayerListeners;
import code.Young_Explicit.nimbus.managers.BanManager;
import code.Young_Explicit.nimbus.managers.ConverseManager;
import code.Young_Explicit.nimbus.managers.DatabaseManager;
import code.Young_Explicit.nimbus.managers.ServerManager;

public class Nimbus extends JavaPlugin {

	public static Nimbus instance;
	private DatabaseManager dm;
	private ConverseManager cm;
	private ServerManager sm;
	private BanManager bm; // huehuehue bm

	public void onEnable() {
		instance = this;

		dm = new DatabaseManager();
		cm = new ConverseManager();
		sm = new ServerManager();
		bm = new BanManager();

		getCommand("clearchat").setExecutor(new Command_clearchat());
		getCommand("clearinventory").setExecutor(new Command_clearinventory());
		getCommand("commandspy").setExecutor(new Command_commandspy());
		getCommand("fly").setExecutor(new Command_fly());
		getCommand("gamemode").setExecutor(new Command_gamemode());
		getCommand("god").setExecutor(new Command_god());
		getCommand("hat").setExecutor(new Command_hat());
		getCommand("head").setExecutor(new Command_head());
		getCommand("heal").setExecutor(new Command_heal());
		getCommand("invsee").setExecutor(new Command_invsee());
		getCommand("kill").setExecutor(new Command_kill());
		getCommand("message").setExecutor(new Command_message());
		getCommand("reply").setExecutor(new Command_reply());
		getCommand("setname").setExecutor(new Command_setname());
		getCommand("speed").setExecutor(new Command_speed());
		getCommand("suicide").setExecutor(new Command_suicide());
		getCommand("teleport").setExecutor(new Command_teleport());
		getCommand("teleporthere").setExecutor(new Command_teleporthere());
		getCommand("tppos").setExecutor(new Command_tppos());
		getCommand("kick").setExecutor(new Command_kick());
		getCommand("ban").setExecutor(new Command_ban());
		getCommand("banip").setExecutor(new Command_banip());
		getCommand("tempban").setExecutor(new Command_tempban());
		getCommand("tempbanip").setExecutor(new Command_tempbanip());
		getCommand("unban").setExecutor(new Command_unban());
		getCommand("unbanip").setExecutor(new Command_unbanip());
		getCommand("checkban").setExecutor(new Command_checkban());
		getServer().getPluginManager().registerEvents(new Command_commandspy(), this);
		getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

	}

	public static Nimbus getInstance() {
		return instance;
	}

	public DatabaseManager getDatabaseManager() {
		return dm;
	}

	public ConverseManager getConverseManager() {
		return cm;
	}

	public ServerManager getServerManager() {
		return sm;
	}

	public BanManager getBanManager() {
		return bm;
	}
}
