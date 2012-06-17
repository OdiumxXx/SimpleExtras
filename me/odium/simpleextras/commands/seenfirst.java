package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class seenfirst implements CommandExecutor {   

  public SimpleExtras plugin;
  public seenfirst(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }
        
      if (args.length == 0) {
        long firstseen = player.getFirstPlayed();
        @SuppressWarnings("static-access")
        String strDte = plugin.getCurrentDTG(firstseen);
        sender.sendMessage(ChatColor.GRAY + "You first logged in: " + ChatColor.BLUE + strDte);
        return true;
      }
      if (args.length == 1) {
        Player targeton = this.plugin.getServer().getPlayer(args[0]);
        if (targeton == null) {
          OfflinePlayer target = this.plugin.getServer().getOfflinePlayer(args[0]);
          long firstseen = target.getFirstPlayed();
          if(firstseen == 0) {
            sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " has not been seen!");
            sender.sendMessage(ChatColor.GOLD + "(Must use exact username)");
            return true;             
          } else {            
            @SuppressWarnings("static-access")
            String strDte = plugin.getFriendly(firstseen);
            sender.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " first logged in: " + ChatColor.BLUE + strDte);
            return true;
          }           
        } else {
          long firstseen = targeton.getFirstPlayed();
          @SuppressWarnings("static-access")
          String strDte = plugin.getCurrentDTG(firstseen);
          // String strDte = getFriendly(firstseen);          
          sender.sendMessage(ChatColor.BLUE + targeton.getName() + ChatColor.GRAY + " first logged in: " + ChatColor.BLUE + strDte);
          return true;
        }
      }    


    return true;    
  }

}