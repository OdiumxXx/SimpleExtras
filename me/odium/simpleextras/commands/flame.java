package me.odium.simpleextras.commands;

import java.util.HashMap;
import java.util.Map;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flame implements CommandExecutor {   
  Boolean StopFlame;
  public Map<Player, Boolean> flameEnabled = new HashMap<Player, Boolean>();
  public Map<Player, Boolean> stopFlame = new HashMap<Player, Boolean>();
  public Map<Player, Integer> taskID = new HashMap<Player, Integer>();

  public SimpleExtras plugin;
  public flame(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  String FlameStat;

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    final Player me = (Player) sender;

    if (args.length == 0) {
      if(flameEnabled.containsKey(player)) {
        if(flameEnabled.get(player) == true) {
          sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.GREEN+"Enabled");
          return true;
        } else {          
          sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.RED+"Disabled");
          return true;
        }
      } else {
        sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.RED+"Disabled");
        return true;
      }

      // IF SETTING ON SELF
    } else if(args.length == 1) {
      // FLAME ON
      if (args[0].equalsIgnoreCase("on")) {       
        if(flameEnabled.containsKey(me)){          
          if(flameEnabled.get(me) == true){
            sender.sendMessage(ChatColor.GREEN+"Flame Already Enabled");
            return true;
          }
        }
        stopFlame.put(me, false);
        flameEnabled.put(me, true);
        taskID.put(me, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {          
          public void run() {
            me.getWorld().playEffect(me.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);                    
            if(stopFlame.get(me) == true){ Bukkit.getScheduler().cancelTask(taskID.get(me)); }
          }
        }, 7L, 7L));
        sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.GREEN+"On");
        return true;
        // FLAME OFF
      } else if (args[0].equalsIgnoreCase("off")) {
        stopFlame.put(player, true);  
        flameEnabled.put(me, false);
        sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.RED+"Off");

        // FLAME STAT ON ANOTHER USER
      } else if (Bukkit.getPlayer(args[0]) != null) {
        Player target = Bukkit.getPlayer(args[0]);
        if(flameEnabled.containsKey(target)) {
          if(flameEnabled.get(target) == true) {
            sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.GREEN+"Enabled "+ChatColor.YELLOW+"for "+target.getDisplayName());
            return true;
          } else {            
            sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.RED+"Disabled "+ChatColor.YELLOW+"for "+target.getDisplayName());
            return true;
          }
        } else {
          sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.RED+"Disabled "+ChatColor.YELLOW+"for "+target.getDisplayName());
          return true;
        }

      }

      // IF SETTING ON ANOTHER
    } else if(args.length == 2 && player.hasPermission("simplextras.flame.other")) {
      if (Bukkit.getPlayer(args[1]) == null) {
        sender.sendMessage(ChatColor.RED+"Player "+args[1]+" not online");
        return true;              
      }
      final Player target = Bukkit.getPlayer(args[1]);
      // FLAME ON
      if (args[0].equalsIgnoreCase("on")) { 
        if(flameEnabled.containsKey(target)){
          if(flameEnabled.get(target) == true){
            sender.sendMessage(ChatColor.GREEN+"Flame Already Enabled");
            return true;
          }
        }
        stopFlame.put(target, false);
        flameEnabled.put(target, true);
        taskID.put(target, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getServer().getPluginManager().getPlugin("SimpleExtras"), new Runnable() {          
          public void run() {
            target.getWorld().playEffect(target.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);                    
            if(stopFlame.get(target) == true){ Bukkit.getScheduler().cancelTask(taskID.get(target)); }
          }
        }, 7L, 7L));
        sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.GREEN+"On "+ChatColor.YELLOW+"for "+target.getName());
        return true;
        // FLAME OFF
      } else if (args[0].equalsIgnoreCase("off")) {
        stopFlame.put(target, true);  
        flameEnabled.put(target, false);
        sender.sendMessage(ChatColor.YELLOW+"Flames: "+ChatColor.RED+"Off "+ChatColor.YELLOW+"for "+target.getName());
      }
    } else if(args.length == 2 && !player.hasPermission("simplextras.flame.other")) {
      sender.sendMessage(ChatColor.RED+"You do not have permission.");
      return true;  
    }
    return true;
  }
}
