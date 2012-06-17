package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class levelup implements CommandExecutor {   

  public SimpleExtras plugin;
  public levelup(SimpleExtras plugin)  {
    plugin.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {      
      if (player == null) {
        sender.sendMessage("This command can only be run by a player");
      } else {
        int cl = player.getLevel();
        int cla = cl + 1;
        player.setLevel(cla);
        player.sendMessage(ChatColor.WHITE + "You've levelled up, Cheater!");
        return true;
      }
    }
    if (args.length == 1) {

      if(Bukkit.getPlayer(args[0]) != null) {
        Player target = Bukkit.getPlayer(args[0]);
        int cl = target.getLevel();
        int cla = cl + 1;
        target.setLevel(cla);          
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " has been levelled up");
        target.sendMessage(ChatColor.DARK_GREEN + sender.getName() + ChatColor.WHITE + " Raised your Experience Level");           
        return true;                     
      } else if(Bukkit.getPlayer(args[0]) == null) {        
        sender.sendMessage(ChatColor.DARK_GREEN + "'" + args[0] + "'" + ChatColor.WHITE + " is not online.");
        return true;
      }
    } else if (args.length == 2) {

      if(Bukkit.getPlayer(args[0]) != null) {           
        Player target = Bukkit.getPlayer(args[0]);
        int cl = target.getLevel();
        int lvl = Integer.parseInt( args[1] );
        int cla = cl + lvl;
        target.setLevel(cla);          
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " has been levelled up");
        target.sendMessage(ChatColor.DARK_GREEN + sender.getName() + ChatColor.WHITE + " Raised your Experience Level by " + ChatColor.DARK_GREEN + lvl);            
        return true;        
      } else {
        sender.sendMessage(ChatColor.DARK_GREEN + "'" + args[0] + "'" + ChatColor.WHITE + " is not online");
        return true;             
      }
    }

    return true;    
  }

}