package me.odium.simpleextras.commands;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class missile implements CommandExecutor {

	public SimpleExtras plugin;
	public missile(SimpleExtras plugin)  {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player == null || player.hasPermission("SimpleExtras.Missile")) {

			if (args.length == 0 && player != null) {
				if (SimpleExtras.Arrow_Grenade.containsKey(player)) {
					SimpleExtras.Arrow_Grenade.remove(player);
					player.sendMessage(ChatColor.RED+" Missiles Deactivated");
				} else {
					SimpleExtras.Arrow_Grenade.put(player, true);
					player.sendMessage(ChatColor.GREEN+" Missiles activated");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("-d") && player.hasPermission("SimpleExtras.Missile.damage") && player != null) {
				if (SimpleExtras.Arrow_Grenade.containsKey(player)) {
					SimpleExtras.Arrow_Grenade.remove(player);
					player.sendMessage(ChatColor.RED+" Missiles Deactivated");
				} else {
					SimpleExtras.Arrow_Grenade.put(player, false);
					player.sendMessage(ChatColor.GREEN+""+ChatColor.ITALIC+" Damage "+ChatColor.RESET+ChatColor.GREEN+"Missiles activated");
				}
			} else if (args.length == 1 && !args[0].equalsIgnoreCase("-d") && player.hasPermission("SimpleExtras.Missile.other")) {
				String targetName = plugin.myGetPlayerName(args[0]);
				Player target = Bukkit.getPlayer(targetName);
				if (SimpleExtras.Arrow_Grenade.containsKey(target)) {
					SimpleExtras.Arrow_Grenade.remove(target);
					player.sendMessage(ChatColor.RED+" Missiles Deactivated for "+targetName);
				} else {
					SimpleExtras.Arrow_Grenade.put(target, true);
					player.sendMessage(ChatColor.GREEN+" Missiles activated for "+targetName);
				}
				
			} else if (args.length == 2 && args[0].equalsIgnoreCase("-d") && player.hasPermission("SimpleExtras.Missile.damage.other")) {
				String targetName = plugin.myGetPlayerName(args[1]);
				Player target = Bukkit.getPlayer(targetName);
				if (SimpleExtras.Arrow_Grenade.containsKey(target)) {
					SimpleExtras.Arrow_Grenade.remove(target);
					player.sendMessage(ChatColor.RED+" Missiles Deactivated for "+targetName);
				} else {
					SimpleExtras.Arrow_Grenade.put(target, false);
					player.sendMessage(ChatColor.GREEN+""+ChatColor.ITALIC+" Damage"+ChatColor.RESET+ChatColor.GREEN+" Missiles activated for "+targetName);
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED+"No Permission");
		}
		return true;
	}
}
