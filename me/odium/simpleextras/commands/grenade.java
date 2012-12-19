package me.odium.simpleextras.commands;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class grenade implements CommandExecutor {

	public SimpleExtras plugin;
	public grenade(SimpleExtras plugin)  {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player == null || player.hasPermission("SimpleExtras.Grenade")) {

			if (args.length == 0 && player != null) {
				if (SimpleExtras.Snowball_Grenade.containsKey(player)) {
					SimpleExtras.Snowball_Grenade.remove(player);
					player.sendMessage(ChatColor.RED+" Grenades Deactivated");
				} else {
					SimpleExtras.Snowball_Grenade.put(player, true);
					player.sendMessage(ChatColor.GREEN+" Grenades activated");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("-d") && player.hasPermission("simpleextras.grenade.damage") && player != null) {
				if (SimpleExtras.Snowball_Grenade.containsKey(player)) {
					SimpleExtras.Snowball_Grenade.remove(player);
					player.sendMessage(ChatColor.RED+" Grenades Deactivated");
				} else {
					SimpleExtras.Snowball_Grenade.put(player, false);
					player.sendMessage(ChatColor.GREEN+""+ChatColor.ITALIC+" Damage "+ChatColor.RESET+ChatColor.GREEN+"Grenades activated");
				}
			} else if (args.length == 1 && !args[0].equalsIgnoreCase("-d") && player.hasPermission("simpleextras.grenade.other")) {
				String targetName = plugin.myGetPlayerName(args[0]);
				Player target = Bukkit.getPlayer(targetName);
				if (SimpleExtras.Snowball_Grenade.containsKey(target)) {
					SimpleExtras.Snowball_Grenade.remove(target);
					player.sendMessage(ChatColor.RED+" Grenades Deactivated for "+targetName);
				} else {
					SimpleExtras.Snowball_Grenade.put(target, true);
					player.sendMessage(ChatColor.GREEN+" Grenades activated for "+targetName);
				}
				
			} else if (args.length == 2 && args[0].equalsIgnoreCase("-d") && player.hasPermission("simpleextras.grenade.damage.other")) {
				String targetName = plugin.myGetPlayerName(args[1]);
				Player target = Bukkit.getPlayer(targetName);
				if (SimpleExtras.Snowball_Grenade.containsKey(target)) {
					SimpleExtras.Snowball_Grenade.remove(target);
					player.sendMessage(ChatColor.RED+" Grenades Deactivated for "+targetName);
				} else {
					SimpleExtras.Snowball_Grenade.put(target, false);
					player.sendMessage(ChatColor.GREEN+""+ChatColor.ITALIC+" Damage"+ChatColor.RESET+ChatColor.GREEN+" Grenades activated for "+targetName);
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED+"No Permission");
		}
		return true;
	}
}
