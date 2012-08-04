package me.odium.simpleextras;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.odium.simpleextras.commands.admin;
import me.odium.simpleextras.commands.basics;
import me.odium.simpleextras.commands.bed;
import me.odium.simpleextras.commands.boom;
import me.odium.simpleextras.commands.creative;
import me.odium.simpleextras.commands.exp;
import me.odium.simpleextras.commands.findplayer;
import me.odium.simpleextras.commands.flame;
import me.odium.simpleextras.commands.fly;
import me.odium.simpleextras.commands.home;
import me.odium.simpleextras.commands.ignite;
import me.odium.simpleextras.commands.levelget;
import me.odium.simpleextras.commands.levelset;
import me.odium.simpleextras.commands.levelup;
import me.odium.simpleextras.commands.mobattack;
import me.odium.simpleextras.commands.owner;
import me.odium.simpleextras.commands.ranks;
import me.odium.simpleextras.commands.se;
import me.odium.simpleextras.commands.seen;
import me.odium.simpleextras.commands.seenfirst;
import me.odium.simpleextras.commands.sethome;
import me.odium.simpleextras.commands.survival;
import me.odium.simpleextras.commands.tpb;
import me.odium.simpleextras.commands.website;
import me.odium.simpleextras.commands.zoom;
import me.odium.simpleextras.commands.effects.blind;
import me.odium.simpleextras.commands.effects.confuse;
import me.odium.simpleextras.commands.effects.effects;
import me.odium.simpleextras.commands.effects.fireresistance;
import me.odium.simpleextras.commands.effects.jump;
import me.odium.simpleextras.commands.effects.noeffect;
import me.odium.simpleextras.commands.effects.slow;
import me.odium.simpleextras.commands.effects.slowdig;
import me.odium.simpleextras.commands.effects.speed;
import me.odium.simpleextras.commands.effects.superdig;
import me.odium.simpleextras.listeners.PListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

public class SimpleExtras extends JavaPlugin {
  public Logger log = Logger.getLogger("Minecraft");
  public SimpleExtras plugin;

  
  //Custom Config  
  private FileConfiguration HomesConfig = null;
  private File HomesConfigFile = null;

  public void reloadHomesConfig() {
    if (HomesConfigFile == null) {
      HomesConfigFile = new File(getDataFolder(), "HomesConfig.yml");
    }
    HomesConfig = YamlConfiguration.loadConfiguration(HomesConfigFile);

    // Look for defaults in the jar
    InputStream defConfigStream = getResource("HomesConfig.yml");
    if (defConfigStream != null) {
      YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
      HomesConfig.setDefaults(defConfig);
    }
  }
  public FileConfiguration getHomesConfig() {
    if (HomesConfig == null) {
      reloadHomesConfig();
    }
    return HomesConfig;
  }

  public void saveHomesConfig() {
    if (HomesConfig == null || HomesConfigFile == null) {
      return;
    }
    try {
      HomesConfig.save(HomesConfigFile);
    } catch (IOException ex) {
      Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + HomesConfigFile, ex);
    }
  }
  // End Custom Config


  public void onEnable(){
    log.info("[" + getDescription().getName() + "] " + getDescription().getVersion() + " enabled.");
    FileConfiguration cfg = getConfig();
    FileConfigurationOptions cfgOptions = cfg.options();
    cfgOptions.copyDefaults(true);
    cfgOptions.copyHeader(true);
    saveConfig();
    // declare new listener
    new PListener(this);
    // declare command executor
    this.getCommand("effects").setExecutor(new effects(this));
    this.getCommand("ignite").setExecutor(new ignite(this));
    this.getCommand("blind").setExecutor(new blind(this));    
    this.getCommand("confuse").setExecutor(new confuse(this));
    this.getCommand("jump").setExecutor(new jump(this));
    this.getCommand("slow").setExecutor(new slow(this));
    this.getCommand("speed").setExecutor(new speed(this));
    this.getCommand("superdig").setExecutor(new superdig(this));
    this.getCommand("slowdig").setExecutor(new slowdig(this));
    this.getCommand("fireresistance").setExecutor(new fireresistance(this));
    this.getCommand("exp").setExecutor(new exp(this));
    this.getCommand("levelget").setExecutor(new levelget(this));
    this.getCommand("levelset").setExecutor(new levelset(this));
    this.getCommand("levelup").setExecutor(new levelup(this));
    this.getCommand("creative").setExecutor(new creative(this));
    this.getCommand("survival").setExecutor(new survival(this));
    this.getCommand("boom").setExecutor(new boom(this));
    this.getCommand("bed").setExecutor(new bed(this));
    this.getCommand("admin").setExecutor(new admin(this));
    this.getCommand("basics").setExecutor(new basics(this));
    this.getCommand("owner").setExecutor(new owner(this));
    this.getCommand("findplayer").setExecutor(new findplayer(this));
    this.getCommand("fly").setExecutor(new fly(this));
    this.getCommand("se").setExecutor(new se(this));
    this.getCommand("seen").setExecutor(new seen(this));
    this.getCommand("seenfirst").setExecutor(new seenfirst(this));
    this.getCommand("ranks").setExecutor(new ranks(this));
    this.getCommand("mobattack").setExecutor(new mobattack(this));
    this.getCommand("flame").setExecutor(new flame(this));
    this.getCommand("home").setExecutor(new home(this));
    this.getCommand("sethome").setExecutor(new sethome(this));
    this.getCommand("website").setExecutor(new website(this));
    this.getCommand("tpb").setExecutor(new tpb(this));
    this.getCommand("zoom").setExecutor(new zoom(this));
    this.getCommand("noeffect").setExecutor(new noeffect(this));
  }

  public void onDisable(){ 
    log.info("[" + getDescription().getName() + "] " + getDescription().getVersion() + " disabled.");	
  }

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

  public String replaceColorMacros(String str) {
    str = str.replace("`r", ChatColor.RED.toString());
    str = str.replace("`R", ChatColor.DARK_RED.toString());        
    str = str.replace("`y", ChatColor.YELLOW.toString());
    str = str.replace("`Y", ChatColor.GOLD.toString());
    str = str.replace("`g", ChatColor.GREEN.toString());
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
}

//public void flyDisable(final Player target1, int mins) {
//  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
//    public void run() {
//      target1.setAllowFlight(false);          
//      target1.setFlying(false);      
//    }
//  }, mins); 
//}

//public void flyEnable(final Player target1, int mins) {
//  this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
//    public void run() {
//      target1.setAllowFlight(true);          
//      target1.setFlying(true);
//    }
//  }, mins); 
//}
//  

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

//    if(cmd.getName().equalsIgnoreCase("gf")){
//      if (player == null) {
//        sender.sendMessage("this command can only be run by a player");
//        return true;
//      } else {
//        if (args.length == 0) {
//          Fireball fb = player.getWorld().spawn(player.getLocation().add(0, 1, 0), Fireball.class);
//          fb.setShooter(player.getPlayer());
//          fb.setYield(0);
//          fb.setFireTicks(10000);
//          fb.setIsIncendiary(true);          
//          fb.setDirection(player.getLocation().getDirection().multiply(10));
//          //.multiply(Integer.MAX_VALUE).subtract(new Vector(0,3,0)));
//          return true;
//        }
//      }
//    }
