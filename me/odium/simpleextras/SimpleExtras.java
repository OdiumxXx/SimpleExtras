package me.odium.simpleextras;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;


public class SimpleExtras extends JavaPlugin {
  private static float explosionPower = 0;
  double version = 0.4;
  Logger log = Logger.getLogger("Minecraft");


  public void onEnable(){
    log.info("[SimpleExtras-" + version + " enabled]");
    FileConfiguration cfg = getConfig();
    FileConfigurationOptions cfgOptions = cfg.options();
    cfgOptions.copyDefaults(true);
    cfgOptions.copyHeader(true);
    saveConfig();
  }

  public void onDisable(){ 
    log.info("[SimpleExtras-" + version + " disabled]");	
  }

  public static String getCurrentDTG (long l_time){
    Date date = new Date (l_time);
    SimpleDateFormat dtgFormat = new SimpleDateFormat ("E - hh:mm (dd/MMM/yyyy)");
    return dtgFormat.format (date);
  }

  public static String replaceColorMacros(String str) {
    str = str.replace("`r", ChatColor.RED.toString());
    str = str.replace("`R", ChatColor.DARK_RED.toString());        
    str = str.replace("`y", ChatColor.YELLOW.toString());
    str = str.replace("`Y", ChatColor.GOLD.toString());
    str = str.replace("`g", ChatColor.BLUE.toString());
    str = str.replace("`G", ChatColor.DARK_GREEN.toString());        
    str = str.replace("`c", ChatColor.AQUA.toString());
    str = str.replace("`C", ChatColor.DARK_AQUA.toString());        
    str = str.replace("`b", ChatColor.BLUE.toString());
    str = str.replace("`B", ChatColor.DARK_BLUE.toString());        
    str = str.replace("`p", ChatColor.LIGHT_PURPLE.toString());
    str = str.replace("`P", ChatColor.DARK_PURPLE.toString());
    str = str.replace("`0", ChatColor.BLACK.toString());
    str = str.replace("`1", ChatColor.DARK_GRAY.toString());
    str = str.replace("`2", ChatColor.GRAY.toString());
    str = str.replace("`w", ChatColor.WHITE.toString());        
    return str;
  }


  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }	  

    if(cmd.getName().equalsIgnoreCase("se")){
      if (args.length == 0) {		
        sender.sendMessage(ChatColor.GOLD + "----- SimpleExtras Menu -----");
        sender.sendMessage(ChatColor.GOLD + " Menu Options:");
        if(player.hasPermission("simpleextras.exp")) {
          sender.sendMessage(ChatColor.BLUE + "  /exp " + ChatColor.GRAY + "- Experience Based Rewards");
        }
        if(player.hasPermission("simpleextras.seen")) {
          sender.sendMessage(ChatColor.BLUE + "  /seen " + ChatColor.GRAY + "- Info on players first/last appearance");
        }
        if(player.hasPermission("simpleextras.player")) {
          sender.sendMessage(ChatColor.BLUE + "  /player " + ChatColor.GRAY + "- Search the player-history");
        }
        if(player.hasPermission("simpleextras.zoom")) {
          sender.sendMessage(ChatColor.BLUE + "  /zoom " + ChatColor.GRAY + "- Zoom in/up/down a # of blocks");
        }
        sender.sendMessage(ChatColor.GOLD + " Stand-alone Commands:");
        if(player.hasPermission("simpleextras.boom")) {
          sender.sendMessage(ChatColor.BLUE + "  /boom [s] PlayerName " + ChatColor.GRAY + "- Surprise user with a safe explosion");		
        }
        if(player.hasPermission("simpleextras.bed")) {
          sender.sendMessage(ChatColor.BLUE + "  /bed " + ChatColor.GRAY + "- Teleport to your bed spawn");
        }
        sender.sendMessage(ChatColor.BLUE + "  /ranks " + ChatColor.GRAY + "- Return a list of server ranks");
        sender.sendMessage(ChatColor.BLUE + "  /basics " + ChatColor.GRAY + "- Return a list of server basic");
        return true;	
      } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
        if(player.hasPermission("simpleextras.reload")) {
          reloadConfig();
          sender.sendMessage(ChatColor.GRAY + "Config Reloaded!");
          return true;
        } else {
          sender.sendMessage(ChatColor.RED + "You do not have permission");
        }
      }
    }

    // Ranks and Basics output	  

    if(cmd.getName().equalsIgnoreCase("ranks")){
      java.util.List<String> rank = getConfig().getStringList("ranks");
      Iterator<String> iter = rank.iterator();        
      sender.sendMessage(ChatColor.GOLD + "--- Server Ranks ---");		        
      while (iter.hasNext()) {
        sender.sendMessage(replaceColorMacros(iter.next()));
      }	    
      return true;
    }		  
    if(cmd.getName().equalsIgnoreCase("basics")){
      java.util.List<String> basic = getConfig().getStringList("basics");
      Iterator<String> iter = basic.iterator();
      sender.sendMessage(ChatColor.GOLD + "--- Basic Commands ---");		        
      while (iter.hasNext()) {		        		
        sender.sendMessage(replaceColorMacros(iter.next()));		        
      }	        
      return true;
    }

    // Ye Olde Boom Command that everyone seems so fond of.

    if(cmd.getName().equalsIgnoreCase("boom")){
      if (player == null) {
        sender.sendMessage("This command can only be run by a player");
      } else if(args.length == 0) {	 	
        Player target = (Player)sender;
        float explosionPower = 0;
        target.getWorld().createExplosion(target.getLocation(), explosionPower);
        player.sendMessage(ChatColor.RED + "Boom!");
        return true;
      }	 		
      if(args.length == 1) {
        if (args[0] == "-s") {
          return false;
        } else if(args[0] == "-d") {
          return false;
        } else if(args[0] == "-h") {
          return false;
        } else if(this.getServer().getPlayer(args[0]) == null) {	    			
          sender.sendMessage(ChatColor.GRAY + args[0] + " is not online.");
          return true;	           
        } else if(this.getServer().getPlayer(args[0]) != null) {
          Player target = this.getServer().getPlayer(args[0]);
          float explosionPower = 0;
          target.getWorld().createExplosion(target.getLocation(), explosionPower);
          target.sendMessage(ChatColor.RED + "Boom! " + ChatColor.GRAY + "Courtesy of: " + player.getDisplayName());	        
          sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " Has been peacefully exploded");
          return true;
        }
      }   	 	
      if(args.length == 2) {
        if(this.getServer().getPlayer(args[1]) == null) {	    			
          sender.sendMessage(ChatColor.GRAY + args[1] + " is not online.");
          return true;	           
        } else if(this.getServer().getPlayer(args[1]) != null){
          Player target = this.getServer().getPlayer(args[1]);   	   	    
          String sendermsg = ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " Has been peacefully exploded";
          String targetmsg = ChatColor.RED + "Boom! " + ChatColor.GRAY + "Courtesy of: " + player.getDisplayName();
          if (args[0].contains("d")) {
            explosionPower = 2;
            sendermsg = ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " Has been exploded!";
          } else if (args[0].contains("h")) {
            explosionPower = 0;
            target.setHealth(20);
            sendermsg = ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " Has been explosivley healed";
          } else if (args[0].contains("s")) {
            targetmsg = " ";
            sendermsg = ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " Has been secretly exploded";
          }
          target.getWorld().createExplosion(target.getLocation(), explosionPower);
          target.sendMessage(targetmsg);
          sender.sendMessage(sendermsg);
          return true;
        }
      }
    }

    // SimpleRewards Begins [Experience]

    if(cmd.getName().equalsIgnoreCase("exp")){
      if (args.length == 0) {	      
        if (player == null) {
          sender.sendMessage("This command can only be run by a player");
        } else {	    
          sender.sendMessage(ChatColor.GOLD + " ----- Experience Rewards ----- ");
          sender.sendMessage(ChatColor.BLUE + "  /exp PlayerName # " + ChatColor.GRAY + "- Award a user # of exp points");
          sender.sendMessage(ChatColor.BLUE + "  /levelup PlayerName # " + ChatColor.GRAY + "- Award a user # of exp levels");
          sender.sendMessage(ChatColor.BLUE + "  /levelset PlayerName # " + ChatColor.GRAY + "- Set a users exp level");
          sender.sendMessage(ChatColor.BLUE + "  /levelget PlayerName " + ChatColor.GRAY + "- View a users exp level");
          return true;
        }
      }	  
      if (args.length == 1) {	      
        Player target = this.getServer().getPlayer(args[0]);
        if(this.getServer().getPlayer(args[0]) == null) {	    			
          sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " is not online.");
          return true;	           
        } else {
          target.giveExp(10);
          sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " has been awarded 10xp ");		    
          target.sendMessage(ChatColor.GRAY + "You've been awarded " + ChatColor.BLUE + "10xp " + ChatColor.GRAY + "by " + ChatColor.BLUE + sender.getName());	          
          return true;
        }
      }
      if (args.length == 2) {	      
        Player target = this.getServer().getPlayer(args[0]);
        if(this.getServer().getPlayer(args[0]) == null) {	    			
          sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " is not online.");
          return true;	           
        } else {
          int xp = Integer.parseInt( args[1] );
          target.giveExp(xp);
          sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " has been awarded " + ChatColor.BLUE + xp + "xp");		    
          target.sendMessage(ChatColor.GRAY + "You've been awarded " + ChatColor.BLUE + xp + "xp" + ChatColor.GRAY + " by " + ChatColor.BLUE + sender.getName());	          
          return true;
        }
      }
    }

    // SimpleRewards [LevelUp]

    if(cmd.getName().equalsIgnoreCase("levelup")){
      if (args.length == 0) {		  
        if (player == null) {
          sender.sendMessage("This command can only be run by a player");
        } else {
          int cl = player.getLevel();
          int cla = cl + 1;
          player.setLevel(cla);
          player.sendMessage(ChatColor.GRAY + "You've levelled up, Cheater!");
          return true;
        }
      }
      if (args.length == 1) {
        Player target = this.getServer().getPlayer(args[0]);
        if(this.getServer().getPlayer(args[0]) == null) {	    			
          sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " is not online.");
          return true;	           
        } else {
          int cl = target.getLevel();
          int cla = cl + 1;
          target.setLevel(cla);          
          sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " has been levelled up");
          target.sendMessage(ChatColor.BLUE + sender.getName() + ChatColor.GRAY + " Raised your Experience Level");	          
          return true;
        }
      }
      if (args.length == 2) {
        Player target = this.getServer().getPlayer(args[0]);
        if(this.getServer().getPlayer(args[0]) == null) {	    			
          sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " is not online");
          return true;	           
        } else {
          int cl = target.getLevel();
          int lvl = Integer.parseInt( args[1] );
          int cla = cl + lvl;
          target.setLevel(cla);          
          sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " has been levelled up");
          target.sendMessage(ChatColor.BLUE + sender.getName() + ChatColor.GRAY + " Raised your Experience Level by " + ChatColor.BLUE + lvl);	          
          return true;
        }
      }
    }

    // SimpleRewards [levelset]

    if(cmd.getName().equalsIgnoreCase("levelset")){
      if (args.length == 0) {		  
        if (player == null) {
          sender.sendMessage("This command can only be run by a player");
        } else {
          player.setLevel(150);
          player.sendMessage(ChatColor.GRAY + "You've made yourself " + ChatColor.BLUE + "Level 150!");
          return true;
        } 
      }
      if (args.length == 1) {	      
        Player target = this.getServer().getPlayer(args[0]);
        if(this.getServer().getPlayer(args[0]) == null) {	    			
          sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " is not online.");
          return true;	           
        } else {
          target.setLevel(120);
          sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " Has been made " + ChatColor.BLUE + "Level 150!" );		    
          target.sendMessage(ChatColor.GRAY + "You've been made " + ChatColor.BLUE + "Level 150 " + ChatColor.GRAY + "by " + ChatColor.BLUE + sender.getName());	          
          return true;
        }
      }	    
      if (args.length == 2) {	      
        Player target = this.getServer().getPlayer(args[0]);
        if(this.getServer().getPlayer(args[0]) == null) {	    			
          sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " is not online.");
          return true;	           
        } else {
          int lvlset = Integer.parseInt( args[1] );
          target.setLevel(lvlset);
          sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " Has been made " + ChatColor.BLUE + "Level " + lvlset );		    
          target.sendMessage(ChatColor.GRAY + "You've been made " + ChatColor.BLUE + "Level " + lvlset + ChatColor.GRAY + " by " + ChatColor.BLUE + sender.getName());	          
          return true;
        }
      }	    
    }
    // SimpleRewards [levelget]
    if(cmd.getName().equalsIgnoreCase("levelget")){ 		  
      if (player == null) {
        sender.sendMessage("This command can only be run by a player");
      } else if(this.getServer().getPlayer(args[0]) == null) {
        sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " is not online.");
        return true;	           
      } else {
        Player target = this.getServer().getPlayer(args[0]);
        int targetlevel = target.getLevel();
        sender.sendMessage(ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + " is level " + ChatColor.BLUE + targetlevel );	        	          
        return true;
      }
    }

    // Return a user to their Bed Spawn

    if(cmd.getName().equalsIgnoreCase("bed")){
      if (args.length == 0) {
        if (player == null) {
          sender.sendMessage("This command can only be run by a player");
        } else {
          Location bedloc = player.getBedSpawnLocation();		          
          player.teleport(bedloc);
          player.sendMessage(ChatColor.GRAY + "You have been returned to your bed");
          return true;
        } 
      }
    }

    // SimpleSeen

    if(cmd.getName().equalsIgnoreCase("seen")){
      if (args.length == 0) {
        if (player == null) {
          sender.sendMessage("This command can only be run by a player");
        } else {
          sender.sendMessage(ChatColor.GOLD + " ----- Seen ----- ");
          sender.sendMessage(ChatColor.BLUE + "  /seen PlayerName " + ChatColor.GRAY + "- Return the LAST time player was seen");
          sender.sendMessage(ChatColor.BLUE + "  /seenf PlayerName " + ChatColor.GRAY + "- Return the FIRST time player was seen");
          sender.sendMessage(ChatColor.GRAY + "Note: These commands are " + ChatColor.RED + "CaseSensitive");
          //  long lastseen = player.getLastPlayed();
          //  String strDte = getCurrentDTG(lastseen);
          //  sender.sendMessage(ChatColor.BLUE + "you were last seen: " + ChatColor.BLUE + strDte);	    	
          return true;
        }
      }

      if (args.length == 1) {		    
        Player targeton = this.getServer().getPlayer(args[0]);
        if (targeton != null) {
          sender.sendMessage(ChatColor.BLUE + targeton.getDisplayName() + ChatColor.GRAY + " is online right now!");
          return true;
        } else {
          OfflinePlayer target = this.getServer().getOfflinePlayer(args[0]);
          long lastseen = target.getLastPlayed();
          if(lastseen == 0) {	    			
            sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " has not been seen!");
            sender.sendMessage(ChatColor.GOLD + "(Must use exact username)");
            return true;
          } else {
            String strDte = getCurrentDTG(lastseen);
            sender.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " was last seen: " + ChatColor.BLUE + strDte);
            return true;		    	  
          }
        }
      }
    }

    // seenf

    if(cmd.getName().equalsIgnoreCase("seenf")){
      if (args.length == 0) {
        if (player == null) {
          sender.sendMessage("This command can only be run by a player");
        } else {
          long firstseen = player.getFirstPlayed();
          String strDte = getCurrentDTG(firstseen);
          player.sendMessage(ChatColor.GRAY + "You first logged in: " + ChatColor.BLUE + strDte);
          return true;
        }
      }

      if (args.length == 1) {
        Player targeton = this.getServer().getPlayer(args[0]);
        if (targeton == null) {
          OfflinePlayer target = this.getServer().getOfflinePlayer(args[0]);
          long firstseen = target.getFirstPlayed();
          if(firstseen == 0) {
            sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " has not been seen!");
            sender.sendMessage(ChatColor.GOLD + "(Must use exact username)");
            return true;	           
          } else {			      
            String strDte = getCurrentDTG(firstseen);
            sender.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " first logged in: " + ChatColor.BLUE + strDte);
          } 					
        } else {
          long firstseen = targeton.getFirstPlayed();
          String strDte = getCurrentDTG(firstseen);
          sender.sendMessage(ChatColor.BLUE + targeton.getName() + ChatColor.GRAY + " first logged in: " + ChatColor.BLUE + strDte);
        }
        return true;
      }
    }

    // Player Search

    if(cmd.getName().equalsIgnoreCase("player")){
      if (player == null) {
        sender.sendMessage("This command can only be run by a player");
      } else {        	   
        if (args.length == 0) {
          sender.sendMessage(ChatColor.GOLD + " ----- Player Search ----- ");
          sender.sendMessage(ChatColor.BLUE + "  /Player PartofName " + ChatColor.GRAY + "- Search for a player " + ChatColor.RED + "(CaseSensitive)");
          return true;
        } else {
          sender.sendMessage(ChatColor.GOLD + "Search Results:");
          int i;           
          OfflinePlayer[] Results = org.bukkit.Bukkit.getServer().getOfflinePlayers();
          for (i = 0; i < Results.length; i++) {              
            if (Results[i].getName().contains(args[0])) {
              sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.BLUE + Results[i].getName());  
            }   	 
          } 
        }
        return true;
      }
    }

    // Zoom in/up/down

    if(cmd.getName().equalsIgnoreCase("zoom")){
      if (args.length == 0) {	    	  
        sender.sendMessage(ChatColor.GOLD + " ----- Zoom ----- ");
        sender.sendMessage(ChatColor.BLUE + "  /zoom in # " + ChatColor.GRAY + "- Zoom where player is looking by # of blocks");
        sender.sendMessage(ChatColor.BLUE + "  /zoom up # " + ChatColor.GRAY + "- Zoom UP number of blocks");
        sender.sendMessage(ChatColor.BLUE + "  /zoom down # " + ChatColor.GRAY + "- Zoom DOWN number of blocks");
        return true;
      }
      if (args.length == 1 && args[0].equalsIgnoreCase("up")) {
        sender.sendMessage(ChatColor.BLUE + "/zoom up # " + ChatColor.GRAY + "- Zoom UP number of blocks (Replacing # with number)");
      }
      if (args.length == 1 && args[0].equalsIgnoreCase("down")) {
        sender.sendMessage(ChatColor.BLUE + "/zoom down # " + ChatColor.GRAY + "- Zoom DOWN number of blocks (Replacing # with number)");
      }
      if (args.length == 1 && args[0].equalsIgnoreCase("in")) {
        sender.sendMessage(ChatColor.BLUE + "/zoom in # " + ChatColor.GRAY + "- Zoom IN number of blocks (Replacing # with number)");
      }
      if (args.length == 2 && args[0].equalsIgnoreCase("in")) {
        Location playerLoc = player.getLocation();
        float dist = Float.parseFloat(args[1]);
        Location loc = playerLoc.add(playerLoc.getDirection().normalize().multiply(dist).toLocation(player.getWorld(), 0, 0));
        player.teleport(loc);
        sender.sendMessage(ChatColor.GRAY + "Zoomed " + ChatColor.BLUE + args[1] + ChatColor.GRAY + " Blocks");
        return true;
      }
      if (args.length == 2 && args[0].equalsIgnoreCase("up")) {
        Location playerLoc = player.getLocation();
        float dist = Float.parseFloat(args[1]);
        Location loc = playerLoc.add(0, dist, 0);
        player.teleport(loc);
        sender.sendMessage(ChatColor.GRAY + "Zoomed " + ChatColor.BLUE + "UP " + args[1] + ChatColor.GRAY + " Blocks");
        return true;
      }
      if (args.length == 2 && args[0].equalsIgnoreCase("down")) {
        Location playerLoc = player.getLocation();
        float dist = Float.parseFloat(args[1]);
        Location loc = playerLoc.subtract(0, dist, 0);
        player.teleport(loc);
        sender.sendMessage(ChatColor.GRAY + "Zoomed " + ChatColor.BLUE + "DOWN " + args[1] + ChatColor.GRAY + " Blocks");
        return true;
      }
    }

    return false;
    // TODO
    // - Console Output
  }
}
