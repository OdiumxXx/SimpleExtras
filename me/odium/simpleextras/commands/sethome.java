package me.odium.simpleextras.commands;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sethome implements CommandExecutor {   

  public SimpleExtras plugin;
  public sethome(SimpleExtras plugin)  {
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
    
    // SETTING HOME FOR SELF
    if (args.length == 0) {
      // GET LOCATION DATA
      World PlayerWorld = player.getWorld();
      String PlayerWorldName = PlayerWorld.getName().toLowerCase();
      double locX = player.getLocation().getX();
      double locY = player.getLocation().getY();
      double locZ = player.getLocation().getZ();
      float locF = player.getLocation().getYaw();
      float locP = player.getLocation().getPitch();
      // SAVE THE LOCATION DATA TO STRING
      StringBuilder sb1 = new StringBuilder(); 
      sb1.append(PlayerWorldName+",");
      sb1.append(locX+",");
      sb1.append(locY+",");
      sb1.append(locZ+",");
      sb1.append(locF+",");
      sb1.append(locP);
      String PlayerLocation = sb1.toString();        

      plugin.getHomesConfig().set(player.getName().toLowerCase()+".location", PlayerLocation);
      player.getWorld().playEffect(player.getLocation(), Effect.POTION_BREAK, 16453);
      plugin.saveHomesConfig();
      sender.sendMessage(ChatColor.YELLOW+" Home Location Set");              
      return true;
      // IF SETTING HOME FOR OTHER PLAYER
    } else if (args.length == 1 && player.hasPermission("simpleextras.sethome.other")) {
      // GET LOCATION DATA
      World PlayerWorld = player.getWorld(); 
      String PlayerWorldName = PlayerWorld.getName().toLowerCase();
      double locX = player.getLocation().getX();
      double locY = player.getLocation().getY();
      double locZ = player.getLocation().getZ();
      float locF = player.getLocation().getYaw();
      float locP = player.getLocation().getPitch();
      // SAVE THE LOCATION DATA TO STRING
      StringBuilder sb1 = new StringBuilder(); 
      sb1.append(PlayerWorldName+",");
      sb1.append(locX+",");
      sb1.append(locY+",");
      sb1.append(locZ+",");
      sb1.append(locF+",");
      sb1.append(locP);
      String PlayerLocation = sb1.toString();        

      plugin.getHomesConfig().set(args[0].toLowerCase()+".location", PlayerLocation);
      player.getWorld().playEffect(player.getLocation(), Effect.POTION_BREAK, 16453);
      plugin.saveHomesConfig();
      sender.sendMessage(ChatColor.YELLOW+" Home Location Set for "+ChatColor.WHITE+args[0]);              
      return true;

    }
    return true;
  }
}