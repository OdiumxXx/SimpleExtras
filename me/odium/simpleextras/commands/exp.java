package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class exp implements CommandExecutor {   

  public SimpleExtras plugin;
  public exp(SimpleExtras plugin)  {
    plugin.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {       
      sender.sendMessage(ChatColor.GOLD + " [ Experience Rewards ] ");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /exp PlayerName # " + ChatColor.WHITE + "- Award a user # of exp points");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /levelup PlayerName # " + ChatColor.WHITE + "- Award a user # of exp levels");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /levelset PlayerName # " + ChatColor.WHITE + "- Set a users exp level");
      sender.sendMessage(ChatColor.DARK_GREEN + "  /levelget PlayerName " + ChatColor.WHITE + "- View a users exp level");
      return true;
    } else if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
      Player target = Bukkit.getPlayer(args[0]);      
      target.giveExp(10);
      sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " has been awarded 10xp ");
      return true;
    } else if (args.length == 1 && Bukkit.getPlayer(args[0]) == null) {
      sender.sendMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.WHITE+" is not online.");
      return true;
    } else if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
      Player target = Bukkit.getPlayer(args[0]);
      int xp = Integer.parseInt(args[1]);
      target.giveExp(xp);
      sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " has been awarded " + ChatColor.DARK_GREEN + xp + "xp");
      if (player == null) {
        target.sendMessage(ChatColor.WHITE + "You've been awarded " + ChatColor.DARK_GREEN + xp + "xp" + ChatColor.WHITE + " by " + ChatColor.DARK_GREEN + "Console");
        return true;          
      } else {
        target.sendMessage(ChatColor.WHITE + "You've been awarded " + ChatColor.DARK_GREEN + xp + "xp" + ChatColor.WHITE + " by " + ChatColor.DARK_GREEN + sender.getName());            
        return true;
      }
    } else if (args.length == 2 && Bukkit.getPlayer(args[0]) == null) {
      sender.sendMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.WHITE+" is not online.");
      return true;
    } else if (args.length > 2) {

    }
    return true;
  }
}
