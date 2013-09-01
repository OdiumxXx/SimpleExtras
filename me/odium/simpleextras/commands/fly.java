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
    this.plugin = plugin;
  }

  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if(args.length == 0) {
      
      if (player == null) {
        sender.sendMessage("This command can only be run by a player");
        return true;
      } 
      
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
          if (sender != target) {
            target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.RED+ "disabled");  
          }          
          return true;
        } else if(canfly == false) {
          target.setAllowFlight(true);
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN +  targetname);
          if (sender != target) {
            target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled");  
          }         
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
        int warningminstemp = 1200 * mintemp;
        int warningmins = warningminstemp - 200;
        if(canfly == true) {
          sender.sendMessage("Already allowed to fly, timer set anyway.");       
          
          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {
            public void run() {       
              target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight is about to " + ChatColor.RED +  "expire! ");              
            }
          }, warningmins); 

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {
            public void run() {
              target1.setAllowFlight(false);          
              target1.setFlying(false);
              sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.RED +  "disabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN + target1.getDisplayName());
              if (sender != target1) {
                target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight " + ChatColor.RED +  "disabled ");  
              }
                            
            }
          }, mins);          
          return true;

        } else if(canfly == false) {
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN +  targetname + ChatColor.WHITE + " for " + min + " minutes");
          if (sender != target1) {
            target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN + min + ChatColor.WHITE + " minutes");  
          }          
          target1.setAllowFlight(true);          
          target1.setFlying(true);

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {
            public void run() {       
              target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight is about to " + ChatColor.RED +  "expire! ");              
            }
          }, warningmins);        

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {
            public void run() {       
              target1.setAllowFlight(false);          
              target1.setFlying(false);
              String targetname = target1.getDisplayName();
              target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight " + ChatColor.RED +  "disabled ");     
              if (sender != target1) {
              sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "Flight " + ChatColor.RED +  "disabled " + ChatColor.WHITE + "for " + ChatColor.DARK_GREEN + targetname);  
              }

            }
          }, mins);
          return true;

        }
      }
    }

    return true;    
  }

}