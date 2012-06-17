package me.odium.simpleextras.listeners;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PListener implements Listener {

    public PListener(SimpleExtras instance) {
      Plugin plugin = instance;
      plugin.getServer().getPluginManager().registerEvents(this, plugin);  
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
        fb.setDirection(player.getLocation().getDirection().multiply(5));
      }      
    }
  }