package me.odium.simpleextras.commands;


import java.util.Iterator;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ranks implements CommandExecutor {   

  public SimpleExtras plugin;
  public ranks(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
   
      java.util.List<String> rank = plugin.getConfig().getStringList("ranks");
      Iterator<String> iter = rank.iterator();        
      sender.sendMessage(ChatColor.GOLD + "- Server Ranks -");            
      while (iter.hasNext()) {
        sender.sendMessage(plugin.replaceColorMacros(iter.next()));
      }     
      return true;
    }
}
