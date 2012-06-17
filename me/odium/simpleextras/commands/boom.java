package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class boom implements CommandExecutor {   

  private static float explosionPower = 0;

  public SimpleExtras plugin;
  public boom(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if(args.length == 0) {
      if (player == null) {
        sender.sendMessage("This command can only be run by a player");
      } else {
        Player target = (Player)sender;
        float explosionPower = 0;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);
        player.sendMessage(ChatColor.RED + "Boom!");
        return true;
      }     
    } else if(args.length == 1) {      
      if(args[0] == "-d") {
        Player target = (Player)sender;
        float explosionPower = 3;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);
        player.sendMessage(ChatColor.RED + "Boom!");
        return true;
      } else if(args[0] == "-n") {
        Player target = (Player)sender;
        float explosionPower = 15;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);
        player.sendMessage(ChatColor.RED + "Boom!");
        return true;
      } else if(Bukkit.getPlayer(args[0]) != null) {              
        Player target = Bukkit.getPlayer(args[0]);
        float explosionPower = 0;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);
        if (player == null) {
          target.sendMessage(ChatColor.RED + "Boom! " + ChatColor.WHITE + "Courtesy of: Console"); // error          
          sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " Has been peacefully exploded");  
        } else {
          target.sendMessage(ChatColor.RED + "Boom! " + ChatColor.WHITE + "Courtesy of: " + player.getDisplayName()); // error          
          sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " Has been peacefully exploded");
          return true;
        }
      } else if(Bukkit.getPlayer(args[0]) == null) {                  
        sender.sendMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.WHITE+" is not online.");
        return true;
      }
    } else if(args.length == 2 && args[0].equals("-d")) {      
      if(Bukkit.getPlayer(args[1]) != null) {
        Player target = Bukkit.getPlayer(args[1]);
        explosionPower = 3;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);      
        target.sendMessage(ChatColor.RED+"Boom!"+ChatColor.WHITE+" Courtesy of: " + player.getDisplayName());
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been exploded");
        return true;
      } else if(Bukkit.getPlayer(args[1]) == null) {           
        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
        return true;
      }
    } else if(args.length == 2 && args[0].equals("-s")) {      
      if(Bukkit.getPlayer(args[1]) != null) {
        Player target = Bukkit.getPlayer(args[1]);
        explosionPower = 3;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);          
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been silently exploded");       
        return true;
      } else if(Bukkit.getPlayer(args[1]) == null) {           
        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
        return true;
      }
    } else if(args.length == 2 && args[0].equals("-n")) {      
      if(Bukkit.getPlayer(args[1]) != null) {
        Player target = Bukkit.getPlayer(args[1]);
        explosionPower = 17;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);  
        if (player == null) {
          target.sendMessage(ChatColor.RED+"Boom!"+ChatColor.WHITE+" Courtesy of: Console");
          sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been Nuked");  
          return true;
        } else {
        target.sendMessage(ChatColor.RED+"Boom!"+ChatColor.WHITE+" Courtesy of: " + player.getDisplayName());
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been Nuked");  
        return true;
        }
      } else if(Bukkit.getPlayer(args[1]) == null) {           
        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
        return true;
      }
    } else if(args.length == 2 && args[0].equals("-ns")) {      
      if(Bukkit.getPlayer(args[1]) != null) {
        Player target = Bukkit.getPlayer(args[1]);
        explosionPower = 17;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);
        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been Nuked");  
        return true;
      } else if(Bukkit.getPlayer(args[1]) == null) {           
        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
        return true;
      }
    }
    return true;
  }
}