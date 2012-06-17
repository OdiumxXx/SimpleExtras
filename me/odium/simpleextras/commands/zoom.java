package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class zoom implements CommandExecutor {   

  public SimpleExtras plugin;
  public zoom(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {          
      sender.sendMessage(ChatColor.GOLD + " [ Zoom ] ");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /zoom in # " + ChatColor.WHITE + "- Zoom where player is looking by # of blocks");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /zoom up # " + ChatColor.WHITE + "- Zoom UP number of blocks");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /zoom down # " + ChatColor.WHITE + "- Zoom DOWN number of blocks");      
      return true;
      
    } else if (args.length == 1 && args[0].equals("in")) {
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      Location playerLoc = player.getLocation();      
      Location loc = playerLoc.add(playerLoc.getDirection().normalize().multiply(25).toLocation(player.getWorld(), 0, 0));
      player.teleport(loc);
      sender.sendMessage(ChatColor.WHITE + "Zoomed " + ChatColor.DARK_GREEN + "25" + ChatColor.WHITE + " Blocks");
      return true;
    } else if (args.length == 1 && args[0].equals("up")) {
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      Location playerLoc = player.getLocation();      
      Location loc = playerLoc.add(0, 25, 0);
      player.teleport(loc);
      sender.sendMessage(ChatColor.WHITE + "Zoomed " + ChatColor.DARK_GREEN + "UP 25"+ChatColor.WHITE + " Blocks");
      return true;
    } else if (args.length == 1 && args[0].equals("down")) {
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      Location playerLoc = player.getLocation();
      Location loc = playerLoc.subtract(0, 25, 0);
      player.teleport(loc);
      sender.sendMessage(ChatColor.WHITE + "Zoomed " + ChatColor.DARK_GREEN + "DOWN " + "25" + ChatColor.WHITE + " Blocks");
      return true;

    } else if (args.length == 1) {
      sender.sendMessage(ChatColor.WHITE+"/zoom [in/up/down] <# of blocks>");
      return true;
    } else if (args.length == 2 && args[0].equalsIgnoreCase("in")) {
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      Location playerLoc = player.getLocation();
      float dist = Float.parseFloat(args[1]);
      Location loc = playerLoc.add(playerLoc.getDirection().normalize().multiply(dist).toLocation(player.getWorld(), 0, 0));
      player.teleport(loc);
      sender.sendMessage(ChatColor.WHITE + "Zoomed " + ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE + " Blocks");
      return true;
    } else if (args.length == 2 && args[0].equalsIgnoreCase("up")) {
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      Location playerLoc = player.getLocation();
      float dist = Float.parseFloat(args[1]);
      Location loc = playerLoc.add(0, dist, 0);
      player.teleport(loc);
      sender.sendMessage(ChatColor.WHITE + "Zoomed " + ChatColor.DARK_GREEN + "UP " + args[1] + ChatColor.WHITE + " Blocks");
      return true;
    } else if (args.length == 2 && args[0].equalsIgnoreCase("down")) {
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      Location playerLoc = player.getLocation();
      float dist = Float.parseFloat(args[1]);
      Location loc = playerLoc.subtract(0, dist, 0);
      player.teleport(loc);
      sender.sendMessage(ChatColor.WHITE + "Zoomed " + ChatColor.DARK_GREEN + "DOWN " + args[1] + ChatColor.WHITE + " Blocks");
      return true;
    }

    return true;    
  }

}