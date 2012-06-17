package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class levelget implements CommandExecutor {   

  public SimpleExtras plugin;
  public levelget(SimpleExtras plugin)  {
    plugin.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    

    if(Bukkit.getPlayer(args[0]) != null) {
      Player target = Bukkit.getPlayer(args[0]);
      int targetlevel = target.getLevel();
      sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " is level " + ChatColor.DARK_GREEN + targetlevel );                     
      return true;      
    } else if(Bukkit.getPlayer(args[0]) == null) {
      sender.sendMessage(ChatColor.DARK_GREEN + "'" + args[0] + "'" + ChatColor.WHITE + " is not online.");
      return true;             
    }
    return true;
  }

}