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

public class fireresistance implements CommandExecutor {   

  public SimpleExtras plugin;
  public fireresistance(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 3));
      sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "You have been given Fire Resist for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
      return true;
    } else if (args.length == 1) {
      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      } else {
        target.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 3));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has been given Fire Resistance for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "You have been given Fire Resist for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
        return true;
      }
    } else if (args.length == 2 && player == null ||player.hasPermission("simpleextras.fireresist.other")) {  
      Player target = Bukkit.getPlayer(args[0]);
      String min = args[1];
      int mintemp = Integer.parseInt( min );
      int mins = 1200 * mintemp;        
      target.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, mins, 3));
      sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has been given Fire Resistance for " + ChatColor.GREEN + min + ChatColor.WHITE + " minutes");
      target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "You have been given Fire Resist for " + ChatColor.GREEN + min + ChatColor.WHITE + " minutes");
      return true;
    } 

    return true;    
  }

}