package eu.lotusgaming.mg.ffa.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import eu.lotusgaming.mg.ffa.misc.MySQL;

public class Main extends JavaPlugin{
	
	//public static String prefix = "§7[§6FFA§7] ";
	public static String noplayer = "§cPlease execute this command in-game.";
	
	public static Logger logger;
	public static Main instance;
	
	public void onEnable() {
		instance = this;
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		LotusManager lm = new LotusManager();
		lm.preInit();
		lm.init();
		lm.postInit();
	}
	
	public void onDisable() {
		instance = null;
		MySQL.disconnect();
	}
}