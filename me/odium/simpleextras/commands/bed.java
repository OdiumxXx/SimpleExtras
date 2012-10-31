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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

                if (player.getBedSpawnLocation() != null) { // if bed location exists
                    final Location bedloc = player.getBedSpawnLocation().add(0, 1, 0);

                    if (player.getAllowFlight() == true) { // if player is allowed to fly
                        final Player playertimer = player; 
                        player.setFlying(true);
                        playertimer.setNoDamageTicks(80);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {  // TELEPORT AFTER 1 TICK
                            public void run() { 
                                playertimer.teleport(bedloc);
                            }
                        }, 20L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {  // FINISH WARP
                            public void run() {
                                playertimer.setFallDistance(0);
                                playertimer.teleport(bedloc);
                                playertimer.setFlying(false);  
                                playertimer.removePotionEffect(PotionEffectType.CONFUSION);
                                playertimer.sendMessage(ChatColor.YELLOW + " You have been returned to your bed");
                            }
                        }, 30L); 
                        return true;  

                    } else if (player.getAllowFlight() == false) { // if player is NOT allowed to fly
                        final Player playertimer = player;
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        playertimer.setNoDamageTicks(80);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { // TELEPORT AFTER 1 TICK
                            public void run() { 
                                playertimer.teleport(bedloc);
                            }
                        }, 20L);   

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {   // FINISH WARP
                            public void run() {
                                playertimer.setFallDistance(0);
                                playertimer.teleport(bedloc);
                                playertimer.setFlying(false);
                                playertimer.setAllowFlight(false);
                                playertimer.removePotionEffect(PotionEffectType.CONFUSION);
                                playertimer.sendMessage(ChatColor.YELLOW + " You have been returned to your bed");
                            }
                        }, 30L); 
                        return true;
                    }

                } else { // OTHERWISE, IF BED DID NOT EXIST
                    player.sendMessage(ChatColor.YELLOW + " You have not yet slept in a bed");
                    return true;
                }

            } 
        } else if(args.length == 1 && player.hasPermission("simpleextras.bed.other")) { // IF LOOKING FOR THE BED OF ANOTHER USER

                final String target = plugin.myGetPlayerName(args[0]);
                OfflinePlayer targetplay = Bukkit.getOfflinePlayer(target);                

                if (targetplay.hasPlayedBefore() && targetplay.getBedSpawnLocation() != null) { // if bed location exists
                    final Location bedloc = targetplay.getBedSpawnLocation().add(0, 1, 0);

                    if (player.getAllowFlight() == true) { // if player is allowed to fly
                        final Player playertimer = player;
                        player.setFlying(true);
                        playertimer.setNoDamageTicks(80);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { // TELEPORT AFTER 1 TICK
                            public void run() { 
                                playertimer.teleport(bedloc);
                            }
                        }, 20L);                         

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { // FINISH WARP
                            public void run() {                
                                playertimer.setFallDistance(0);
                                playertimer.teleport(bedloc);                  
                                playertimer.setFlying(false);                
                                playertimer.removePotionEffect(PotionEffectType.CONFUSION);
                                playertimer.sendMessage(ChatColor.YELLOW+" "+target + ChatColor.WHITE+" 's bed");
                            }
                        }, 30L); 
                        return true;

                    } else if (player.getAllowFlight() == false) { // if player is NOT allowed it fly
                        final Player playertimer = player;
                        player.setAllowFlight(true);
                        player.setFlying(true); 
                        playertimer.setNoDamageTicks(80);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 10)); // BEGIN WARP

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { // TELEPORT AFTER 1 TICK
                            public void run() { 
                                playertimer.teleport(bedloc);
                            }
                        }, 20L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { // FINISH WARP
                            public void run() {                
                                playertimer.setFallDistance(0);
                                playertimer.teleport(bedloc);
                                playertimer.setFlying(false);
                                playertimer.setAllowFlight(false);
                                playertimer.removePotionEffect(PotionEffectType.CONFUSION);                
                                playertimer.sendMessage(ChatColor.YELLOW+" "+target + ChatColor.WHITE+" 's bed");
                            }
                        }, 30L); 
                        return true;
                    }
                } else { // if bed location does NOT exist
                    player.sendMessage(ChatColor.WHITE+" "+target + ChatColor.YELLOW+" has not yet slept in a bed");
                    return true;
                }
            
        } else if(args.length == 1 && !player.hasPermission("simpleextras.bed.other")) {
          sender.sendMessage(ChatColor.RED+"You do not have permission.");
          return true;
        }


        return true;    
    }

}