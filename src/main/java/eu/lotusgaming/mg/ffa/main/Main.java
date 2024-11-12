package eu.lotusgaming.mg.ffa.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.lotusgaming.mg.ffa.command.FFA_CMD;
import eu.lotusgaming.mg.ffa.command.Stats_CMD;
import eu.lotusgaming.mg.ffa.event.Chat_LIS;
import eu.lotusgaming.mg.ffa.event.FFA_LIS;
import eu.lotusgaming.mg.ffa.misc.MySQLConnect;

public class Main extends JavaPlugin{
	
	public static String prefix = "§7[§6FFA§7] ";
	public static String noperm = prefix + "§cDarauf hast du keine Berechtigung!";
	public static String noplayer = prefix + "§cDu musst ein Spieler sein!";

	static File coinsapi = new File("plugins/LotusFFA/mysql.yml");
	
	public static Main instance;
	
	public void onEnable() {
		instance = this;
		createFile();
		onmysqlfile();
		Chat_LIS.startSchedulermapchange();
		Chat_LIS.startscoreboardScheduler();
		Bukkit.getConsoleSender().sendMessage(prefix + "§aDas Plugin wurde geladen!");
		Bukkit.getConsoleSender().sendMessage(prefix + "§6FFA §cVersion: §e" + this.getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage(prefix + "§cEntwickelt von: §e" + this.getDescription().getAuthors());
	
		getCommand("FFA").setExecutor(new FFA_CMD());
		getCommand("Stats").setExecutor(new Stats_CMD());
		
		getServer().getPluginManager().registerEvents(new FFA_LIS(), this);
		getServer().getPluginManager().registerEvents(new Chat_LIS(), this);
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(prefix + "§cDas Plugin wurde gestoppt!");
	}
	
	public void createFile() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onmysqlfile() {
		if(!coinsapi.exists()) {
			try {
				coinsapi.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(coinsapi);
			cfg.addDefault("Host", "127.0.0.1");
			cfg.addDefault("Port", "3306");
			cfg.addDefault("Datenbank", "Testdatenbank");
			cfg.addDefault("Username", "Test");
			cfg.addDefault("Passwort", "Supersicherespasswort");
			cfg.options().copyDefaults(true);
			try {
				cfg.save(coinsapi);
			} catch (IOException e) {
				e.printStackTrace();
			}
			MySQLConnect.connectsql();
		}
	
}
