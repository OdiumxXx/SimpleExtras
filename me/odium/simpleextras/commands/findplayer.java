package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class findplayer implements CommandExecutor {   

  public SimpleExtras plugin;
  public findplayer(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    


    if (args.length == 0) {
      sender.sendMessage(ChatColor.GOLD + "[ "+ChatColor.WHITE+"Player Search"+ChatColor.GOLD+" ]");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /findplayer <PartofName> " + ChatColor.GRAY + "- Search for a player ");
      return true;
    } else {
      sender.sendMessage(ChatColor.GOLD + "Search Results:");
      int i;           
      OfflinePlayer[] Results = org.bukkit.Bukkit.getServer().getOfflinePlayers();
      for (i = 0; i < Results.length; i++) {              
        if (Results[i].getName().contains(args[0])) {
          sender.sendMessage(ChatColor.WHITE + "- " + ChatColor.DARK_GREEN + Results[i].getName());  
        }      
      } 
    }

    return true;    
  }

}