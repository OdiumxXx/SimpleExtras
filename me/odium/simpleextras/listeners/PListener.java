package me.odium.simpleextras.listeners;

import java.util.List;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
		if(proj.getShooter() instanceof Player && proj.getType().equals(EntityType.SNOWBALL)){
			Player player = (Player) proj.getShooter();
			if (SimpleExtras.Snowball_Grenade.containsKey(player)) {			

				Location loc = proj.getLocation();

				if (SimpleExtras.Snowball_Grenade.get(player) == false) {

					loc.getWorld().createExplosion(loc, 3); // Create typical TNT detonation.

				} else {

					loc.getWorld().createExplosion(loc, 0); // Create explosion which does no damage


					List<Entity> entities2 = proj.getNearbyEntities(2, 2, 2); // Get entities within 2 blocks of the explosion
					List<Entity> entities3 = proj.getNearbyEntities(3, 3, 3); // Get entities within 4 blocks of the explosion
					List<Entity> entities4 = proj.getNearbyEntities(4, 4, 4); // Get entities within 4 blocks of the explosion
					List<Entity> entities5 = proj.getNearbyEntities(5, 5, 5); // Get entities within 4 blocks of the explosion

					for (Entity entity : entities2) { // for every entity WITHIN 2 BLOCKS
						if (entity instanceof LivingEntity) { // if living entity
							((LivingEntity)entity).damage(20); // kill the entity
							entities3.remove(entity); // remove the entity from entities2 list	
						}
					}
					
					for (Entity entity : entities3) { // for every entity WITHIN 3 BLOCKS
						if (entity instanceof LivingEntity) { // if living entity
							((LivingEntity)entity).damage(10); // kill the entity
							entities4.remove(entity); // remove the entity from entities2 list	
						}
					}
					
					for (Entity entity : entities4) { // for every entity WITHIN 4 BLOCKS
						if (entity instanceof LivingEntity) { // if living entity
							((LivingEntity)entity).damage(6); // kill the entity
							entities5.remove(entity); // remove the entity from entities2 list	
						}
					}

					for (Entity entity : entities5) { // for every entity within 5 BLOCKS
						if (entity instanceof LivingEntity) { // if living entity
							((LivingEntity)entity).damage(3); // damage the player
						}
					}

				}
			}
		}
	}
	

}