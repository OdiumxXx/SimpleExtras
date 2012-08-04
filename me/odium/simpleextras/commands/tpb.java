package me.odium.simpleextras.commands;

import me.odium.simpleextras.SimpleExtras;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpb implements CommandExecutor {   

    public SimpleExtras plugin;
    public tpb(SimpleExtras plugin)  {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (player == null) {
            sender.sendMessage("Command must be run by a player");
            return true;
        }
        if (args.length == 0) {                
            sender.sendMessage(" Usage: /tpb <player>");
            return true;

        } else if(args.length == 1) {
            // setup variables
            Player target = Bukkit.getPlayer(args[0]);            
            World targetWorld = target.getWorld();
            Location targetLocation = target.getLocation();
            float explosionPower = 0;
            // Teleport player and player effects.
            targetWorld.createExplosion(player.getLocation(), explosionPower);
            targetWorld.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
            player.teleport(targetLocation);            
            targetWorld.createExplosion(player.getLocation(), explosionPower);
            targetWorld.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
            return true;
            // if command contains a flag
        } else if(args.length == 2 && args[0].contains("-")) { 
            // setup variables
            Player target = Bukkit.getPlayer(args[1]);
            Location targetLocation = target.getLocation();
            World targetWorld = target.getWorld();
            float explosionPower = 0;
            // Teleport player
            targetWorld.createExplosion(player.getLocation(), explosionPower);
            targetWorld.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
            player.teleport(targetLocation);
            
            // Check which flag is being used
            if (args[0].equalsIgnoreCase("-s")) { 
                targetWorld.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
                return true;    
            } else if(args[0].equalsIgnoreCase("-g")) {
                // setup variables
                targetWorld.createExplosion(player.getLocation(), explosionPower);
                targetWorld.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
                targetWorld.playEffect(player.getLocation(), Effect.GHAST_SHRIEK, 100);
                return true;            
            } else if(args[0].equalsIgnoreCase("-l")) {
                targetWorld.createExplosion(player.getLocation(), explosionPower);
                targetWorld.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
                targetWorld.strikeLightningEffect(player.getLocation().add(0, 150, 0));                
                return true;            
            } else if(args[0].equalsIgnoreCase("-a")) {
              targetWorld.createExplosion(player.getLocation(), explosionPower);
              targetWorld.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
              targetWorld.playEffect(player.getLocation(), Effect.GHAST_SHRIEK, 100);
              targetWorld.strikeLightningEffect(player.getLocation().add(0, 150, 0));                
              return true;            
          }
        }
        return true;
    }


}

