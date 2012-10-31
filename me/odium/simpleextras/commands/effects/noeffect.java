package me.odium.simpleextras.commands.effects;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class noeffect implements CommandExecutor {   

  public SimpleExtras plugin;
  public noeffect(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }
    if (args.length == 0) {
      player.removePotionEffect(PotionEffectType.BLINDNESS);
      player.removePotionEffect(PotionEffectType.CONFUSION);
      player.removePotionEffect(PotionEffectType.SLOW);
      player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
      player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
      player.removePotionEffect(PotionEffectType.JUMP);
      player.removePotionEffect(PotionEffectType.SPEED);
      player.removePotionEffect(PotionEffectType.FAST_DIGGING);
      player.removePotionEffect(PotionEffectType.WATER_BREATHING);  
      sender.sendMessage(ChatColor.GREEN+"Effects Removed");
      return true;
    } else if (args.length == 1) {
      if (Bukkit.getPlayer(args[0]) == null) {
        sender.sendMessage("Player "+args[0]+" is not online.");
        return true;
      }
      Player target = Bukkit.getPlayer(args[0]);
      target.removePotionEffect(PotionEffectType.BLINDNESS);
      target.removePotionEffect(PotionEffectType.CONFUSION);
      target.removePotionEffect(PotionEffectType.SLOW);
      target.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
      target.removePotionEffect(PotionEffectType.SLOW_DIGGING);
      target.removePotionEffect(PotionEffectType.JUMP);
      target.removePotionEffect(PotionEffectType.SPEED);
      target.removePotionEffect(PotionEffectType.FAST_DIGGING);
      player.removePotionEffect(PotionEffectType.WATER_BREATHING);
      sender.sendMessage(ChatColor.GREEN+"Effects Removed from "+target.getDisplayName());
      return true;
    }
    return true;
  }
}
