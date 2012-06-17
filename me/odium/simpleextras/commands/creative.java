package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class creative implements CommandExecutor {   

  public SimpleExtras plugin;
  public creative(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {
      int gm = player.getGameMode().getValue();
      if(gm == 0){
        player.setGameMode(GameMode.CREATIVE);
        plugin.log.info(player + "Changed gamemode to CREATIVE");
        sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.WHITE + "Your Gamemode has been changed to " + ChatColor.DARK_GREEN + "Creative");
        return true;
      } else {
        sender.sendMessage(ChatColor.RED + "* " + ChatColor.WHITE + "Your Gamemode is already set to " + ChatColor.DARK_GREEN + "Creative");
        return true;
      }
    } else if(args.length == 1 && player == null || args.length == 1 && player.hasPermission("simpleextras.creative.other")) {
      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
        sender.sendMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.WHITE+" is not online.");
        return true;
      } else {
        int gm = target.getGameMode().getValue();
        if(gm == 0){
          target.setGameMode(GameMode.CREATIVE);
          plugin.log.info(player + "Changed gamemode to CREATIVE");
          sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + "'s Gamemode has been changed to " + ChatColor.DARK_GREEN + "Creative");
          return true;
        } else {
          sender.sendMessage(ChatColor.RED + "* " + ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + "'s Gamemode is already set to " + ChatColor.DARK_GREEN + "Creative");
          return true; 
        }
      }
    }

    return true;    
  }

}