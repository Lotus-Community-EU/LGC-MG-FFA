package eu.lotusgaming.mg.ffa.misc;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.configuration.file.YamlConfiguration;

public class MySQLConnect {

	static File coinsapi = new File("plugins/LotusFFA/mysql.yml");

	public static void connectsql() {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(coinsapi);
		MySQL.connect(cfg.getString("Host"), cfg.getString("Port"), cfg.getString("Datenbank"), cfg.getString("Username"), cfg.getString("Passwort"));
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Coins(UUID varchar(64), COINS int)");
			ps.executeUpdate();
			PreparedStatement pss = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS StatsFFA (UUID VARCHAR(100), KILLS int, DEATHS int , WIN int , LOSE int , COINS int , BEDS int)");
			pss.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
