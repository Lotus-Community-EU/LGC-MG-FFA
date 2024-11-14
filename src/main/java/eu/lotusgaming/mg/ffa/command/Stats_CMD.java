package eu.lotusgaming.mg.ffa.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.lotusgaming.mg.ffa.api.StatsAPI;
import eu.lotusgaming.mg.ffa.main.LotusController;
import eu.lotusgaming.mg.ffa.main.Main;
import eu.lotusgaming.mg.ffa.misc.Prefix;

public class Stats_CMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.noplayer);
		}else {
			Player p = (Player)sender;
			LotusController lc = new LotusController();
			if(args.length == 0) {
				
				double kills = StatsAPI.getKills(p.getUniqueId().toString());
				double tode = StatsAPI.getDeaths(p.getUniqueId().toString());
				double kd = (kills / tode);
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7----[§6Deine FFA Stats§7]----");
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Deine Kills: §6" + kills);
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Deine Tode: §6" + tode);
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Deine K/D: §6" + kd);
			}else if(args.length == 1) {
				if(!p.hasPermission("FFA.statsother")) {
					lc.noPerm(p, "FFA.statsother");
				}else {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						double kills = StatsAPI.getKills(target.getUniqueId().toString());
						double tode = StatsAPI.getDeaths(target.getUniqueId().toString());
						double kd = (kills/tode);
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7----[§a" + target.getName() + " §6FFA Stats§7]----");
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Kills: §6" + kills);
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Tode: §6" + tode);
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7K/D: §6" + kd);
					}else {
						p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§cDieser Spieler ist aktuell nicht verfügbar!");
					}
				}
			}
		}
		
		return true;
	}
	
	

}
