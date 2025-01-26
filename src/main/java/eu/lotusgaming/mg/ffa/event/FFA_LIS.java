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
import eu.lotusgaming.mg.ffa.misc.Money;
import eu.lotusgaming.mg.ffa.misc.Prefix;

public class FFA_LIS implements Listener{
	
	static File config = new File("plugins/LotusFFA/config.yml");
	static YamlConfiguration cfg = null;
	
	{
		cfg = YamlConfiguration.loadConfiguration(config);
	}
	
	public void removePotionEffect(Player p) {
		for(PotionEffect ef : p.getActivePotionEffects()) {
			p.removePotionEffect(ef.getType());
		}
	}
	
	/* Inventar für das Game!  */
	public void giveInventory(Player p) {
		LotusController lc = new LotusController();
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setLevel(0);
		p.getInventory().clear();
		p.getInventory().setItem(0, lc.defItem(Material.IRON_SWORD, null, 1));
		p.getInventory().setItem(1, lc.defItem(Material.BOW, null, 1));
		p.getInventory().setItem(6, lc.defItem(Material.GOLDEN_APPLE, null, 2));
		p.getInventory().setItem(7, lc.defItem(Material.ARROW, null, 8));
		p.getInventory().setItem(8, lc.defItem(Material.FISHING_ROD, null, 1));
		p.getInventory().setHelmet(lc.defItem(Material.IRON_HELMET, null, 1));
		p.getInventory().setChestplate(lc.defItem(Material.DIAMOND_CHESTPLATE, null, 1));
		p.getInventory().setLeggings(lc.defItem(Material.IRON_LEGGINGS, null, 1));
		p.getInventory().setBoots(lc.defItem(Material.IRON_BOOTS, null, 1));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		LotusController lc = new LotusController();
		Player p = e.getPlayer();
		Attackcooldown.setAttackCooldown(e.getPlayer(), Attackcooldown.attackCooldown);
		e.setJoinMessage(lc.getPrefix(Prefix.MAIN) + "§a" + p.getName() + " §7hat das Spiel betreten!");
		Location loc = p.getLocation();
		if(cfg.getString("Spawn.WORLD") == null) {
			p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cDas Spiel wurde noch nicht eingerichtet!");
			p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cRichte es ganz einfach ein mit §e/FFA setup");
		}
		//TODO: UN-HARDCODE!!!!!!
		loc.setX(cfg.getDouble("Spawn.X"));
		loc.setY(cfg.getDouble("Spawn.Y"));
		loc.setZ(cfg.getDouble("Spawn.Z"));
		loc.setYaw((float)cfg.getDouble("Spawn.YAW"));
		loc.setPitch((float)cfg.getDouble("Spawn.PITCH"));
		loc.setWorld(Bukkit.getWorld(cfg.getString("Spawn.WORLD")));
		p.teleport(loc);
		
		giveInventory(p);
		removePotionEffect(p);
		for(Player all : Bukkit.getOnlinePlayers()) {
			Chat_LIS.setScoreboard(all);
			Chat_LIS.Tablist();
		}
		
		if(!new StatsAPI(p).hasMGAccount()) {
			new StatsAPI(p).createMGAccount();
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		LotusController lc = new LotusController();
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
		
		giveInventory(p);
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Player p = e.getEntity();
		LotusController lc = new LotusController();
        if(p.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
        	Player k = e.getEntity().getKiller();
        	e.setDeathMessage(lc.getPrefix(Prefix.MAIN) + "§a" + p.getName() + " §ehas been killed by §a" + k.getName() + " §e!");
        	StatsAPI sapi = new StatsAPI(p);
        	StatsAPI kapi = new StatsAPI(k);
        	sapi.addDeath();
        	kapi.addKill();
        	new LotusController().addMoney(p, 50, Money.BANK);
            k.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
            k.getInventory().addItem(lc.defItem(Material.ARROW, null, 3));
            k.getInventory().addItem(lc.defItem(Material.GOLDEN_APPLE, null, 1));
        }else {
        	e.setDeathMessage(lc.getPrefix(Prefix.MAIN) + "§a" + p.getName() + "§7 died!");
        	new StatsAPI(p).addDeath();
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
}