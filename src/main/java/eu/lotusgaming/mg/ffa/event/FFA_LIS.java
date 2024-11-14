package eu.lotusgaming.mg.ffa.event;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.lotusgaming.mg.ffa.api.Attackcooldown;
import eu.lotusgaming.mg.ffa.api.StatsAPI;
import eu.lotusgaming.mg.ffa.command.FFA_CMD;
import eu.lotusgaming.mg.ffa.main.LotusController;
import eu.lotusgaming.mg.ffa.main.Main;
import eu.lotusgaming.mg.ffa.misc.Prefix;

public class FFA_LIS implements Listener{
	
	static File spawn = new File("plugins/LotusFFA/spawn.yml");
	static File config = new File("plugins/LotusFFA/config.yml");
	
	public void removePotionEffect(Player p) {
		for(PotionEffect ef : p.getActivePotionEffects()) {
			p.removePotionEffect(ef.getType());
		}
	}
	
	/* Inventar für das Game!  */
	public void giveInventory(Player p) {
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setLevel(0);
		p.getInventory().clear();
		p.getInventory().setItem(0, createItem(Material.IRON_SWORD, 1, null));
		p.getInventory().setItem(1, createItem(Material.BOW, 1, null));
		p.getInventory().setItem(6, createItem(Material.GOLDEN_APPLE, 2, null));
		p.getInventory().setItem(7, createItem(Material.ARROW, 8, null));
		p.getInventory().setItem(8, createItem(Material.FISHING_ROD, 1, null));
		p.getInventory().setHelmet(createItem(Material.IRON_HELMET, 1, null));
		p.getInventory().setChestplate(createItem(Material.DIAMOND_CHESTPLATE, 1, null));
		p.getInventory().setLeggings(createItem(Material.IRON_LEGGINGS, 1, null));
		p.getInventory().setBoots(createItem(Material.IRON_BOOTS, 1, null));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		YamlConfiguration configfile = YamlConfiguration.loadConfiguration(config);
		LotusController lc = new LotusController();
		Player p = e.getPlayer();
		Attackcooldown.setAttackCooldown(e.getPlayer(), Attackcooldown.attackCooldown);
		e.setJoinMessage(lc.getPrefix(Prefix.MAIN) + "§a" + p.getName() + " §7hat das Spiel betreten!");
		
		if(configfile.getBoolean("maps.map1") == true) {
		//	Bukkit.broadcastMessage(lc.getPrefix(Prefix.MAIN) + "§aAktuell wird auf §e" + configfile.getString("ffa.Mapname1") + " §agespielt!");
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
			p.teleport(loc);
		}else if(configfile.getBoolean("maps.map2") == true) {
		//	Bukkit.broadcastMessage(lc.getPrefix(Prefix.MAIN) + "§aAktuell wird auf §e" + configfile.getString("ffa.Mapname2") + " §agespielt!");
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
			p.teleport(loc);
		}else if(configfile.getBoolean("maps.map3") == true) {
		//	Bukkit.broadcastMessage(lc.getPrefix(Prefix.MAIN) + "§aAktuell wird auf §e" + configfile.getString("ffa.Mapname3") + " §agespielt!");
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
			p.teleport(loc);
		}
		
		giveInventory(p);
		removePotionEffect(p);
		for(Player all : Bukkit.getOnlinePlayers()) {
			Chat_LIS.setScoreboard(all);
			Chat_LIS.Tablist();
		}
		
		if(!StatsAPI.playerExists(p.getUniqueId().toString())) {
			StatsAPI.createPlayer(p.getUniqueId().toString());
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		LotusController lc = new LotusController();
		YamlConfiguration configfile = YamlConfiguration.loadConfiguration(config);
		if(configfile.getBoolean("maps.map1") == true) {
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
			p.teleport(loc);
			e.setRespawnLocation(loc);
		}else if(configfile.getBoolean("maps.map2") == true) {
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
			p.teleport(loc);
			e.setRespawnLocation(loc);
		}else if(configfile.getBoolean("maps.map3") == true) {
			Bukkit.broadcastMessage(lc.getPrefix(Prefix.MAIN) + "§aAktuell wird auf §e" + configfile.getString("ffa.Mapname3") + " §agespielt!");
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
			p.teleport(loc);
			e.setRespawnLocation(loc);
		}
		
		giveInventory(p);
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Player p = e.getEntity();
		LotusController lc = new LotusController();
        if(p.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
        	Player k = e.getEntity().getKiller();
        	e.setDeathMessage(lc.getPrefix(Prefix.MAIN) + "§a" +p.getName()+ "§e wurde von §a" + k.getName() + " §egetötet!");
        	StatsAPI.addDeaths(p.getUniqueId().toString(), 1);
        	StatsAPI.addKills(k.getUniqueId().toString(), 1);
        	/* So könntest du dem Spieler noch Coins zuweisen pro Kill! :)
        	 * 
        	 * CoinsAPI.addCoinsDB(p.getUniqueId().toString(), 50);
        	 */
            k.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
            k.getInventory().addItem(createItem(Material.ARROW, 3, null));
            k.getInventory().addItem(createItem(Material.GOLDEN_APPLE, 1, null));
        }else {
        	e.setDeathMessage(lc.getPrefix(Prefix.MAIN) + "§a" + p.getName() + "§7 ist gestorben!");
        	StatsAPI.addDeaths(p.getUniqueId().toString(), 1);
        }
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		LotusController lc = new LotusController();
		Attackcooldown.setAttackCooldown(e.getPlayer(), Attackcooldown.VANILLA_ATTACK_SPEED);
		e.setQuitMessage(lc.getPrefix(Prefix.MAIN) + "§c" + p.getName() + " §7hat das Spiel verlassen!");
		FFA_CMD.build.remove(p.getName());
	}
	
	@EventHandler
	public void onBreakbuildmode(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(FFA_CMD.build.contains(p.getName())) {
			e.setCancelled(false);
		}else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlacebuildmode(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(FFA_CMD.build.contains(p.getName())) {
			e.setCancelled(false);
		}else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL) {
			e.setCancelled(true);
			return;
		}
	/*	if(FFA_CMD.inSideSpawn(e.getEntity().getLocation())) {
			e.setCancelled(true);
		} */
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
	  @SuppressWarnings("unused")
	Player p = e.getPlayer();
	  if(e.getAction() == Action.PHYSICAL) {
	    e.setCancelled(true);
	  }
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if(FFA_CMD.build.contains(p.getName())) {
			e.setCancelled(false);
		}else {
			e.setCancelled(true);
		}
	}

	public static ItemStack createItem(Material material, int amount, String displayname) {
		ItemStack item = new ItemStack(material, amount);
        ItemMeta mitem = item.getItemMeta();
        mitem.setDisplayName(displayname);
        item.setItemMeta(mitem);
        return item;
    }
	
	
}
