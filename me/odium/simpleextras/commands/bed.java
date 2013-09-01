package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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

    if (player == null) {
      sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
      return true;
    }

    if (args.length == 0) {
      String Player_Name = player.getName();
      String worldName = player.getWorld().getName();

      if (plugin.getBedsConfig().getString(Player_Name+"."+worldName) != null) {
        String bedLocation = plugin.getBedsConfig().getString(Player_Name+"."+worldName);
        // compile location
        String[] vals = bedLocation.split(",");
        World world = Bukkit.getWorld(vals[0]);
        double x = Double.parseDouble(vals[1]);        
        double y = Double.parseDouble(vals[2]);
        double z = Double.parseDouble(vals[3]);      
        final Location loc = new Location(world, x, y+1, z);      
        player.teleport(loc);
        player.sendMessage(ChatColor.YELLOW+"You have been returned to your bed location for "+ChatColor.RESET+worldName);
        return true;
      } else {
        player.sendMessage(ChatColor.YELLOW+"You do not have a bed location set for "+ChatColor.RESET+worldName);
        return true;
      }
    } else if (args.length == 1 && player.hasPermission("simpleextras.bed.other")) {
      String targetName= plugin.myGetPlayerName(args[0]);
      String worldName = player.getWorld().getName();
      if (plugin.getBedsConfig().getString(targetName+"."+worldName) != null) {
        String bedLocation = plugin.getBedsConfig().getString(targetName+"."+worldName);
        // compile location
        String[] vals = bedLocation.split(",");
        World world = Bukkit.getWorld(vals[0]);
        double x = Double.parseDouble(vals[1]);        
        double y = Double.parseDouble(vals[2]);
        double z = Double.parseDouble(vals[3]);      
        final Location loc = new Location(world, x, y+1, z);      
        player.teleport(loc);
        player.sendMessage(ChatColor.YELLOW+"You have been returned to "+targetName+"'s bed location for "+ChatColor.RESET+worldName);
        return true;
      } else {
        player.sendMessage(ChatColor.YELLOW+targetName+" does not have a bed location set for "+ChatColor.RESET+worldName);
        return true;
      }
    }
    return true;
  }
}





//      if (player.getBedSpawnLocation() != null) { // if bed location exists
//          final Location bedloc = player.getBedSpawnLocation().add(0, 1, 0);
//
//          if (player.getAllowFlight() == true) { // if player is allowed to fly
//            final Player playertimer = player; 
//            player.setFlying(true);
//            playertimer.setNoDamageTicks(80);
//            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP
//
//            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {  // TELEPORT AFTER 1 TICK
//              public void run() { 
//                playertimer.teleport(bedloc);
//                playertimer.setFlying(true); 
//              }
//            }, 20L);
//
//            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {  // FINISH WARP
//              public void run() {
//                playertimer.setFallDistance(0);
//                playertimer.teleport(bedloc);
//                playertimer.setFlying(false);  
//                playertimer.removePotionEffect(PotionEffectType.CONFUSION);
//                playertimer.sendMessage(ChatColor.YELLOW + " You have been returned to your bed");
//              }
//            }, finishHome); 
//            return true;  
//
//          } else if (player.getAllowFlight() == false) { // if player is NOT allowed to fly
//            final Player playertimer = player;
//            player.setAllowFlight(true);
//            player.setFlying(true);
//            playertimer.setNoDamageTicks(80);
//            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP
//
//            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() { // TELEPORT AFTER 1 TICK
//              public void run() { 
//                playertimer.teleport(bedloc);
//                playertimer.setFlying(true);
//              }
//            }, 20L);   
//
//            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {   // FINISH WARP
//              public void run() {
//                playertimer.setFallDistance(0);
//                playertimer.teleport(bedloc);
//                playertimer.setFlying(false);
//                playertimer.setAllowFlight(false);
//                playertimer.removePotionEffect(PotionEffectType.CONFUSION);
//                playertimer.sendMessage(ChatColor.YELLOW + " You have been returned to your bed");
//              }
//            }, finishHome); 
//            return true;
//          }
//
//        } else { // OTHERWISE, IF BED DID NOT EXIST
//          player.sendMessage(ChatColor.YELLOW + " You have not yet slept in a bed");
//          return true;
//        }
//      
//    } else if(args.length == 1 && player.hasPermission("simpleextras.bed.other")) { // IF LOOKING FOR THE BED OF ANOTHER USER
//      
//      final String target = plugin.myGetPlayerName(args[0]);
//      OfflinePlayer targetplay = Bukkit.getOfflinePlayer(target);                
//
//      if (targetplay.hasPlayedBefore() && targetplay.getBedSpawnLocation() != null) { // if bed location exists
//        final Location bedloc = targetplay.getBedSpawnLocation().add(0, 1, 0);
//
//        if (player.getAllowFlight() == true) { // if player is allowed to fly
//          final Player playertimer = player;
//          player.setFlying(true);
//          playertimer.setNoDamageTicks(80);
//          player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP
//
//          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() { // TELEPORT AFTER 1 TICK
//            public void run() { 
//              playertimer.teleport(bedloc);
//              playertimer.setFlying(true);
//            }
//          }, 20L);                         
//
//          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() { // FINISH WARP
//            public void run() {                
//              playertimer.setFallDistance(0);
//              playertimer.teleport(bedloc);                  
//              playertimer.setFlying(false);                
//              playertimer.removePotionEffect(PotionEffectType.CONFUSION);
//              playertimer.sendMessage(ChatColor.YELLOW+" "+target + ChatColor.WHITE+" 's bed");
//            }
//          }, finishHome); 
//          return true;
//
//        } else if (player.getAllowFlight() == false) { // if player is NOT allowed it fly
//          final Player playertimer = player;
//          player.setAllowFlight(true);
//          player.setFlying(true); 
//          playertimer.setNoDamageTicks(80);
//          player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP
//
//          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() { // TELEPORT AFTER 1 TICK
//            public void run() { 
//              playertimer.teleport(bedloc);
//              playertimer.setFlying(true);
//            }
//          }, 20L);
//
//          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() { // FINISH WARP
//            public void run() {                
//              playertimer.setFallDistance(0);
//              playertimer.teleport(bedloc);
//              playertimer.setFlying(false);
//              playertimer.setAllowFlight(false);
//              playertimer.removePotionEffect(PotionEffectType.CONFUSION);                
//              playertimer.sendMessage(ChatColor.YELLOW+" "+target + ChatColor.WHITE+" 's bed");
//            }
//          }, finishHome); 
//          return true;
//        }
//      } else { // if bed location does NOT exist
//        player.sendMessage(ChatColor.WHITE+" "+target + ChatColor.YELLOW+" has not yet slept in a bed");
//        return true;
//      }
//
//    } else if(args.length == 1 && !player.hasPermission("simpleextras.bed.other")) {
//      sender.sendMessage(ChatColor.RED+"You do not have permission.");
//      return true;
//    }
//
//
//    return true;    
//  }
//
//}