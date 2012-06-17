package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class levelset implements CommandExecutor {   

  public SimpleExtras plugin;
  public levelset(SimpleExtras plugin)  {
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
        player.setLevel(150);
        player.sendMessage(ChatColor.WHITE + "You've made yourself " + ChatColor.DARK_GREEN + "Level 150!");
        return true;
      } 
    }
    if (args.length == 1) {      
      if(Bukkit.getPlayer(args[0]) != null) {
        Player target = Bukkit.getPlayer(args[0]);
        target.setLevel(150);
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " Has been made " + ChatColor.DARK_GREEN + "Level 150!" );       
        target.sendMessage(ChatColor.WHITE + "You've been made " + ChatColor.DARK_GREEN + "Level 150 " + ChatColor.WHITE + "by " + ChatColor.DARK_GREEN + sender.getName());            
        return true;                     
      } else if(Bukkit.getPlayer(args[0]) == null) {        
        sender.sendMessage(ChatColor.DARK_GREEN + "'" + args[0] + "'" + ChatColor.WHITE + " is not online.");
        return true;
      }
    }     
    if (args.length == 2) {       
      if(Bukkit.getPlayer(args[0]) != null) {           
        Player target = Bukkit.getPlayer(args[0]);
        int lvlset = Integer.parseInt( args[1] );
        target.setLevel(lvlset);
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " Has been made " + ChatColor.DARK_GREEN + "Level " + lvlset );        
        target.sendMessage(ChatColor.WHITE + "You've been made " + ChatColor.DARK_GREEN + "Level " + lvlset + ChatColor.WHITE + " by " + ChatColor.DARK_GREEN + sender.getName());            
        return true;        
      } else if(Bukkit.getPlayer(args[0]) == null) {
        sender.sendMessage(ChatColor.DARK_GREEN + "'" + args[0] + "'" + ChatColor.WHITE + " is not online.");
        return true;             
      }
    }     

    return true;    
  }

}