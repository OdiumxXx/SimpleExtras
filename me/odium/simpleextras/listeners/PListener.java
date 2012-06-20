package me.odium.simpleextras.listeners;

import java.util.List;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PListener implements Listener {
  
  public SimpleExtras plugin;
  public PListener(SimpleExtras plugin) {
    this.plugin = plugin;
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
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer(); 
// IF PLAYER HAS NOT PLAYED BEFORE
      if (!player.hasPlayedBefore()) {
       boolean GiveStarterKit = plugin.getConfig().getBoolean("NewPlayer.GiveStarterKit");
       if (GiveStarterKit == true) {       
        List<String> NewPlayerKit = plugin.getConfig().getStringList("NewPlayer.Kit");       
        for (String item:NewPlayerKit) {       
          String[] itemlist = item.split(", ");
          String mat = itemlist[0];
          String amnt = itemlist[1];
          int material = Integer.parseInt(mat);
          int amount = Integer.parseInt(amnt);
         player.getInventory().addItem(new ItemStack(material, amount));
        }
       }
       boolean WelcomeUser = plugin.getConfig().getBoolean("NewPlayer.WelcomeUser");
       if (WelcomeUser == true) {
         String Welcome = plugin.getConfig().getString("NewPlayer.WelcomeMessage").replace("%player%", player.getDisplayName());
          player.sendMessage(plugin.replaceColorMacros(Welcome)); 
       }
      }
    }
}