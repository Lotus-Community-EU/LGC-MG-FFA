package eu.lotusgaming.mg.ffa.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import eu.lotusgaming.mg.ffa.event.Chat_LIS;
import eu.lotusgaming.mg.ffa.main.LotusController;
import eu.lotusgaming.mg.ffa.main.Main;
import eu.lotusgaming.mg.ffa.misc.Prefix;

public class FFA_CMD implements CommandExecutor{

	static File spawn = new File("plugins/LotusFFA/spawn.yml");
	public static ArrayList<String> build = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.noplayer);
		}else {
			Player p = (Player)sender;
			LotusController lc = new LotusController();
			if(p.hasPermission("FFA.Admin")) {
				if(args.length == 0) {
					p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7----[§6FFA " + lc.getPrefix(Prefix.SCOREBOARD) + "§7]----");
					p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§e/FFA setup §8>> §7Start the setup");
					p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§e/FFA build §8>> §aEnables§7/§cDisables §7the buildmode");
					p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§e/FFA setmapchange <2|3> <setspawn | pos1 | pos2> ");
					p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§e/FFA mapchange now §8>> §7Change the map immediately");
					p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§e/FFA reload §8>> §7Lädt die Konfiguration neu!");
					//p.sendMessage(lc.getPrefix(Prefix.MAIN) + "");
					//p.sendMessage(lc.getPrefix(Prefix.MAIN) + "");
					//p.sendMessage(lc.getPrefix(Prefix.MAIN) + "");
					//p.sendMessage(lc.getPrefix(Prefix.MAIN) + "");
				}else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("setup")) {
						//p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cBeginnen wir mit dem Spawn §8>> §e/FFA setup setspawn");
					}else if(args[0].equalsIgnoreCase("build")) {
						if(build.contains(p.getName())) {
							build.remove(p.getName());
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Der §eBaumodus §7wurde §cdeaktiviert!");
						}else {
							build.add(p.getName());
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Der §eBaumodus §7wurde §aaktiviert!");
						}
					}else if(args[0].equalsIgnoreCase("setmapchange")) {
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cVerwende: §8>> §e/FFA mapchange <2|3> <setspawn> <pos1> <pos2>");
					}else if(args[0].equalsIgnoreCase("reload")) {
						File config = new File("plugins/LotusFFA/config.yml");
						@SuppressWarnings("unused")
						YamlConfiguration configfile = YamlConfiguration.loadConfiguration(config);
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§aDie Konfiguration wurde neu geladen!");
					}else if(args[0].equalsIgnoreCase("mapchange")) {
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cVerwende: §8>> §e/FFA mapchange <now>");
					}
				}else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("setup") && args[1].equalsIgnoreCase("setspawn")) {
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
						cfg.set("Spawn.X", Double.valueOf(p.getLocation().getX()));
						cfg.set("Spawn.Y", Double.valueOf(p.getLocation().getY()));
						cfg.set("Spawn.Z", Double.valueOf(p.getLocation().getZ()));
						cfg.set("Spawn.YAW", Float.valueOf(p.getLocation().getYaw()));
						cfg.set("Spawn.PITCH", Float.valueOf(p.getLocation().getPitch()));
						cfg.set("Spawn.WORLD", p.getLocation().getWorld().getName());
						cfg.set("Spawn.ADMIN", p.getName());
						try {
							cfg.save(spawn);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§aDer §eSpawn §awurde gesetzt!");
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cNun setze die §eErste Position §cfür den Spawnbereich! §8>> §e/FFA setup pos1");
					}else if(args[0].equalsIgnoreCase("setup") && args[1].equalsIgnoreCase("pos1")) {
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
						cfg.set("Spawn.pos1.X", Double.valueOf(p.getLocation().getX()));
						cfg.set("Spawn.pos1.Y", Double.valueOf(p.getLocation().getY()));
						cfg.set("Spawn.pos1.Z", Double.valueOf(p.getLocation().getZ()));
						try {
							cfg.save(spawn);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§ePosition 1 §awurde gesetzt!");
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cNun setze zuletzt die §eZweite Position §cfür den Spawnbereich! §8>> §e/FFA setup pos2");
					}else if(args[0].equalsIgnoreCase("setup") && args[1].equalsIgnoreCase("pos2")) {
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
						cfg.set("Spawn.pos2.X", Double.valueOf(p.getLocation().getX()));
						cfg.set("Spawn.pos2.Y", Double.valueOf(p.getLocation().getY()));
						cfg.set("Spawn.pos2.Z", Double.valueOf(p.getLocation().getZ()));
						try {
							cfg.save(spawn);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§ePosition 2 §awurde gesetzt!");
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§aDas Setup wurde beendet! Der Server wird nun neugestartet!");
						Bukkit.getServer().shutdown();
					}else if(args[0].equalsIgnoreCase("setmapchange") && args[1].equalsIgnoreCase("2")) {
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cVerwende: §8>> §e/FFA mapchange <2|3> <setspawn> <pos1> <pos2>");
					}else if(args[0].equalsIgnoreCase("mapchange") && args[1].equalsIgnoreCase("now")) {
						File configfile = new File("plugins/LotusFFA/config.yml");
						YamlConfiguration config = YamlConfiguration.loadConfiguration(configfile);
						if(config.getBoolean("maps.map1") == true) {
							config.set("maps.map1", false);
							config.set("maps.map2", true);
							config.set("maps.map3", false);
							try {
								config.save(configfile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							for(Player all : Bukkit.getOnlinePlayers()) {
								Chat_LIS.setScoreboard(all);
								YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
								Location loc = p.getLocation();
								if(cfg.getString("Map2.WORLD") == null) {
									p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cDas Spiel wurde noch nicht eingerichtet!");
									p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cRichte es ganz einfach ein mit §e/FFA setup");
								}
								loc.setX(cfg.getDouble("Map2.X"));
								loc.setY(cfg.getDouble("Map2.Y"));
								loc.setZ(cfg.getDouble("Map2.Z"));
								loc.setYaw((float)cfg.getDouble("Map2.YAW"));
								loc.setPitch((float)cfg.getDouble("Map2.PITCH"));
								loc.setWorld(Bukkit.getWorld(cfg.getString("Map2.WORLD")));
								all.teleport(loc);
							}
							Bukkit.broadcastMessage(lc.getPrefix(Prefix.MAIN) + "§7Die Map wurde nun gewechselt zu: §e" + config.getString("ffa.Mapname2"));
						}else if(config.getBoolean("maps.map2") == true) {
							config.set("maps.map1", false);
							config.set("maps.map2", false);
							config.set("maps.map3", true);
							try {
								config.save(configfile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							for(Player all : Bukkit.getOnlinePlayers()) {
								Chat_LIS.setScoreboard(all);
								YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
								Location loc = p.getLocation();
								if(cfg.getString("Map3.WORLD") == null) {
									p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cDas Spiel wurde noch nicht eingerichtet!");
									p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cRichte es ganz einfach ein mit §e/FFA setup");
								}
								loc.setX(cfg.getDouble("Map3.X"));
								loc.setY(cfg.getDouble("Map3.Y"));
								loc.setZ(cfg.getDouble("Map3.Z"));
								loc.setYaw((float)cfg.getDouble("Map3.YAW"));
								loc.setPitch((float)cfg.getDouble("Map3.PITCH"));
								loc.setWorld(Bukkit.getWorld(cfg.getString("Map3.WORLD")));
								all.teleport(loc);
							}
							Bukkit.broadcastMessage(lc.getPrefix(Prefix.MAIN) + "§7Die Map wurde nun gewechselt zu: §e" + config.getString("ffa.Mapname3"));
						}else if(config.getBoolean("maps.map3") == true) {
							config.set("maps.map1", true);
							config.set("maps.map2", false);
							config.set("maps.map3", false);
							try {
								config.save(configfile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							for(Player all : Bukkit.getOnlinePlayers()) {
								Chat_LIS.setScoreboard(all);
								YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
								Location loc = p.getLocation();
								if(cfg.getString("Spawn.WORLD") == null) {
									p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cDas Spiel wurde noch nicht eingerichtet!");
									p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cRichte es ganz einfach ein mit §e/FFA setup");
								}
								loc.setX(cfg.getDouble("Spawn.X"));
								loc.setY(cfg.getDouble("Spawn.Y"));
								loc.setZ(cfg.getDouble("Spawn.Z"));
								loc.setYaw((float)cfg.getDouble("Spawn.YAW"));
								loc.setPitch((float)cfg.getDouble("Spawn.PITCH"));
								loc.setWorld(Bukkit.getWorld(cfg.getString("Spawn.WORLD")));
								all.teleport(loc);
							}
							Bukkit.broadcastMessage(lc.getPrefix(Prefix.MAIN) + "§7Die Map wurde nun gewechselt zu: §e" + config.getString("ffa.Mapname1"));
						}
					}
						
					}else if(args.length == 3) {
						if(args[0].equalsIgnoreCase("setmapchange") && args[1].equalsIgnoreCase("2") && args[2].equalsIgnoreCase("setspawn")) {
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
							cfg.set("Map2.X", Double.valueOf(p.getLocation().getX()));
							cfg.set("Map2.Y", Double.valueOf(p.getLocation().getY()));
							cfg.set("Map2.Z", Double.valueOf(p.getLocation().getZ()));
							cfg.set("Map2.YAW", Float.valueOf(p.getLocation().getYaw()));
							cfg.set("Map2.PITCH", Float.valueOf(p.getLocation().getPitch()));
							cfg.set("Map2.WORLD", p.getLocation().getWorld().getName());
							cfg.set("Map2.ADMIN", p.getName());
							try {
								cfg.save(spawn);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§aDer §eSpawn §afür §eMap 2 §awurde gesetzt!");
						}else if(args[0].equalsIgnoreCase("setmapchange") && args[1].equalsIgnoreCase("3") && args[2].equalsIgnoreCase("setspawn")) {
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
							cfg.set("Map3.X", Double.valueOf(p.getLocation().getX()));
							cfg.set("Map3.Y", Double.valueOf(p.getLocation().getY()));
							cfg.set("Map3.Z", Double.valueOf(p.getLocation().getZ()));
							cfg.set("Map3.YAW", Float.valueOf(p.getLocation().getYaw()));
							cfg.set("Map3.PITCH", Float.valueOf(p.getLocation().getPitch()));
							cfg.set("Map3.WORLD", p.getLocation().getWorld().getName());
							cfg.set("Map3.ADMIN", p.getName());
							try {
								cfg.save(spawn);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§aDer §eSpawn §afür §eMap 3 §awurde gesetzt!");
						}else if(args[0].equalsIgnoreCase("setmapchange") && args[1].equalsIgnoreCase("2") && args[2].equalsIgnoreCase("pos1")) {
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
							cfg.set("Map2.pos1.X", Double.valueOf(p.getLocation().getX()));
							cfg.set("Map2.pos1.Y", Double.valueOf(p.getLocation().getY()));
							cfg.set("Map2.pos1.Z", Double.valueOf(p.getLocation().getZ()));
							try {
								cfg.save(spawn);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§ePosition 1 §afür §eMap 2 §awurde gesetzt!");
						}else if(args[0].equalsIgnoreCase("setmapchange") && args[1].equalsIgnoreCase("2") && args[2].equalsIgnoreCase("pos2")) {
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
							cfg.set("Map2.pos2.X", Double.valueOf(p.getLocation().getX()));
							cfg.set("Map2.pos2.Y", Double.valueOf(p.getLocation().getY()));
							cfg.set("Map2.pos2.Z", Double.valueOf(p.getLocation().getZ()));
							try {
								cfg.save(spawn);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§ePosition 2 §afür §eMap 2 §awurde gesetzt!");
						}else if(args[0].equalsIgnoreCase("setmapchange") && args[1].equalsIgnoreCase("3") && args[2].equalsIgnoreCase("pos1")) {
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
							cfg.set("Map3.pos1.X", Double.valueOf(p.getLocation().getX()));
							cfg.set("Map3.pos1.Y", Double.valueOf(p.getLocation().getY()));
							cfg.set("Map3.pos1.Z", Double.valueOf(p.getLocation().getZ()));
							try {
								cfg.save(spawn);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§ePosition 1 §afür §eMap 3 §awurde gesetzt!");
						}else if(args[0].equalsIgnoreCase("setmapchange") && args[1].equalsIgnoreCase("3") && args[2].equalsIgnoreCase("pos2")) {
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(spawn);
							cfg.set("Map3.pos2.X", Double.valueOf(p.getLocation().getX()));
							cfg.set("Map3.pos2.Y", Double.valueOf(p.getLocation().getY()));
							cfg.set("Map3.pos2.Z", Double.valueOf(p.getLocation().getZ()));
							try {
								cfg.save(spawn);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§ePosition 2 §afür §eMap 3 §awurde gesetzt!");
						}
					}
			}else {
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§6FFA §cVersion: §e" + Main.instance.getDescription().getVersion());
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cEntwickelt von: §e" + Main.instance.getDescription().getAuthors());
			}
			
		}
		return true;
	}
}