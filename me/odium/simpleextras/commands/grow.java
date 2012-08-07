package me.odium.simpleextras.commands;

import java.util.Random;
import me.odium.simpleextras.SimpleExtras;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class grow implements CommandExecutor {   

  public SimpleExtras plugin;
  public grow(SimpleExtras plugin)  {
    this.plugin = plugin;
  }

  public boolean growTrees(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock();    
    int tree = 0;
    Random rnd = new Random();

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.SAPLING) {
            int dataValue = b.getData();
            b.setTypeId(0);
            if (dataValue == 0) {
              b.getWorld().generateTree(b.getLocation(), TreeType.TREE);  
            } else if (dataValue == 1) {
              b.getWorld().generateTree(b.getLocation(), TreeType.REDWOOD);  
            } else if (dataValue == 2) {
              b.getWorld().generateTree(b.getLocation(), TreeType.BIRCH);  
            } else if (dataValue == 3) {
              b.getWorld().generateTree(b.getLocation(), TreeType.JUNGLE);  
            }             
            tree++;
            for (int x1 = -3; x1 <= 3; x1++) {
              for (int z1 = -3; z1 <= 3; z1++) {
                Block toHandle =  b.getWorld().getBlockAt(b.getX() + x1, b.getY(), b.getZ() + z1);
                if (toHandle.getRelative(BlockFace.DOWN).getType() == Material.GRASS) { // Block beneath is grass
                  if (rnd.nextInt(100) < 60) {    // Random chance
                    toHandle.setType(Material.LONG_GRASS);
                    toHandle.setData((byte) 1);
                  } else if (rnd.nextInt(100) < 8) {    // Random chance
                    toHandle.setType(Material.YELLOW_FLOWER);
                  } else if (rnd.nextInt(100) < 16) {    // Random chance
                    toHandle.setType(Material.RED_ROSE);
                  }
                }
              }
            }
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+tree+" trees");
    return true;
  }

  public boolean growCrops(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int crops = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.CROPS) {
            int dataValue = b.getData();

            if (dataValue == 0) {
              b.setData((byte) 7);
              crops++;
            } else if (dataValue == (byte) 7) {
              // DO NOTHING
            }
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+crops+" crops");
    return true;
  }

  public boolean growMelons(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int melons = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.MELON_STEM) {
            int dataValue = b.getData();

            if (dataValue == 0) {
              b.setData((byte) 7);
              melons++;
            } else if (dataValue == (byte) 7) {
              // DO NOTHING
            }
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+melons+" melons");
    return true;
  }

  public boolean growPumpkins(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int pumpkins = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.PUMPKIN_STEM) {
            int dataValue = b.getData();

            if (dataValue == 0) {
              b.setData((byte) 7);
              pumpkins++;
            } else if (dataValue == (byte) 7) {
              // DO NOTHING
            }            
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+pumpkins+" pumpkins");
    return true;
  }



  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }
    int radius = 0;

    if (args.length == 0) {      
      radius = 20;
      growTrees(player, args, radius);
      growCrops(player, args, radius);
      growPumpkins(player, args, radius);
      growMelons(player, args, radius);      
      return true;      
    } else if (args[0].equalsIgnoreCase("-t")) {
      if (args.length == 1) {
        radius = 20;
        growTrees(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growTrees(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-c")) {
      if (args.length == 1) {
        radius = 20;
        growCrops(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growCrops(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-m")) {
      if (args.length == 1) {
        radius = 20;
        growMelons(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growMelons(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-p")) {
      if (args.length == 1) {
        radius = 20;
        growPumpkins(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growPumpkins(player, args, radius);
        return true;
      }
    } else if (args.length == 1) {
      radius = Integer.parseInt(args[0]);
      growTrees(player, args, radius);
      growCrops(player, args, radius);
      growPumpkins(player, args, radius);
      growMelons(player, args, radius);      
      return true;
    } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
      sender.sendMessage(" /Grow [-t/-c/-m/-p] [radius]");
      return true;
    }
    return true;
  }
}