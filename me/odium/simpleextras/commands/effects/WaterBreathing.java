package me.odium.simpleextras.commands.effects;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WaterBreathing implements CommandExecutor {   

  public SimpleExtras plugin;
  public WaterBreathing(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }
    // ON SELF
    if (args.length == 0) {
      
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      
      // CHECK FOR EXISTING EFFECT
      if (player.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
        player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.WHITE + "You have lost "+ChatColor.GREEN+"Water Breathing");
        return true;
      }
      // OTHERWISE ADD EFFECT
      player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1200, 1));
      sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.WHITE + "You have Water Breathing for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
      return true;
      // ON OTHER WITH NO TIMEFRAME
    } else if (args.length == 1) {
      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      }
   // CHECK FOR EXIsTING EFFECT
        if (target.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
          target.removePotionEffect(PotionEffectType.WATER_BREATHING);
          sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has lost Water Breathing");
          target.sendMessage(ChatColor.GREEN + "* " + ChatColor.WHITE + "You have lost "+ChatColor.GREEN+"Water Breathing");
          return true;
        }
        // OTHERWISE ADD EFFECT
        target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1200, 1));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has Water Breathing for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
        target.sendMessage(ChatColor.GREEN + "* " + ChatColor.WHITE + "You have Water Breathing for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
        return true;
      
    } else if (args.length == 2 && player == null || player.hasPermission("simpleextras.waterbreathing.other")) {  
      Player target = Bukkit.getPlayer(args[0]);
      // CHECK IF TARGET EXISTS
      if (target == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      }
      String min = args[1];
      int mintemp = Integer.parseInt( min );
      int mins = 1200 * mintemp; 
      if (target.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
        target.removePotionEffect(PotionEffectType.WATER_BREATHING);
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has lost Water Breathing");
        target.sendMessage(ChatColor.GREEN + "* " + ChatColor.WHITE + "You have lost "+ChatColor.GREEN+"Water Breathing");
        return true;
      }
      target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, mins, 1));
      sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has Water Breathing for " + ChatColor.GREEN + min + ChatColor.WHITE + " minutes");
      target.sendMessage(ChatColor.GREEN + "* " + ChatColor.WHITE + "You have Water Breathing for " + ChatColor.GREEN + min + ChatColor.WHITE + " minutes");
      return true;
    } 

    return true;    
  }
}
