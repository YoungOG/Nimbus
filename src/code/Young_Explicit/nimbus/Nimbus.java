package code.Young_Explicit.nimbus;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.Jedis;
import code.Young_Explicit.nimbus.commands.Command_ban;
import code.Young_Explicit.nimbus.commands.Command_baninfo;
import code.Young_Explicit.nimbus.commands.Command_banip;
import code.Young_Explicit.nimbus.commands.Command_checkban;
import code.Young_Explicit.nimbus.commands.Command_commandspy;
import code.Young_Explicit.nimbus.commands.Command_restart;
import code.Young_Explicit.nimbus.commands.Command_tempban;
import code.Young_Explicit.nimbus.commands.Command_tempbanip;
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
	private BanManager bm;

	public void onEnable() {
		instance = this;

		dm = new DatabaseManager();
		cm = new ConverseManager();
		sm = new ServerManager();
		bm = new BanManager();

		getCommand("ban").setExecutor(new Command_ban());
		getCommand("banip").setExecutor(new Command_banip());
		getCommand("tempban").setExecutor(new Command_tempban());
		getCommand("tempbanip").setExecutor(new Command_tempbanip());
		getCommand("unban").setExecutor(new Command_unban());
		getCommand("unbanip").setExecutor(new Command_unbanip());
		getCommand("checkban").setExecutor(new Command_checkban());
		getCommand("baninfo").setExecutor(new Command_baninfo());
		getCommand("restart").setExecutor(new Command_restart());
		getServer().getPluginManager().registerEvents(new Command_commandspy(), this);
		getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Jedis j = dm.getPool().getResource();
				Bukkit.getLogger().log(Level.INFO, "§7[§bNimbus§7]: §ePining the Jedis Server... " + j.ping());
				dm.getPool().returnResource(j);
				dm.getPool().destroy();
			}
		}, 0L, 60*20);
		
//		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
//			public void run() {
//				try {
//					//pm
//				    String string1 = "11:55:00";
//				    Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
//				    Calendar calendar1 = Calendar.getInstance();
//				    calendar1.setTime(time1);
//				    
//				    String string2 = "12:00:00";
//				    Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
//				    Calendar calendar2 = Calendar.getInstance();
//				    calendar1.setTime(time2);
//
//				    //am
//				    String string3 = "23:55:00";
//				    Date time3 = new SimpleDateFormat("HH:mm:ss").parse(string3);
//				    Calendar calendar3 = Calendar.getInstance();
//				    calendar3.setTime(time3);
//				    
//				    String string4 = "00:00:00";
//				    Date time4 = new SimpleDateFormat("HH:mm:ss").parse(string4);
//				    Calendar calendar4 = Calendar.getInstance();
//				    calendar4.setTime(time4);
//				    
//				    Calendar calendar5 = Calendar.getInstance();
//				    
//				    Date x = calendar5.getTime();
//				    if (x.getHours() == calendar1.getTime().getHours() || x.getHours() == calendar2.getTime().getHours()) {
//				    	ServerManager.restart();
//				    }
//				} catch (ParseException e) {
//				    e.printStackTrace();
//				}
//			}
//		}, 0L, 5*20);
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
