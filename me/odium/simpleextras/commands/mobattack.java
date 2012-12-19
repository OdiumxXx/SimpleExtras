package me.odium.simpleextras.commands;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class mobattack implements CommandExecutor {   

  public SimpleExtras plugin;
  public mobattack(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }
    // TARGET SELF (NO RADIUS)
    if (args.length == 0) {
      Player target = player;
      for (final Entity entity : target.getNearbyEntities(100, 100, 100)) {
        if (entity != null && entity instanceof Creature) {
          final Creature creature = ((Creature) entity);
          creature.setTarget(target);                
        }
      }
      sender.sendMessage(ChatColor.GREEN+target.getName()+" Targetted for attack!");
      return true;
      // MENUS
    } else if (args.length == 1 && args[0].equalsIgnoreCase("menu")) {
      sender.sendMessage(ChatColor.GOLD+"[Mob Assasins]");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack"+ChatColor.WHITE+" - Nearby Mobs target you");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack <player>"+ChatColor.WHITE+" - Nearby Mobs target player");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack <player> <#>"+ChatColor.WHITE+" - Mobs within # blocks target player");
      sender.sendMessage(ChatColor.GOLD+" /mobattack mobs"+ChatColor.WHITE+" - List possible Assasins");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack [c/e/s/p/] <player>"+ChatColor.WHITE+" - Send Assasins to kill player");      
      return true;
    } else if (args.length == 1 && args[0].equalsIgnoreCase("mobs")) {
      sender.sendMessage(ChatColor.GOLD+"[Mob Assasins]");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack -c <player> - "+ChatColor.WHITE+"CaveSpider Assasins");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack -e <player> - "+ChatColor.WHITE+"Enderman Assasins");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack -p <player> - "+ChatColor.WHITE+"PigZombie Assasins");
      sender.sendMessage(ChatColor.YELLOW+" /mobattack -s <player> - "+ChatColor.WHITE+"Spider Assasins");      
      sender.sendMessage(ChatColor.YELLOW+" /mobattack -w <player> - "+ChatColor.WHITE+"Wither Assasin");
      return true;

      // TARGET PLAYER (NO RADIUS)
    } else if (args.length == 1) {
      if (Bukkit.getPlayer(args[0]) == null) {
        sender.sendMessage(ChatColor.RED+args[0]+" not online");
        return true;
      }      
      Player target = Bukkit.getPlayer(args[0]);
      
      for (final Entity entity : target.getNearbyEntities(100, 100, 100)) {
        if (entity != null && entity instanceof Creature) {
          final Creature creature = ((Creature) entity);
          creature.setTarget(target);                
        }
      }
      sender.sendMessage(ChatColor.GREEN+target.getName()+" Targetted for attack!");
      return true;
      // ENDERMAN
    } else if (args.length == 2 && args[0].equalsIgnoreCase("-e")) {
      if (Bukkit.getPlayer(args[1]) == null) {
        sender.sendMessage(ChatColor.RED+args[1]+" is not online.");
        return true;            
      }
      Player target = Bukkit.getPlayer(args[1]);
      int EnderCount = 0;
      Location targetloc = target.getLocation();
      EntityType Enderman = EntityType.ENDERMAN;

      target.getWorld().spawnEntity(targetloc.add(9, 0, 6), Enderman);
      
      target.getWorld().spawnEntity(targetloc.add(8, 0, 7), Enderman);
      target.getWorld().spawnEntity(targetloc.add(7, 0, 8), Enderman);
      target.getWorld().spawnEntity(targetloc.add(6, 0, 9), Enderman);
      target.getWorld().spawnEntity(targetloc.subtract(9, 0, 6), Enderman);
      target.getWorld().spawnEntity(targetloc.subtract(8, 0, 7), Enderman);
      target.getWorld().spawnEntity(targetloc.subtract(7, 0, 8), Enderman);
      target.getWorld().spawnEntity(targetloc.subtract(6, 0, 9), Enderman);
      for (final Entity entity : target.getNearbyEntities(50, 50, 50)) {      // Shoo Mobs
        if (entity.getType().getName() == "Enderman") {
          EnderCount++;
          Enderman minion = (Enderman) entity;
          minion.setTarget(target);
          minion.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 2));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 2));
        }
      }
      sender.sendMessage(ChatColor.GREEN+""+EnderCount+ChatColor.YELLOW+" minions have been unleased upon "+ChatColor.GREEN+target.getName());
      return true;

      // PIG ZOMBIES
    } else if (args.length == 2 && args[0].equalsIgnoreCase("-p")) {
      if (Bukkit.getPlayer(args[1]) == null) {
        sender.sendMessage(ChatColor.RED+args[1]+" is not online.");
        return true;            
      }
      Player target = Bukkit.getPlayer(args[1]);
      int PigZombieCount = 0;          
      Location targetloc = target.getLocation();
      EntityType PigZombie = EntityType.PIG_ZOMBIE;

      target.getWorld().spawnEntity(targetloc.add(5, 0, 2), PigZombie);
      target.getWorld().spawnEntity(targetloc.add(4, 0, 3), PigZombie);
      target.getWorld().spawnEntity(targetloc.add(3, 0, 4), PigZombie);
      target.getWorld().spawnEntity(targetloc.add(2, 0, 5), PigZombie);
      target.getWorld().spawnEntity(targetloc.subtract(4, 0, 2), PigZombie);
      target.getWorld().spawnEntity(targetloc.subtract(3, 0, 3), PigZombie);
      target.getWorld().spawnEntity(targetloc.subtract(2, 0, 4), PigZombie);
      for (final Entity entity : target.getNearbyEntities(25, 25, 25)) {      // Shoo Mobs
        if (entity.getType().getName() == "PigZombie") {
          PigZombieCount++;
          PigZombie minion = (PigZombie) entity;
          minion.setTarget(target);
          minion.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 2));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 2));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
        }
      }
      sender.sendMessage(ChatColor.GREEN+""+PigZombieCount+ChatColor.YELLOW+" minions have been unleased upon "+ChatColor.GREEN+target.getName());
      return true;

      // SPIDER
    } else if (args.length == 2 && args[0].equalsIgnoreCase("-s")) {
      if (Bukkit.getPlayer(args[1]) == null) {
        sender.sendMessage(ChatColor.RED+args[1]+" is not online.");
        return true;            
      }
      Player target = Bukkit.getPlayer(args[1]);
      int SpiderCount = 0;          
      Location targetloc = target.getLocation();
      EntityType Spider = EntityType.SPIDER;

      target.getWorld().spawnEntity(targetloc.add(5, 0, 2), Spider);
      target.getWorld().spawnEntity(targetloc.add(4, 0, 3), Spider);
      target.getWorld().spawnEntity(targetloc.add(3, 0, 4), Spider);
      target.getWorld().spawnEntity(targetloc.add(2, 0, 5), Spider);
      target.getWorld().spawnEntity(targetloc.subtract(4, 0, 2), Spider);
      target.getWorld().spawnEntity(targetloc.subtract(3, 0, 3), Spider);
      target.getWorld().spawnEntity(targetloc.subtract(2, 0, 4), Spider);
      for (final Entity entity : target.getNearbyEntities(25, 25, 25)) {
        if (entity.getType().getName() == "Spider") {
          SpiderCount++;
          Spider minion = (Spider) entity;          
          minion.setTarget(target);
          minion.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 2));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 2));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));          
        }
      }
      sender.sendMessage(ChatColor.GREEN+""+SpiderCount+ChatColor.YELLOW+" minions have been unleased upon "+ChatColor.GREEN+target.getName());
      return true;

      // CAVE SPIDER
    } else if (args.length == 2 && args[0].equalsIgnoreCase("-c")) {
      if (Bukkit.getPlayer(args[1]) == null) {
        sender.sendMessage(ChatColor.RED+args[1]+" is not online.");
        return true;            
      }
      Player target = Bukkit.getPlayer(args[1]);
      int CaveSpiderCount = 0;          
      Location targetloc = target.getLocation();
      EntityType CaveSpider = EntityType.CAVE_SPIDER;

      target.getWorld().spawnEntity(targetloc.add(5, 0, 2), CaveSpider);
      target.getWorld().spawnEntity(targetloc.add(4, 0, 3), CaveSpider);
      target.getWorld().spawnEntity(targetloc.add(3, 0, 4), CaveSpider);
      target.getWorld().spawnEntity(targetloc.add(2, 0, 5), CaveSpider);
      target.getWorld().spawnEntity(targetloc.subtract(4, 0, 2), CaveSpider);
      target.getWorld().spawnEntity(targetloc.subtract(3, 0, 3), CaveSpider);
      target.getWorld().spawnEntity(targetloc.subtract(2, 0, 4), CaveSpider);
      for (final Entity entity : target.getNearbyEntities(20, 20, 20)) { 
        if (entity.getType().getName() == "CaveSpider") {
          CaveSpiderCount++;
          CaveSpider minion = (CaveSpider) entity;
          minion.setTarget(target);
          minion.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));
          minion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
        }
      }
      sender.sendMessage(ChatColor.GREEN+""+CaveSpiderCount+ChatColor.YELLOW+" minions have been unleased upon "+ChatColor.GREEN+target.getName());
      return true;

      
      
      
      // LOCAL ATTACK (WITH RADIUS)
    } else if (args.length == 2) {
      if (Bukkit.getPlayer(args[0]) == null) {
        sender.sendMessage(ChatColor.RED+args[1]+" is not online.");
        return true;            
      }
      Player target = Bukkit.getPlayer(args[0]);
      double radius = Double.parseDouble(args[1]);
      double EntCount = 0; 
      for (final Entity entity : target.getNearbyEntities(radius, radius, radius)) {  
        if (entity != null && entity instanceof Creature) {
          final Creature creature = ((Creature) entity);
          creature.setTarget(target);
          EntCount++;
        }
      }
      sender.sendMessage(ChatColor.GREEN+target.getName()+" Targetted for attack by "+EntCount+" creatures!");
      return true;
    }
    return true;
  }
}