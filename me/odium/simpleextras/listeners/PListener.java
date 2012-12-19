package me.odium.simpleextras.listeners;

import java.util.List;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
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

          //          if (mat.contains(":")) {
          //            String[] matWithData = mat.split(":");
          //            String material = matWithData[0];
          //            String datavalue = matWithData[1];            
          //          }

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

  @EventHandler
  public void onPlayerThrowSnowball(ProjectileHitEvent event){

    Projectile proj = event.getEntity();
    // IF PROJECTILE IS SNOWBALL
    if(proj.getShooter() instanceof Player && proj.getType().equals(EntityType.SNOWBALL)){
      Player player = (Player) proj.getShooter();
      if (SimpleExtras.Snowball_Grenade.containsKey(player)) {			

        Location loc = proj.getLocation();

        if (SimpleExtras.Snowball_Grenade.get(player) == false) {

          loc.getWorld().createExplosion(loc, 4); // Create typical TNT detonation.

        } else {

          loc.setPitch(0);
          loc.setYaw(0);          
          plugin.Blast_Mode.put(loc, 0);
          loc.getWorld().createExplosion(loc, 4); // Create explosion which does no damage
        }
      }
      // IF PROJECTILE IS ARROW
    } else if(proj.getShooter() instanceof Player && proj.getType().equals(EntityType.ARROW)){
      Player player = (Player) proj.getShooter();
      if (SimpleExtras.Arrow_Grenade.containsKey(player)) {      

        Location loc = proj.getLocation();

        if (SimpleExtras.Arrow_Grenade.get(player) == false) {

          loc.getWorld().createExplosion(loc, 4); // Create typical TNT detonation.

        } else {

          loc.setPitch(0);
          loc.setYaw(0);          
          plugin.Blast_Mode.put(loc, 0);
          loc.getWorld().createExplosion(loc, 4); // Create explosion which does no damage
        }
      }
    }
  }

  
  @EventHandler(priority = EventPriority.LOW) 
  public void onExplosionEvent(final EntityExplodeEvent event) {

    Location eventLocation = event.getLocation();



    if (plugin.Blast_Mode.containsKey(eventLocation)) {

      Integer BlastMode = plugin.Blast_Mode.get(eventLocation);
      if (BlastMode == 0) {
        // GET BLOCKS EFFECT BY AN EXPLOSION
        List<Block> blockList = event.blockList();
        int len = blockList.size();
        // FOR EVERY BLOCK EFFECTED BY EXPLOSION
        for(int i = 0; i < len; i++) {
          // remove block from damage list
          blockList.remove(i);
          i--;
          len--;
        }

      } else if (BlastMode == 1) {
        // GET BLOCKS EFFECT BY AN EXPLOSION
        List<Block> blockList = event.blockList();
        int len = blockList.size();
        // FOR EVERY BLOCK EFFECTED BY EXPLOSION
        for(int i = 0; i < len; i++) {
          // remove block from damage list
          blockList.remove(i);
          i--;
          len--;
        }

      } else if (BlastMode == 2) {
        // DO NOTHING
      } else if (BlastMode == 3) {
        // NO DAMAGE PLAYER and then...
        event.setYield(0);
      } else if (BlastMode == 4) {
        event.setYield(0);
      }
    }

  }


}