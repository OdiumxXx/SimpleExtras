package me.odium.simpleextras.commands;


import java.util.Iterator;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class basics implements CommandExecutor {   

  public SimpleExtras plugin;
  public basics(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    

      java.util.List<String> basic = plugin.getConfig().getStringList("basics");
      Iterator<String> iter = basic.iterator();
      sender.sendMessage(ChatColor.GOLD + "[ "+ChatColor.WHITE+" Basic Commands"+ChatColor.GOLD+" ]");            
      while (iter.hasNext()) {                
        sender.sendMessage(plugin.replaceColorMacros(iter.next()));            
      }         
 
    return true;    
  }

}