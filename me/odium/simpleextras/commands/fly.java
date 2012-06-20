package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly implements CommandExecutor {   

  public SimpleExtras plugin;
  public fly(SimpleExtras plugin)  {
    plugin.plugin = plugin;
  }

  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if(args.length == 0) {
      Boolean canfly = player.getAllowFlight();
      if(canfly == true) {
        player.setAllowFlight(false);
        player.setFlying(false);
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight " + ChatColor.RED +  "disabled ");
        return true;
      } else if(canfly == false) {
        player.setAllowFlight(true);
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GREEN + "Flight " + ChatColor.GREEN +  "enabled ");
        return true;
      }
    } else if(args.length == 1 && player == null ||args.length == 1 && player.hasPermission("simpleextras.fly.other")) {
      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      } else {
        Boolean canfly = target.getAllowFlight();
        String targetname = target.getDisplayName();
        if(canfly == true) {
          target.setAllowFlight(false);          
          target.setFlying(false);
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.RED +  "disabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN + targetname);
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.RED+ "disabled");
          return true;
        } else if(canfly == false) {
          target.setAllowFlight(true);
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN +  targetname);
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled");
          return true;
        }
      }
    } else if(args.length == 2 && player == null || args.length == 2 && player.hasPermission("simpleextras.fly.other")) {
      final Player target1 = Bukkit.getPlayer(args[0]);
      if (target1 == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      } else {
//        final Player player1 = player;
        Boolean canfly = target1.getAllowFlight();
        String targetname = target1.getDisplayName();
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;  
        if(canfly == true) {
          sender.sendMessage("Already allowed to fly, timer set anyway.");        

          Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {
            public void run() {
              target1.setAllowFlight(false);          
              target1.setFlying(false);
              sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.RED +  "disabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN + target1.getDisplayName());
            }
          }, mins);          
          return true;

        } else if(canfly == false) {
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN +  targetname + ChatColor.WHITE + " for " + min + " minutes");
          target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN + min + ChatColor.WHITE + " minutes");
          target1.setAllowFlight(true);          
          target1.setFlying(true);

          Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {
            public void run() {       
              target1.setAllowFlight(false);          
              target1.setFlying(false);
              String targetname = target1.getDisplayName();
              target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight " + ChatColor.RED +  "disabled ");
              sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.RED +  "disabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN + targetname);
            }
          }, mins);
          return true;

        }
      }
    }

    return true;    
  }

}