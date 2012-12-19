package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class colours implements CommandExecutor {   

  public SimpleExtras plugin;
  public colours(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    sender.sendMessage(ChatColor.YELLOW+"Colour Codes:");
    sender.sendMessage(ChatColor.BLACK+"0 "+ChatColor.DARK_BLUE+"1 "+ChatColor.DARK_GREEN+"2 "+ChatColor.DARK_AQUA+"3 "+ChatColor.DARK_RED+"4 "+ChatColor.DARK_PURPLE+"5 "+ChatColor.GOLD+"6 "+ChatColor.GRAY+"7 "+ChatColor.DARK_GRAY+"8 "+ChatColor.BLUE+"9 "+ChatColor.GREEN+"a "+ChatColor.AQUA+"b "+ChatColor.RED+"c "+ChatColor.LIGHT_PURPLE+"d "+ChatColor.YELLOW+"e "+ChatColor.WHITE+"f");
    return true;
  }

}