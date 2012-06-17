package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class website implements CommandExecutor {   

  public SimpleExtras plugin;
  public website(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    

    String website = plugin.getConfig().getString("website");  
    sender.sendMessage(ChatColor.GRAY + "Website: " + ChatColor.AQUA + website);

    return true;    
  }

}