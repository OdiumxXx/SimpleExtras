package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class se implements CommandExecutor {   

  public SimpleExtras plugin;
  public se(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {   
      sender.sendMessage(ChatColor.GOLD + "[ "+ChatColor.WHITE+"SimpleExtras "+plugin.getDescription().getVersion()+ChatColor.GOLD+" ]");
      sender.sendMessage(ChatColor.GOLD + " Menu Options:");
      if(player == null || player.hasPermission("simpleextras.exp")) {
        sender.sendMessage(ChatColor.AQUA + "  /exp " + ChatColor.WHITE + "- Experience Based Rewards");
      }
      if(player == null || player.hasPermission("simpleextras.seen")) {
        sender.sendMessage(ChatColor.AQUA + "  /seen " + ChatColor.WHITE + "- Info on players first/last appearance");
      }

      if(player == null || player.hasPermission("simpleextras.zoom")) {
        sender.sendMessage(ChatColor.AQUA + "  /zoom " + ChatColor.WHITE + "- Zoom in/up/down a # of blocks");
      }
      if(player == null || player.hasPermission("simpleextras.effects")) {
        sender.sendMessage(ChatColor.AQUA + "  /effects " + ChatColor.WHITE + "- Effects Menu");
      }
      if(player == null || player.hasPermission("simpleextras.mobattack")) {
        sender.sendMessage(ChatColor.AQUA + "  /mobattack menu " + ChatColor.WHITE + "- Mobattack Menu");
      }
      sender.sendMessage(ChatColor.GOLD +" Stand-alone Commands");
      if(player == null || player.hasPermission("simpleextras.findplayer")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /findplayer " + ChatColor.WHITE + "- Search the player-history");
      }
      sender.sendMessage(ChatColor.DARK_GREEN + "  /ranks " + ChatColor.WHITE + "- Return a list of server ranks");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /basics " + ChatColor.WHITE + "- Return a list of server basic");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /admin " + ChatColor.WHITE + "- Return a list of server Admin");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /Colour " + ChatColor.WHITE + "- Return a list of Colour Codes");
      if(player == null || player.hasPermission("simpleextras.creative")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /creative " + ChatColor.WHITE + "- Change a gamemode to Creative");    
      }
      if(player == null || player.hasPermission("simpleextras.survival")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /survival " + ChatColor.WHITE + "- Change a gamemode to Survival");    
      }
      if(player == null || player.hasPermission("simpleextras.boom")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /boom [dsn] <player> " + ChatColor.WHITE + "- Explode a user");   
      }
      if(player == null || player.hasPermission("simpleextras.bed")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /bed " + ChatColor.WHITE + "- Teleport to your bed spawn");
      }
      if(player == null || player.hasPermission("simpleextras.tpb")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /tpb [lg] <player> " + ChatColor.WHITE + "- Teleport with a flourish");
      }
      if(player == null || player.hasPermission("simpleextras.flame")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /flame [On/Off] " + ChatColor.WHITE + "- Toggle your flame particles");
      }
      if(player == null || player.hasPermission("simpleextras.grow")) {
        sender.sendMessage(ChatColor.DARK_GREEN + "  /grow [-t/-c/-p/-m] [Radius] " + ChatColor.WHITE + "- Grow saplings/seedlings");
      }
      if(player == null || player.hasPermission("simpleextras.grenade")) {
          sender.sendMessage(ChatColor.DARK_GREEN + "  /grenade [-d]" + ChatColor.WHITE + "- Activate snowball grenades");
        }
      return true;  
    } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
      if(player == null || player.hasPermission("simpleextras.reload")) {
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "SimpleExtras Config Reloaded!");
        return true;
      } else {
        sender.sendMessage(ChatColor.RED + "You do not have permission");
        return true;
      }
    }
    return true;    
  }

}