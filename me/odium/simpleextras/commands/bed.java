package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class bed implements CommandExecutor {   

  public SimpleExtras plugin;
  public bed(SimpleExtras plugin)  {
    this.plugin = plugin;
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
          Location bedloc = player.getBedSpawnLocation();
          if (bedloc != null) {
            player.teleport(bedloc);
            player.sendMessage(ChatColor.WHITE + "You have been returned to your bed");
            return true;  
          } else {
            player.sendMessage(ChatColor.WHITE + "You have not yet slept in a bed");
            return true;
          }

        } 
      } else if(args.length == 1) {
        if (player == null) {
          sender.sendMessage("This command can only be run by a player");
        } else {
          String target = plugin.myGetPlayerName(args[0]);
          OfflinePlayer targetplay = Bukkit.getOfflinePlayer(target);
          Location bedloc = targetplay.getBedSpawnLocation();
          if (bedloc != null) {
            player.teleport(bedloc);
            player.sendMessage(ChatColor.DARK_GREEN + target + ChatColor.WHITE+" 's bed");
            return true;  
          } else {
            player.sendMessage(ChatColor.DARK_GREEN + target + ChatColor.WHITE+" has not yet slept in a bed");
            return true;
          }
        }
      }    


    return true;    
  }

}