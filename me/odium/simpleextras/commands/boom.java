package me.odium.simpleextras.commands;


import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class boom implements CommandExecutor {   

  public SimpleExtras plugin;
  public boom(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }

    Integer ExplosionPower = null;
    Integer BlastMode = null;
    Player target = null;
    String BoomMsgSENDER = null;
    String BoomMsgTARGET = null;
    String playerName;
    int NukePower = plugin.getConfig().getInt("NukePower");
    // IF ON SELF
    if (args.length == 0) {
      if (player == null) {
        sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
        return true;
      }
      ExplosionPower = 0;
      BlastMode = 0;
      target = player;
      BoomMsgSENDER = ChatColor.RED+"Boom!";
      BoomMsgTARGET = null;
      // IF ONE ARG
    } else if(args.length == 1) {      
      // IF ARG IS DAMAGE
      if(args[0] == "-d") {
        if (player == null) {
          sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
          return true;
        }
        ExplosionPower = 3;
        BlastMode = 2;
        target = player;
        BoomMsgSENDER = ChatColor.RED+"Boom!";
        BoomMsgTARGET = null;
        // IF ARGS IS NUKE
      } else if(args[0] == "-n") {
        if (player == null) {
          sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
          return true;
        }
        if (player.hasPermission("boom.nuke")) {      
          ExplosionPower = NukePower;
          BlastMode = 2;
          target = player;
          BoomMsgSENDER = ChatColor.RED+"KaBoom!";
          BoomMsgTARGET = null;
        } else {
          sender.sendMessage(ChatColor.RED+"No Permission");
          return true;
        }
        // IF ARG IS OTHER PLAYER
      } else if(Bukkit.getPlayer(args[0]) != null) {
        ExplosionPower = 0;
        BlastMode = 0;
        target = Bukkit.getPlayer(args[0]);
        if (player == null) {
          playerName = "Console";
        } else {
          playerName = player.getDisplayName();
        }
        BoomMsgSENDER =  target.getDisplayName() + ChatColor.YELLOW+" has been peacefully exploded";
        BoomMsgTARGET = ChatColor.RED+"Boom!" + ChatColor.WHITE + " Courtesy of: "+playerName;   
      } else if(Bukkit.getPlayer(args[0]) == null && !args[0].contains("-d") && !args[0].contains("-n")) {                  
        sender.sendMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.WHITE+" is not online.");
        return true;
      }
      // IF TWO ARGUMENTS
    } else if(args.length == 2) {
      if(Bukkit.getPlayer(args[1]) == null) {
        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
        return true;
      }
      target = Bukkit.getPlayer(args[1]);
      // IF FLAG = DAMAGE
      if (args[0].contains("d")) {
        ExplosionPower = 3;
        BlastMode = 2;
        if (player == null) {
          playerName = "Console";
        } else {
          playerName = player.getDisplayName();
        }
        BoomMsgSENDER =  target.getDisplayName() + ChatColor.YELLOW+" has been exploded";
        BoomMsgTARGET = ChatColor.RED+"Boom!" + ChatColor.WHITE + " Courtesy of: "+playerName;
      }
      // IF FLAG = NUKE
      if (args[0].contains("n")) {
        ExplosionPower = 20;
        BlastMode = 2;
        if (player == null) {
          playerName = "Console";
        } else {
          playerName = player.getDisplayName();
        }
        BoomMsgSENDER =  target.getDisplayName() + ChatColor.YELLOW+" has been NUKED!";
        BoomMsgTARGET = ChatColor.RED+"KaBoom!" + ChatColor.WHITE + " Courtesy of: "+playerName;

      }
      // IF FLAG = SILENT
      if (args[0].contains("s")) {
        BoomMsgTARGET = null;
      }
  
    }


    // EXPLOSION EVENT
    Location targetLocation = target.getLocation();
    targetLocation.setPitch(0);
    targetLocation.setYaw(0);
    
    plugin.Blast_Mode.put(targetLocation, BlastMode);
    target.getWorld().createExplosion(target.getLocation(), ExplosionPower);    
    if (BoomMsgSENDER != null) {      
      sender.sendMessage(BoomMsgSENDER);
    }
    if (BoomMsgTARGET != null) {
     target.sendMessage(BoomMsgTARGET);
    }
    return true;
  }
}


//    } else if(args.length == 2) {
//      if (args[0] == "") {
//        
//      }
//    }
//
//
//    if(args.length == 0) {
//      if (player == null) {
//        sender.sendMessage("This command can only be run by a player");
//      } else {
//        Player target = (Player)sender;
//        float explosionPower = 3;
//        plugin.Blast_Mode.put(target.getLocation(), 0);
//        target.getWorld().createExplosion(target.getLocation(), explosionPower);
//        player.sendMessage(ChatColor.RED + "Boom!");
//        return true;
//      }     
//    } else if(args.length == 1) {      
//      if(args[0] == "-d") {
//        Player target = (Player)sender;
//        float explosionPower = 3;
//        target.getWorld().createExplosion(target.getLocation(), explosionPower);
//        player.sendMessage(ChatColor.RED + "Boom!");
//        return true;
//      } else if(args[0] == "-n") {
//        if (player.hasPermission("boom.nuke")) {
//          Player target = (Player)sender;
//          float explosionPower = 20;
//          target.getWorld().createExplosion(target.getLocation(), explosionPower);
//          player.sendMessage(ChatColor.RED + "Boom!");
//          return true;
//        }
//      } else if(Bukkit.getPlayer(args[0]) != null) {              
//        Player target = Bukkit.getPlayer(args[0]);
//        String playerName;
//        float explosionPower = 0;
//        target.getWorld().createExplosion(target.getLocation(), explosionPower);
//        if (player == null) {
//          playerName = "Console";
//        } else {
//          playerName = player.getDisplayName();
//        }
//          target.sendMessage(ChatColor.RED + "Boom! " + ChatColor.WHITE + "Courtesy of: "+playerName);
//          sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE + " Has been peacefully exploded");  
//          return true;
//      } else if(Bukkit.getPlayer(args[0]) == null) {                  
//        sender.sendMessage(ChatColor.DARK_GREEN + args[0] + ChatColor.WHITE+" is not online.");
//        return true;
//      }
//    } else if(args.length == 2 && args[0].equals("-d")) {      
//      if(Bukkit.getPlayer(args[1]) != null) {
//        Player target = Bukkit.getPlayer(args[1]);
//        explosionPower = 3;
//        target.getWorld().createExplosion(target.getLocation(), explosionPower);      
//        target.sendMessage(ChatColor.RED+"Boom!"+ChatColor.WHITE+" Courtesy of: " + player.getDisplayName());
//        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been exploded");
//        return true;
//      } else if(Bukkit.getPlayer(args[1]) == null) {           
//        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
//        return true;
//      }
//    } else if(args.length == 2 && args[0].equals("-s")) {      
//      if(Bukkit.getPlayer(args[1]) != null) {
//        Player target = Bukkit.getPlayer(args[1]);
//        explosionPower = 3;
//        target.getWorld().createExplosion(target.getLocation(), explosionPower);          
//        sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been silently exploded");       
//        return true;
//      } else if(Bukkit.getPlayer(args[1]) == null) {           
//        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
//        return true;
//      }
//    } else if(args.length == 2 && args[0].equals("-n")) {
//      if (player == null || player.hasPermission("boom.nuke")) {
//        if(Bukkit.getPlayer(args[1]) != null) {
//          Player target = Bukkit.getPlayer(args[1]);
//          explosionPower = 20;
//          target.getWorld().createExplosion(target.getLocation(), explosionPower);  
//          if (player.getDisplayName() == null) {
//            target.sendMessage(ChatColor.RED+"Boom!"+ChatColor.WHITE+" Courtesy of: Console");
//            sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been Nuked");  
//            return true;
//          } else {
//            target.sendMessage(ChatColor.RED+"Boom!"+ChatColor.WHITE+" Courtesy of: " + player.getDisplayName());
//            sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been Nuked");  
//            return true;
//          }
//        } else if(Bukkit.getPlayer(args[1]) == null) {           
//          sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
//          return true;
//        }
//      }
//    } else if(args.length == 2 && args[0].equals("-ns")) {
//      if (player == null || player.hasPermission("boom.nuke")) {
//        if(Bukkit.getPlayer(args[1]) != null) {
//          Player target = Bukkit.getPlayer(args[1]);
//          explosionPower = 20;
//          target.getWorld().createExplosion(target.getLocation(), explosionPower);
//          sender.sendMessage(ChatColor.DARK_GREEN + target.getDisplayName() + ChatColor.WHITE +  " has been silently Nuked");  
//          return true;
//        } else if(Bukkit.getPlayer(args[1]) == null) {           
//          sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.WHITE+" is not online.");
//          return true;
//        }
//      }
//    }
//    return true;
//  }
//}