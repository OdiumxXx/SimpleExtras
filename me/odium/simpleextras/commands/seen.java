package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class seen implements CommandExecutor {   

  public SimpleExtras plugin;
  public seen(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    

      if (args.length == 0) {
        sender.sendMessage(ChatColor.GOLD + "[ "+ChatColor.WHITE+"Seen"+ChatColor.GOLD+" ]");
        sender.sendMessage(ChatColor.DARK_GREEN + "  /seen PlayerName " + ChatColor.WHITE + "- Return the LAST time player was seen");
        sender.sendMessage(ChatColor.DARK_GREEN + "  /seenf PlayerName " + ChatColor.WHITE + "- Return the FIRST time player was seen");
        return true;
      } else if (args.length == 1) {        
        Player targeton = this.plugin.getServer().getPlayer(args[0]);
        if (targeton != null) {
          sender.sendMessage(ChatColor.DARK_GREEN + targeton.getDisplayName() + ChatColor.WHITE + " is online right now!");
          return true;
        } else {
          OfflinePlayer target = this.plugin.getServer().getOfflinePlayer(args[0]);
          long lastseen = target.getLastPlayed();
          //          int lastseen = (int) target.getLastPlayed() / 1000;
          if(lastseen == 0) {           
            sender.sendMessage(ChatColor.DARK_GREEN + "'" + args[0] + "'" + ChatColor.WHITE + " has not been seen!");
            sender.sendMessage(ChatColor.GOLD + "(Must use exact username)");
            return true;
          } else {
            @SuppressWarnings("static-access")
            String strDte = plugin.getCurrentDTG(lastseen);
            // String strDte = getFriendly(lastseen);
            sender.sendMessage(ChatColor.DARK_GREEN + target.getName() + ChatColor.WHITE + " was last seen: " + ChatColor.DARK_GREEN + strDte);
            return true;            
          }
        }
      }


    return true;    
  }

}