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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class home implements CommandExecutor {   

  public SimpleExtras plugin;
  public home(SimpleExtras plugin)  {
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

    int fwarp = plugin.getConfig().getInt("HomeEffectDuration");
    if (fwarp == 0) {
      fwarp = 1;
    }
    final int finishHome =  fwarp * 20;

    if (args.length == 0) {
      String World_Name = player.getWorld().getName();
      String loc = plugin.getHomesConfig().getString(player.getName().toLowerCase()+"."+World_Name);
      if (loc == null) {
        sender.sendMessage(ChatColor.RED+" You do not have a home location saved for "+ChatColor.RESET+World_Name);
        return true;                
      } else {
        // compile location
        String[] vals = loc.split(",");
        World world = Bukkit.getWorld(vals[0]);
        double x = Double.parseDouble(vals[1]);        
        double y = Double.parseDouble(vals[2]);
        double z = Double.parseDouble(vals[3]);
        final float f = Float.parseFloat(vals[4]);
        final float p = Float.parseFloat(vals[5]);
        final Location locc = new Location(world, x, y+1, z, f, p);


        if (player.getAllowFlight() == true) { // if player is allowed to fly
          final Player playertimer = player; 
          player.setFlying(true);
          playertimer.setNoDamageTicks(60 + finishHome);
          player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {  // TELEPORT AFTER 1 TICK
            public void run() { 
              playertimer.teleport(locc);
              playertimer.setFlying(true);
            }
          }, 15L);

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {  // FINISH WARP
            public void run() {                     
              playertimer.setFallDistance(0);
              playertimer.teleport(locc);
              playertimer.removePotionEffect(PotionEffectType.CONFUSION);
              playertimer.setFlying(false);
              playertimer.sendMessage(ChatColor.YELLOW + " You have been returned to your home");
            }
          }, finishHome); 
          return true;  

        } else if (player.getAllowFlight() == false) { // if player is NOT allowed to fly
          final Player playertimer = player;
          player.setAllowFlight(true);
          player.setFlying(true);            
          playertimer.setNoDamageTicks(60 + finishHome);
          player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() { // TELEPORT AFTER 1 TICK
            public void run() { 
              playertimer.teleport(locc);
              playertimer.setFlying(true);
            }
          }, 15L);   

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {   // FINISH WARP
            public void run() {
              playertimer.setFallDistance(0);
              playertimer.teleport(locc);                        
              playertimer.setFlying(false);
              playertimer.setAllowFlight(false);                        
              playertimer.removePotionEffect(PotionEffectType.CONFUSION);
              playertimer.sendMessage(ChatColor.YELLOW + " You have been returned to your home");
            }
          }, finishHome); 
          return true;
        }
      }

    } else if (args.length == 1 && player.hasPermission("simpleextras.home.other")) {

      final String targetname = plugin.myGetPlayerName(args[0]);
      String World_Name = player.getWorld().getName();
      String loc = plugin.getHomesConfig().getString(targetname.toLowerCase()+"."+World_Name);
      if (loc == null) {
        sender.sendMessage(args[0]+ChatColor.YELLOW+" does not have a home location");
        return true;                
      } else {
        // compile location
        String[] vals = loc.split(",");
        World world = Bukkit.getWorld(vals[0]);
        double x = Double.parseDouble(vals[1]);        
        double y = Double.parseDouble(vals[2]);
        double z = Double.parseDouble(vals[3]);
        final float f = Float.parseFloat(vals[4]);
        final float p = Float.parseFloat(vals[5]);
        final Location locc = new Location(world, x, y+1, z, f, p);


        if (player.getAllowFlight() == true) { // if player is allowed to fly
          final Player playertimer = player; 
          player.setFlying(true);           
          playertimer.setNoDamageTicks(40 + finishHome);
          player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {  // TELEPORT AFTER 1 TICK
            public void run() { 
              playertimer.teleport(locc);
              playertimer.setFlying(true);
            }
          }, 15L);

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {  // FINISH WARP
            public void run() {
              playertimer.setFlying(false);  
              playertimer.removePotionEffect(PotionEffectType.CONFUSION);
              playertimer.teleport(locc);
              playertimer.sendMessage(ChatColor.YELLOW + " You have been warped to "+targetname+"'s home");
            }
          }, finishHome); 
          return true;  

        } else if (player.getAllowFlight() == false) { // if player is NOT allowed to fly
          final Player playertimer = player;
          player.setAllowFlight(true);
          player.setFlying(true);       
          playertimer.setNoDamageTicks(40 + finishHome);
          player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() { // TELEPORT AFTER 1 TICK
            public void run() { 
              playertimer.teleport(locc);
              playertimer.setFlying(true);
            }
          }, 15L);   

          plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {   // FINISH WARP
            public void run() {

              playertimer.setFallDistance(0);
              playertimer.teleport(locc);
              playertimer.setFlying(false);
              playertimer.setAllowFlight(false);                        
              playertimer.removePotionEffect(PotionEffectType.CONFUSION);
              playertimer.sendMessage(ChatColor.YELLOW + " You have been warped to "+targetname+"'s home");
            }
          }, finishHome); 
          return true;
        }

      }

    } else if (args.length == 1 && !player.hasPermission("simpleextras.home.other")) {
      sender.sendMessage(ChatColor.RED+"You do not have permission");
      return true;
    } 

    return true;
  }

}