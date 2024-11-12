package eu.lotusgaming.mg.ffa.event;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import eu.lotusgaming.mg.ffa.main.Main;

public class Chat_LIS implements Listener{

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage().replace("%", "%%");
		if(p.hasPermission("LobbySystem.ColorChat")) {
			String msg1 = ChatColor.translateAlternateColorCodes('&', msg);
			e.setFormat(p.getDisplayName() + "§8: §f" + msg1);
		}else {
			e.setFormat(p.getDisplayName() + "§8: §f" + msg);
		}
	}
	
	public static void Tablist() {
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.setPlayerListHeaderFooter("§cLotus§aGaming §8>> §cHier kann dein Text stehen \n§coder hier \n§c§lICH DENKE AN DICH xD", 
					" §eFFA exclusiv für Lotus! \n§cViel Spaß damit \n§aLG Gianluca");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setScoreboard(Player p) {
		File config = new File("plugins/LotusFFA/config.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("aaa", "dummy", "bbb");
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName("§cLotus§aGaming §8>> " + Main.prefix);
		o.getScore("§6").setScore(4);
		o.getScore("§eMapname:").setScore(3);
		if(cfg.getBoolean("maps.map1") == true) {
			o.getScore("§8>> §6" + cfg.getString("ffa.Mapname1")).setScore(2);
		}else if(cfg.getBoolean("maps.map2") == true) {
			o.getScore("§8>> §6" + cfg.getString("ffa.Mapname2")).setScore(2);
		}else if(cfg.getBoolean("maps.map3") == true) {
			o.getScore("§8>> §6" + cfg.getString("ffa.Mapname3")).setScore(2);
		}else {
			o.getScore("§8>> §cFehler beim laden des Mapnamens..").setScore(2);
		}
		o.getScore("§9").setScore(1);
		if(cfg.getBoolean("mapsettings.teams") == true) {
			o.getScore("§aTeams erlaubt!").setScore(0);
		}else if(cfg.getBoolean("mapsettings.teams") == false) {
			o.getScore("§cTeams verboten!").setScore(0);
		}
		p.setScoreboard(sb);
	}
	
	public static void startscoreboardScheduler() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					setScoreboard(all);
					Tablist();
				}
				
			}
		}.runTaskTimer(Main.instance, 0, 600);
	}
	
	public static void startSchedulermapchange() {
		File configfile = new File("plugins/LotusFFA/config.yml");
		File spawn = new File("plugins/LotusFFA/spawn.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configfile);
		
		if(config.getBoolean("mapsettings.mapchange") == true) {
			
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(config.getBoolean("maps.map1") == true) {
						config.set("maps.map1", false);
						config.set("maps.map2", true);
						config.set("maps.map3", false);
						try {
							config.save(configfile);
						} catch (IOException e) {
							e.printStackTrace();
						}
							Chat_LIS.setScoreboard(all);
							Location loc = all.getLocation();
							if(cfg.getString("Map2.WORLD") == null) {
								all.sendMessage(Main.prefix + "§cDas Spiel wurde noch nicht eingerichtet!");
								all.sendMessage(Main.prefix + "§cRichte es ganz einfach ein mit §e/FFA setup");
							}
							loc.setX(cfg.getDouble("Map2.X"));
							loc.setY(cfg.getDouble("Map2.Y"));
							loc.setZ(cfg.getDouble("Map2.Z"));
							loc.setYaw((float)cfg.getDouble("Map2.YAW"));
							loc.setPitch((float)cfg.getDouble("Map2.PITCH"));
							loc.setWorld(Bukkit.getWorld(cfg.getString("Map2.WORLD")));
							Bukkit.broadcastMessage(Main.prefix + "§7Die Map wird in §e10 Sekunden §7gewechselt!");
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
								
								@Override
								public void run() {
									all.teleport(loc);
									Bukkit.broadcastMessage(Main.prefix + "§7Die Map wurde nun gewechselt zu: §e" + config.getString("ffa.Mapname2"));
									setScoreboard(all);
								}
							}, 100);
					}else if(config.getBoolean("maps.map2") == true) {
						config.set("maps.map1", false);
						config.set("maps.map2", false);
						config.set("maps.map3", true);
						try {
							config.save(configfile);
						} catch (IOException e) {
							e.printStackTrace();
						}
							Chat_LIS.setScoreboard(all);
							Location loc = all.getLocation();
							if(cfg.getString("Map3.WORLD") == null) {
								all.sendMessage(Main.prefix + "§cDas Spiel wurde noch nicht eingerichtet!");
								all.sendMessage(Main.prefix + "§cRichte es ganz einfach ein mit §e/FFA setup");
							}
							loc.setX(cfg.getDouble("Map3.X"));
							loc.setY(cfg.getDouble("Map3.Y"));
							loc.setZ(cfg.getDouble("Map3.Z"));
							loc.setYaw((float)cfg.getDouble("Map3.YAW"));
							loc.setPitch((float)cfg.getDouble("Map3.PITCH"));
							loc.setWorld(Bukkit.getWorld(cfg.getString("Map3.WORLD")));
							Bukkit.broadcastMessage(Main.prefix + "§7Die Map wird in §e10 Sekunden §7gewechselt!");
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
								
								@Override
								public void run() {
									all.teleport(loc);
									Bukkit.broadcastMessage(Main.prefix + "§7Die Map wurde nun gewechselt zu: §e" + config.getString("ffa.Mapname3"));
									setScoreboard(all);
								}
							}, 100);
					}else if(config.getBoolean("maps.map3") == true) {
						config.set("maps.map1", true);
						config.set("maps.map2", false);
						config.set("maps.map3", false);
						try {
							config.save(configfile);
						} catch (IOException e) {
							e.printStackTrace();
						}
							Chat_LIS.setScoreboard(all);
							Location loc = all.getLocation();
							if(cfg.getString("Spawn.WORLD") == null) {
								all.sendMessage(Main.prefix + "§cDas Spiel wurde noch nicht eingerichtet!");
								all.sendMessage(Main.prefix + "§cRichte es ganz einfach ein mit §e/FFA setup");
							}
							loc.setX(cfg.getDouble("Spawn.X"));
							loc.setY(cfg.getDouble("Spawn.Y"));
							loc.setZ(cfg.getDouble("Spawn.Z"));
							loc.setYaw((float)cfg.getDouble("Spawn.YAW"));
							loc.setPitch((float)cfg.getDouble("Spawn.PITCH"));
							loc.setWorld(Bukkit.getWorld(cfg.getString("Spawn.WORLD")));
							Bukkit.broadcastMessage(Main.prefix + "§7Die Map wird in §e10 Sekunden §7gewechselt!");
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
								
								@Override
								public void run() {
									all.teleport(loc);
									Bukkit.broadcastMessage(Main.prefix + "§7Die Map wurde nun gewechselt zu: §e" + config.getString("ffa.Mapname1"));
									setScoreboard(all);
								}
							}, 200);
						}
					
					}
				
				}
			}.runTaskTimer(Main.instance, 0, 12000);
		} else {
			Bukkit.getConsoleSender().sendMessage(Main.prefix + "§cMapchange wurde in der §econfig.yml §cdeaktiviert!");
		}
	}
}