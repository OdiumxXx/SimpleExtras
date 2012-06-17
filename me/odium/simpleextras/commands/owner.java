package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class owner implements CommandExecutor {   

  public SimpleExtras plugin;
  public owner(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    

    String owner = plugin.getConfig().getString("owner");
    sender.sendMessage(ChatColor.RED + owner + ChatColor.GRAY + " is the Owner of this server");


    return true;    
  }

}