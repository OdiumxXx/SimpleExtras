package me.odium.simpleextras.commands.effects;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class effects implements CommandExecutor {   

  public SimpleExtras plugin;
  public effects(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }
    sender.sendMessage(ChatColor.GOLD + "[ "+ChatColor.WHITE+ "Player Effects"+ChatColor.GOLD+" ]");
    if(player == null || player.hasPermission("simpleextras.ignite")) {
      sender.sendMessage(ChatColor.RED + "  /ignite <playername> " + ChatColor.WHITE + "- Ignite a player");    
    }        
    if(player == null || player.hasPermission("simpleextras.blind")) {
      sender.sendMessage(ChatColor.RED + "  /blind <playername> <minutes> " + ChatColor.WHITE + "- Blind a player");    
    }
    if(player == null || player.hasPermission("simpleextras.slow")) {
      sender.sendMessage(ChatColor.RED + "  /slow <playername> <minutes> " + ChatColor.WHITE + "- Slow a player");    
    }
    if(player == null || player.hasPermission("simpleextras.slowdig")) {
      sender.sendMessage(ChatColor.RED + "  /slowdig <playername> <minutes> " + ChatColor.WHITE + "- Slow a player's digging");    
    }
    if(player == null || player.hasPermission("simpleextras.confuse")) {
      sender.sendMessage(ChatColor.RED + "  /confuse <playername> <minutes> " + ChatColor.WHITE + "- Confuse a player");    
    }
    if(player == null || player.hasPermission("simpleextras.fly")) {
      sender.sendMessage(ChatColor.GREEN + "  /fly [playername] [minutes] " + ChatColor.WHITE + "- Toggle flight");    
    }
    if(player == null || player.hasPermission("simpleextras.speed")) {
      sender.sendMessage(ChatColor.GREEN + "  /speed <playername> <minutes> " + ChatColor.WHITE + "- Give player Speed x2");    
    }        
    if(player == null || player.hasPermission("simpleextras.jump")) {
      sender.sendMessage(ChatColor.GREEN + "  /jump <playername> <minutes> " + ChatColor.WHITE + "- Give player Jump x2");    
    }
    if(player == null || player.hasPermission("simpleextras.superdig")) {
      sender.sendMessage(ChatColor.GREEN + "  /superdig <playername> <minutes> " + ChatColor.WHITE + "- Give player SuperDig x2");    
    }
    if(player == null || player.hasPermission("simpleextras.fireresist")) {
      sender.sendMessage(ChatColor.GREEN + "  /fireresist <playername> <minutes> " + ChatColor.WHITE + "- Give player FireResist");    
    }
 
    return true;    
  }

}