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

		
			if (args.length == 0) {
			  
			  if (player == null) {
	        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
	        return true;
	      }
			  
				if (SimpleExtras.Snowball_Grenade.containsKey(player)) {
					SimpleExtras.Snowball_Grenade.remove(player);
					sender.sendMessage(ChatColor.RED+" Grenades Deactivated");
				} else {
					SimpleExtras.Snowball_Grenade.put(player, true);
					sender.sendMessage(ChatColor.GREEN+" Grenades activated");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("-d")) {		
			  
			  if (player == null) {
	        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
	        return true;
	      } else if(!player.hasPermission("SimpleExtras.grenade.damage")) {
	        sender.sendMessage(ChatColor.RED+"No Permission");
          return true;
	      }	      
			  
				if (SimpleExtras.Snowball_Grenade.containsKey(player)) {
					SimpleExtras.Snowball_Grenade.remove(player);
					player.sendMessage(ChatColor.RED+" Grenades Deactivated");
				} else {
					SimpleExtras.Snowball_Grenade.put(player, false);
					player.sendMessage(ChatColor.GREEN+""+ChatColor.ITALIC+" Damage "+ChatColor.RESET+ChatColor.GREEN+"Grenades activated");
				}
				
				
			// GIVING MISSILE TO OTHER	
			} else if (args.length == 1 && !args[0].equalsIgnoreCase("-d") && player == null || args.length == 1 && !args[0].equalsIgnoreCase("-d") && player.hasPermission("SimpleExtras.Missile.other")) {
				String targetName = plugin.myGetPlayerName(args[0]);
				Player target = Bukkit.getPlayer(targetName);
				if (SimpleExtras.Snowball_Grenade.containsKey(target)) {
					SimpleExtras.Snowball_Grenade.remove(target);
					sender.sendMessage(ChatColor.RED+" Grenades Deactivated for "+targetName);
				} else {
					SimpleExtras.Snowball_Grenade.put(target, true);
					sender.sendMessage(ChatColor.GREEN+" Grenades activated for "+targetName);
				}
				
			} else if (args.length == 2 && args[0].equalsIgnoreCase("-d") && player == null || args.length == 2 && args[0].equalsIgnoreCase("-d") && player.hasPermission("SimpleExtras.Missile.damage.other")) {
				String targetName = plugin.myGetPlayerName(args[1]);
				Player target = Bukkit.getPlayer(targetName);
				if (SimpleExtras.Snowball_Grenade.containsKey(target)) {
					SimpleExtras.Snowball_Grenade.remove(target);
					sender.sendMessage(ChatColor.RED+" Grenades Deactivated for "+targetName);
				} else {
					SimpleExtras.Snowball_Grenade.put(target, false);
					sender.sendMessage(ChatColor.GREEN+""+ChatColor.ITALIC+" Damage"+ChatColor.RESET+ChatColor.GREEN+" Grenades activated for "+targetName);
				}
			}

		return true;
	}
}
