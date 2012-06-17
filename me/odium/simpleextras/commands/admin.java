package me.odium.simpleextras.commands;


import java.util.Iterator;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class admin implements CommandExecutor {   

  public SimpleExtras plugin;
  public admin(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    

      java.util.List<String> admin = plugin.getConfig().getStringList("admin");
      Iterator<String> iter = admin.iterator();        
      sender.sendMessage(ChatColor.GOLD + "[ Server Administrators ]");            
      while (iter.hasNext()) {
        sender.sendMessage(plugin.replaceColorMacros(iter.next()));
      }     
      return true;
  }

}