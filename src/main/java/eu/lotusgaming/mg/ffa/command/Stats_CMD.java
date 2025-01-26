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
				
				double kills = new StatsAPI(p).getKills();
				double tode = new StatsAPI(p).getDeaths();
				double kd = (kills / tode);
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7----[§6Deine FFA Stats§7]----");
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Your Kills: §6" + kills);
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Your Deaths: §6" + tode);
				p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Your K/D: §6" + kd);
			}else if(args.length == 1) {
				if(!p.hasPermission("lgc.command.statsother")) {
					lc.noPerm(p, "lgc.command.statsother");
				}else {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						if(new StatsAPI(target).hasMGAccount()) {
							double kills = new StatsAPI(target).getKills();
							double tode = new StatsAPI(target).getDeaths();
							double kd = (kills/tode);
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7----[§a" + target.getName() + " §6FFA Stats§7]----");
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Kills: §6" + kills);
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7Deaths: §6" + tode);
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + "§7K/D: §6" + kd);
						}else {
							p.sendMessage(lc.getPrefix(Prefix.MAIN) + lc.sendMessageToFormat(p, "command.mg_ffa.stats.noStats").replace("%target%", target.getName()));
						}
					}else {
						lc.sendMessageReady(p, "general.playerOffline");
					}
				}
			}
		}
		return true;
	}
}