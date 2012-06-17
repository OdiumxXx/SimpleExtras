package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ignite implements CommandExecutor {   

  public SimpleExtras plugin;
  public ignite(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {        
      player.setFireTicks(10000);
      sender.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + ChatColor.GRAY + " ignited");
      return true;
    } else {
      Player target = plugin.getServer().getPlayer(args[0]);
      if (target == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      } else {
        target.setFireTicks(10000);
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " ignited");
        return true;
      }
    }
   
  }

}