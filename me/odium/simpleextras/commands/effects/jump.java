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

public class jump implements CommandExecutor {   

  public SimpleExtras plugin;
  public jump(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    if (args.length == 0) {
      
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      
      player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2));
      sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "You have been given jump x2 for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
      return true;
    } else if (args.length == 1) {
      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      }
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has been given jump x2 for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "You have been given jump x2 for " + ChatColor.GREEN + "1" + ChatColor.WHITE + " minute");
        return true;
      
    } else if (args.length == 2 && player == null ||player.hasPermission("simpleextras.jump.other")) {  
      Player target = Bukkit.getPlayer(args[0]);
      // CHECK IF TARGET EXISTS
      if (target == null) {
        sender.sendMessage(ChatColor.RED + args[0] + " is not online");
        return true;
      }
      String min = args[1];
      int mintemp = Integer.parseInt( min );
      int mins = 1200 * mintemp;        
      target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, mins, 2));
      sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.WHITE + " has been given jump x2 for " + ChatColor.GREEN + min + ChatColor.WHITE + " minutes");
      target.sendMessage(ChatColor.GOLD + "* " + ChatColor.WHITE + "You have been given jump x2 for " + ChatColor.GREEN + min + ChatColor.WHITE + " minutes");
      return true;
    } 

    return true;    
  }

}