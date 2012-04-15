package me.odium.simpleextras;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

public class SimpleExtras extends JavaPlugin {
  private static float explosionPower = 0;
  Logger log = Logger.getLogger("Minecraft");


  public void onEnable(){
    log.info("[" + getDescription().getName() + "] " + getDescription().getVersion() + " enabled.");
    FileConfiguration cfg = getConfig();
    FileConfigurationOptions cfgOptions = cfg.options();
    cfgOptions.copyDefaults(true);
    cfgOptions.copyHeader(true);
    saveConfig();
    // declare new listener
    new PListener(this);
  }

  public void onDisable(){ 
    log.info("[" + getDescription().getName() + "] " + getDescription().getVersion() + " disabled.");	
  }

  //  .: GoMySQL :. Convert to unix timestamp.
  //  .: GoMySQL :. Calculate hours from seconds.
  //  .: GoMySQL :. date(now) - last_seen = #seconds / 3600 == hours since

  public static String getCurrentDTG (long l_time){
    Date date = new Date (l_time);
    SimpleDateFormat dtgFormat = new SimpleDateFormat ("E - hh:mm (dd/MMM/yyyy)");
    return dtgFormat.format (date);
  }


  public static String getFriendly(long milliseconds) {
    String finaltime = "";
    String unit = "";
    long currenttime = Calendar.getInstance().getTimeInMillis();
    Integer time = (int) (currenttime - milliseconds) / 1000;
    if (time > 0) {
      Integer minute = 60;
      Integer hour = minute * 60;
      Integer day = hour * 24;
      int seconds = time;
      int minutes = (int) Math.floor(time / minute);
      int hours = (int) Math.floor(time / hour);
      int days = (int) Math.floor(time / day);

      if (days >= 1) {
        unit = (days > 1) ? "days" : "day";
        finaltime += days + " " + unit + " ";
        time = time - (days * day);
        hours = (int) Math.floor(time / hour);
      }
      if (hours >= 1) {
        unit = (hours > 1) ? "hours" : "hour";
        finaltime += hours + " " + unit + " ";
        time = time - (hours * hour);
        minutes = (int) Math.floor(time / minute);
      }
      if (minutes >= 1) {
        unit = (minutes > 1) ? "minutes" : "minute";
        finaltime += minutes + " " + unit + " ";
        time = time - (minutes * minute);
        seconds = (int) Math.floor(time);
      }
      if (seconds >= 1) {
        unit = (seconds > 1) ? "seconds" : "second";
        finaltime += seconds + " " + unit + " ";
      }
    }
    else {
      return null;
    }
    return finaltime;
  }

  public String myGetPlayerName(String name) { 
    Player caddPlayer = getServer().getPlayerExact(name);
    String pName;
    if(caddPlayer == null) {
      caddPlayer = getServer().getPlayer(name);
      if(caddPlayer == null) {
        pName = name;
      } else {
        pName = caddPlayer.getName();
      }
    } else {
      pName = caddPlayer.getName();
    }
    return pName;
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

  public class PListener implements Listener {

    public PListener(SimpleExtras instance) {
      Plugin plugin = instance;
      getServer().getPluginManager().registerEvents(this, plugin);  
    }    

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(final PlayerInteractEvent event) {
      Player player = event.getPlayer();
      //make sure we are dealing with a block and not clicking on air or an entity
      if (event.getAction().equals(Action.LEFT_CLICK_AIR) && player.getItemInHand().getTypeId() == 280 && player.hasPermission("simpleextras.fireball")) {
        Fireball fb = player.getWorld().spawn(player.getLocation().add(0, 2, 0), Fireball.class);
        fb.setShooter(player.getPlayer());
        fb.setYield(0);
        fb.setIsIncendiary(true);          
        fb.setDirection(player.getLocation().getDirection().multiply(3));
      }      
    }
  }

  //  private boolean isPlayerWithinRadius(Player player, Location loc, double radius)  {
  //    double x = player.getLocation().getX();
  //    double y = player.getLocation().getY();
  //    double z = player.getLocation().getZ();
  //    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
  //    if (distance <= radius)
  //        return true;
  //    else
  //        return false;
  //}

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }	  

    //    if(cmd.getName().equalsIgnoreCase("test")){
    //      World world = player.getWorld();
    //      int x = player.getLocation().getBlockX();
    //      int y = player.getLocation().getBlockY();
    //      int z = player.getLocation().getBlockZ();
    //      double startx = x+5;
    //      double startz = z+5;
    //      double endx = x-5;
    //      double endz = z-5;
    //      Block blockToChange = world.getBlockAt(x,y,z); // get the block with the current coordinates
    //      blockToChange.setTypeId(34);    // set the block to Type 34
    //      
    //    }


    if(cmd.getName().equalsIgnoreCase("se")){
      if (args.length == 0) {		
        sender.sendMessage(ChatColor.GOLD + "--- SimpleExtras Menu ---");
        sender.sendMessage(ChatColor.GOLD + " Menu Options:");
        if(player.hasPermission("simpleextras.exp") || player == null) {
          sender.sendMessage(ChatColor.AQUA + "  /exp " + ChatColor.GRAY + "- Experience Based Rewards");
        }
        if(player.hasPermission("simpleextras.seen") || player == null) {
          sender.sendMessage(ChatColor.AQUA + "  /seen " + ChatColor.GRAY + "- Info on players first/last appearance");
        }
        if(player.hasPermission("simpleextras.findplayer") || player == null) {
          sender.sendMessage(ChatColor.AQUA + "  /findplayer " + ChatColor.GRAY + "- Search the player-history");
        }
        if(player.hasPermission("simpleextras.zoom") || player == null) {
          sender.sendMessage(ChatColor.AQUA + "  /zoom " + ChatColor.GRAY + "- Zoom in/up/down a # of blocks");
        }
        if(player.hasPermission("simpleextras.effects") || player == null) {
          sender.sendMessage(ChatColor.AQUA + "  /effects " + ChatColor.GRAY + "- Return a list of player effects");
        }
        sender.sendMessage(ChatColor.GOLD + " Stand-alone Commands:");
        sender.sendMessage(ChatColor.BLUE + "  /ranks " + ChatColor.GRAY + "- Return a list of server ranks");
        sender.sendMessage(ChatColor.BLUE + "  /basics " + ChatColor.GRAY + "- Return a list of server basic");
        if(player.hasPermission("simpleextras.creative") || player == null) {
          sender.sendMessage(ChatColor.BLUE + "  /creative" + ChatColor.GRAY + "- Change a gamemode to Creative");    
        }
        if(player.hasPermission("simpleextras.survival") || player == null) {
          sender.sendMessage(ChatColor.BLUE + "  /survival" + ChatColor.GRAY + "- Change a gamemode to Survival");    
        }
        if(player.hasPermission("simpleextras.boom") || player == null) {
          sender.sendMessage(ChatColor.BLUE + "  /boom [dsh] PlayerName " + ChatColor.GRAY + "- explode a user");   
        }
        if(player.hasPermission("simpleextras.fireball") || player == null) {
          sender.sendMessage(ChatColor.BLUE + "  /gf" + ChatColor.GRAY + " - Fireball (or leftclick with stick)");    
        }
        if(player.hasPermission("simpleextras.bed") || player == null) {
          sender.sendMessage(ChatColor.BLUE + "  /bed " + ChatColor.GRAY + "- Teleport to your bed spawn");
        }
        if(player.hasPermission("simpleextras.magic") || player == null) {
          sender.sendMessage(ChatColor.BLUE + "  /magic <text>" + ChatColor.GRAY + "- Magic Font!");    
        }
        return true;	
      } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
        if(player.hasPermission("simpleextras.reload") || player == null) {
          reloadConfig();
          sender.sendMessage(ChatColor.GRAY + "Config Reloaded!");
          return true;
        } else {
          sender.sendMessage(ChatColor.RED + "You do not have permission");
          return true;
        }
      }
    }
    
    if(cmd.getName().equalsIgnoreCase("effects")){
      sender.sendMessage(ChatColor.GOLD + "- Player Effects -");
      if(player.hasPermission("simpleextras.ignite") || player == null) {
        sender.sendMessage(ChatColor.RED + "  /ignite <playername>" + ChatColor.GRAY + "- Ignite a player");    
      }        
      if(player.hasPermission("simpleextras.blind") || player == null) {
        sender.sendMessage(ChatColor.RED + "  /blind <playername> <minutes>" + ChatColor.GRAY + "- Blind a player");    
      }
      if(player.hasPermission("simpleextras.slow") || player == null) {
        sender.sendMessage(ChatColor.RED + "  /slow <playername> <minutes>" + ChatColor.GRAY + "- Slow a player");    
      }
      if(player.hasPermission("simpleextras.confuse") || player == null) {
        sender.sendMessage(ChatColor.RED + "  /confuse <playername> <minutes>" + ChatColor.GRAY + "- Confuse a player");    
      }
      if(player.hasPermission("simpleextras.fly") || player == null) {
        sender.sendMessage(ChatColor.GREEN + "  /fly [playername] [minutes]" + ChatColor.GRAY + "- Toggle flight");    
      }
      if(player.hasPermission("simpleextras.speed") || player == null) {
        sender.sendMessage(ChatColor.GREEN + "  /speed <playername> <minutes>" + ChatColor.GRAY + "- Give player Speed x2");    
      }        
      if(player.hasPermission("simpleextras.jump") || player == null) {
        sender.sendMessage(ChatColor.GREEN + "  /jump <playername> <minutes>" + ChatColor.GRAY + "- Give player Jump x2");    
      }
      if(player.hasPermission("simpleextras.superdig") || player == null) {
        sender.sendMessage(ChatColor.GREEN + "  /superdig <playername> <minutes>" + ChatColor.GRAY + "- Give player SuperDig x2");    
      }
      return true;
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

    if(cmd.getName().equalsIgnoreCase("owner")){
      String owner = getConfig().getString("owner");
      sender.sendMessage(ChatColor.RED + owner + ChatColor.GRAY + " is the Owner of this server");
      return true;
    }

    if(cmd.getName().equalsIgnoreCase("website")){
      String website = getConfig().getString("website");  
      sender.sendMessage(ChatColor.GRAY + "Website: " + ChatColor.AQUA + website);
      return true;
    }

    // Gamemode Changer

    if(cmd.getName().equalsIgnoreCase("creative")){
      if (args.length == 0) {
        int gm = player.getGameMode().getValue();
        if(gm == 0){
          player.setGameMode(GameMode.CREATIVE);
          sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.GRAY + "Your Gamemode has been changed to " + ChatColor.BLUE + "Creative");
          return true;
        } else {
          sender.sendMessage(ChatColor.RED + "* " + ChatColor.GRAY + "Your Gamemode is already set to " + ChatColor.BLUE + "Creative");
          return true;
        }
      } else if(args.length == 1 && player.hasPermission("simpleextras.creative.other") || player == null) {
        Player target = this.getServer().getPlayer(args[0]);
        if (target == null) {
          // DO NOTHING
          return true;
        } else {
          int gm = target.getGameMode().getValue();
          if(gm == 0){
            target.setGameMode(GameMode.CREATIVE);
            sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + "'s Gamemode has been changed to " + ChatColor.BLUE + "Creative");
            return true;
          } else {
            sender.sendMessage(ChatColor.RED + "* " + ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + "'s Gamemode is already set to " + ChatColor.BLUE + "Creative");
            return true; 
          }
        }
      }
    }

    if(cmd.getName().equalsIgnoreCase("survival")){
      if (args.length == 0) {
        int gm = player.getGameMode().getValue();
        if(gm == 1){
          player.setGameMode(GameMode.SURVIVAL);
          log.info(player + "Changed gamemode to SURVIVAL");
          sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.GRAY + "Your Gamemode has been changed to " + ChatColor.BLUE + "Survival");
          return true;
        } else {
          sender.sendMessage(ChatColor.RED + "* " + ChatColor.GRAY + "Your Gamemode is already set to " + ChatColor.BLUE + "Survival");
          return true;
        }         
      } else if(args.length == 1 && player.hasPermission("simpleextras.survival.other") || player == null) {
        Player target = this.getServer().getPlayer(args[0]);
        if (target == null) {
          // DO NOTHING
          return true;
        } else {
          int gm = target.getGameMode().getValue();
          if(gm == 0){
            target.setGameMode(GameMode.SURVIVAL);
            sender.sendMessage(ChatColor.GREEN + "* " + ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + "'s Gamemode has been changed to " + ChatColor.BLUE + "Survival");
            return true;
          } else {
            sender.sendMessage(ChatColor.RED + "* " + ChatColor.BLUE + target.getDisplayName() + ChatColor.GRAY + "'s Gamemode is already set to " + ChatColor.BLUE + "Survival");
            return true; 
          }
        }
      }
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
          sender.sendMessage(ChatColor.GOLD + " --- Experience Rewards --- ");
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
        sender.sendMessage(ChatColor.GOLD + " --- Seen --- ");
        sender.sendMessage(ChatColor.BLUE + "  /seen PlayerName " + ChatColor.GRAY + "- Return the LAST time player was seen");
        sender.sendMessage(ChatColor.BLUE + "  /seenf PlayerName " + ChatColor.GRAY + "- Return the FIRST time player was seen");
        sender.sendMessage(ChatColor.GRAY + "Note: These commands are " + ChatColor.RED + "CaseSensitive");
        //  long lastseen = player.getLastPlayed();
        //  String strDte = getCurrentDTG(lastseen);
        //  sender.sendMessage(ChatColor.BLUE + "you were last seen: " + ChatColor.BLUE + strDte);	    	
        return true;
      } else if (args.length == 1) {		    
        Player targeton = this.getServer().getPlayer(args[0]);
        if (targeton != null) {
          sender.sendMessage(ChatColor.BLUE + targeton.getDisplayName() + ChatColor.GRAY + " is online right now!");
          return true;
        } else {
          OfflinePlayer target = this.getServer().getOfflinePlayer(args[0]);
          long lastseen = target.getLastPlayed();
          //          int lastseen = (int) target.getLastPlayed() / 1000;
          if(lastseen == 0) {	    			
            sender.sendMessage(ChatColor.BLUE + "'" + args[0] + "'" + ChatColor.GRAY + " has not been seen!");
            sender.sendMessage(ChatColor.GOLD + "(Must use exact username)");
            return true;
          } else {
            //            String strDte = getCurrentDTG(lastseen);
            String strDte = getFriendly(lastseen);
            sender.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " was last seen: " + ChatColor.BLUE + strDte + ChatColor.GRAY + "ago");
            return true;		    	  
          }
        }
      }
    

    // seenf

    if(cmd.getName().equalsIgnoreCase("seenf")){
      if (args.length == 0) {
          long firstseen = player.getFirstPlayed();
          String strDte = getCurrentDTG(firstseen);
          sender.sendMessage(ChatColor.GRAY + "You first logged in: " + ChatColor.BLUE + strDte);
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
            String strDte = getFriendly(firstseen);
            sender.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.GRAY + " first logged in: " + ChatColor.BLUE + strDte);
          } 					
        } else {
          long firstseen = targeton.getFirstPlayed();
          //          String strDte = getCurrentDTG(firstseen);
          String strDte = getFriendly(firstseen);          
          sender.sendMessage(ChatColor.BLUE + targeton.getName() + ChatColor.GRAY + " first logged in: " + ChatColor.BLUE + strDte);
        }
        return true;
      }
    }

    // Player Search

    if(cmd.getName().equalsIgnoreCase("findplayer")){
        if (args.length == 0) {
          sender.sendMessage(ChatColor.GOLD + " --- Player Search --- ");
          sender.sendMessage(ChatColor.BLUE + "  /findplayer PartofName " + ChatColor.GRAY + "- Search for a player " + ChatColor.RED + "(CaseSensitive)");
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

    // Zoom in/up/down

    if(cmd.getName().equalsIgnoreCase("zoom")){      
      if (args.length == 0) {	    	  
        sender.sendMessage(ChatColor.GOLD + " --- Zoom --- ");
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

    if(cmd.getName().equalsIgnoreCase("fly")){
      if(args.length == 0) {
        Boolean canfly = player.getAllowFlight();
        if(canfly == true) {
          player.setAllowFlight(false);
          player.setFlying(false);
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight " + ChatColor.RED +  "disabled ");
          return true;
        } else if(canfly == false) {
          player.setAllowFlight(true);
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GREEN + "Flight " + ChatColor.GREEN +  "enabled ");
          return true;
        }
      } else if(args.length == 1 && player.hasPermission("simpleextras.fly.other") || player == null) {
        Player target = this.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          Boolean canfly = target.getAllowFlight();
          String targetname = target.getDisplayName();
          if(canfly == true) {
            target.setAllowFlight(false);          
            target.setFlying(false);
            sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "Flight " + ChatColor.RED +  "disabled " + ChatColor.GRAY + "for " + ChatColor.BLUE + targetname);
            return true;
          } else if(canfly == false) {
            target.setAllowFlight(true);
            sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.GRAY + "for " + ChatColor.BLUE +  targetname);
            return true;
          }
        }
      } else if(args.length == 2 && player.hasPermission("simpleextras.fly.other") || player == null) {
        final Player target1 = this.getServer().getPlayer(args[0]);
        if (target1 == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
        final Player player1 = player;
        Boolean canfly = target1.getAllowFlight();
        String targetname = target1.getDisplayName();
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;  
        if(canfly == true) {
          sender.sendMessage("Already allowed to fly, timer set anyway.");        
          this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
              target1.setAllowFlight(false);          
              target1.setFlying(false);
              player1.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "Flight " + ChatColor.RED +  "disabled " + ChatColor.GRAY + "for " + ChatColor.BLUE + target1.getDisplayName());
            }
          }, mins);
          return true;
        } else if(canfly == false) {
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.GRAY + "for " + ChatColor.BLUE +  targetname + ChatColor.GRAY + " for " + min + " minutes");
          target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "Flight " + ChatColor.GREEN +  "enabled " + ChatColor.GRAY + "for " + ChatColor.BLUE + min + ChatColor.GRAY + " minutes");
          target1.setAllowFlight(true);          
          target1.setFlying(true);
          this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
              target1.setAllowFlight(false);          
              target1.setFlying(false);
              player1.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "Flight " + ChatColor.RED +  "disabled " + ChatColor.GRAY + "for " + ChatColor.BLUE + target1.getDisplayName());
              target1.sendMessage(ChatColor.GOLD + "* " + ChatColor.RED + "Flight " + ChatColor.RED +  "disabled ");
            }
          }, mins);
          return true;
        }
      }
    }
    }

    if(cmd.getName().equalsIgnoreCase("ignite")){
      if (args.length == 0) {        
        player.setFireTicks(10000);
        sender.sendMessage(ChatColor.GREEN + "" + player.getDisplayName() + ChatColor.GRAY + " ignited");
        return true;
      } else {
        Player target = this.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          target.setFireTicks(10000);
          sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " ignited");
          return true;
        }
      }
    }

    if(cmd.getName().equalsIgnoreCase("speed")){
      if (args.length == 0) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 2));
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given speed x2 for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
        return true;
      } else if (args.length == 1) {
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 2));
          sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " given speed x2 for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given speed x2 for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          return true;
        }
      } else if (args.length == 2 && player.hasPermission("simpleextras.speed.other") || player == null) {  
        Player target = Bukkit.getServer().getPlayer(args[0]);
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;        
        target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, mins, 2));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " given speed x2 for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given speed x2 for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        return true;
      } 
    }

    if(cmd.getName().equalsIgnoreCase("blind")){
      if (args.length == 0) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200, 1));
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been blinded for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
        return true;
      } else if (args.length == 1) {
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200, 1));
          sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been blinded for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been blinded for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          return true;
        }
      } else if (args.length == 2 && player.hasPermission("simpleextras.blind.other") || player == null) {  
        Player target = Bukkit.getServer().getPlayer(args[0]);
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;        
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, mins, 1));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been blinded for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been blinded for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        return true;
      } 
    }
    
    if(cmd.getName().equalsIgnoreCase("jump")){
      if (args.length == 0) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 3));
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given jump x2 for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
        return true;
      } else if (args.length == 1) {
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 3));
          sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been given jump x2 for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given jump x2 for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          return true;
        }
      } else if (args.length == 2 && player.hasPermission("simpleextras.jump.other") || player == null) {  
        Player target = Bukkit.getServer().getPlayer(args[0]);
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;        
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, mins, 3));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been given jump x2 for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given jump x2 for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        return true;
      } 
    }
    
    if(cmd.getName().equalsIgnoreCase("confuse")){
      if (args.length == 0) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 1));
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been confused for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
        return true;
      } else if (args.length == 1) {
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 1));
          sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been confused for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been confused for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          return true;
        }
      } else if (args.length == 2 && player.hasPermission("simpleextras.confuse.other") || player == null) {  
        Player target = Bukkit.getServer().getPlayer(args[0]);
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;        
        target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, mins, 1));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been confused for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been confused for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        return true;
      } 
    }
    
    if(cmd.getName().equalsIgnoreCase("slow")){
      if (args.length == 0) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1200, 2));
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been slowed for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
        return true;
      } else if (args.length == 1) {
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1200, 2));
          sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been slowed for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been slowed for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          return true;
        }
      } else if (args.length == 2 && player.hasPermission("simpleextras.slow.other") || player == null) {  
        Player target = Bukkit.getServer().getPlayer(args[0]);
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;        
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, mins, 2));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been slowed for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been slowed for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        return true;
      } 
    }
    
    if(cmd.getName().equalsIgnoreCase("superdig")){
      if (args.length == 0) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1200, 2));
        sender.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given SuperDig for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
        return true;
      } else if (args.length == 1) {
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
          sender.sendMessage(ChatColor.RED + args[0] + " is not online");
          return true;
        } else {
          target.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1200, 2));
          sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been given SuperDig for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given SuperDig for " + ChatColor.GREEN + "1" + ChatColor.GRAY + " minute");
          return true;
        }
      } else if (args.length == 2 && player.hasPermission("simpleextras.superdig.other") || player == null) {  
        Player target = Bukkit.getServer().getPlayer(args[0]);
        String min = args[1];
        int mintemp = Integer.parseInt( min );
        int mins = 1200 * mintemp;        
        target.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, mins, 2));
        sender.sendMessage(ChatColor.GREEN + "" + target.getDisplayName() + ChatColor.GRAY + " has been given SuperDig for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        target.sendMessage(ChatColor.GOLD + "* " + ChatColor.GRAY + "You have been given SuperDig for " + ChatColor.GREEN + min + ChatColor.GRAY + " minutes");
        return true;
      } 
    }

    if(cmd.getName().equalsIgnoreCase("magic")){
      StringBuilder sb = new StringBuilder();
      for (String arg : args)
        sb.append(arg + " ");          
          String[] temp = sb.toString().split(" ");
          String[] temp2 = Arrays.copyOfRange(temp, 0, temp.length);
          sb.delete(0, sb.length());
          for (String details : temp2)
          {
            sb.append(details);
            sb.append(" ");
          }
          String details = sb.toString();
          player.chat("" + ChatColor.MAGIC + details);
          return true;
    }

    if(cmd.getName().equalsIgnoreCase("gf")){
      if (player == null) {
        sender.sendMessage("this command can only be run by a player");
        return true;
      } else {
        if (args.length == 0) {
          Fireball fb = player.getWorld().spawn(player.getLocation().add(0, 1, 0), Fireball.class);
          fb.setShooter(player.getPlayer());
          fb.setYield(0);
          fb.setFireTicks(10000);
          fb.setIsIncendiary(true);          
          fb.setDirection(player.getLocation().getDirection().multiply(10));
          //.multiply(Integer.MAX_VALUE).subtract(new Vector(0,3,0)));
          return true;
        }
      }
    }

    return false;
    // TODO
    // - Console Output
  }
}
