package me.odium.simpleextras.commands;

import java.util.Random;
import me.odium.simpleextras.SimpleExtras;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
              if (rnd.nextInt(100) > 90) {    // Random chance (10%)
                b.getWorld().generateTree(b.getLocation(), TreeType.JUNGLE);
              } else {
                b.getWorld().generateTree(b.getLocation(), TreeType.SMALL_JUNGLE);
              }
            }             
            tree++;
            for (int x1 = -3; x1 <= 3; x1++) {
              for (int z1 = -3; z1 <= 3; z1++) {
                Block toHandle =  b.getWorld().getBlockAt(b.getX() + x1, b.getY(), b.getZ() + z1);
                if (toHandle.getRelative(BlockFace.DOWN).getType() == Material.GRASS && toHandle.getType() != Material.SAPLING) { // Block beneath is grass, but not sappling position
                  if (rnd.nextInt(100) < 30) {    // Random chance
                    toHandle.setType(Material.LONG_GRASS);
                    toHandle.setData((byte) 1);
                  } else if (rnd.nextInt(100) == 6) {    // Random chance
                    toHandle.setType(Material.YELLOW_FLOWER);
                  } else if (rnd.nextInt(100) == 2) {    // Random chance
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

            if (dataValue != 7) {
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

            if (dataValue != 7) {
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

            if (dataValue != 7) {
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

  public boolean growCarrots(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int carrots = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.CARROT) {
            int dataValue = b.getData();

            if (dataValue != 7) {
              b.setData((byte) 7);
              carrots++;
            } else if (dataValue == (byte) 7) {
              // DO NOTHING
            }
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+carrots+" carrots");
    return true;
  }

  public boolean growPotatoes(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int Potatoes = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.POTATO) {
            int dataValue = b.getData();

            if (dataValue != 7) {
              b.setData((byte) 7);
              Potatoes++;
            } else if (dataValue == (byte) 7) {
              // DO NOTHING
            }
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+Potatoes+" potatoes");
    return true;
  }

  public boolean growCoCoa(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int cocoa = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.COCOA) {
            int dataValue = b.getData();

            // west
            if (dataValue == 0) {
              b.setData((byte) 8);
              cocoa++;
            } else if (dataValue == 4) {
              b.setData((byte) 8);
              cocoa++;

              // north
            } else  if (dataValue == 1) {
              b.setData((byte) 9);
              cocoa++;
            } else  if (dataValue == 5) {
              b.setData((byte) 9);
              cocoa++;  

              // east
            } else if (dataValue == 2) {
              b.setData((byte) 10);
              cocoa++;
            } else if (dataValue == 6) {
              b.setData((byte) 10);
              cocoa++;            

              //South
            } else if (dataValue == 3) {
              b.setData((byte) 11);
              cocoa++;
            } else if (dataValue == 7) {
              b.setData((byte) 11);
              cocoa++;
            }
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+cocoa+" cocoa plants");
    return true;
  }
  
  public boolean growNetherWart(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int netherWarts = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (playerCenter.getRelative(x,y,z).getType() == Material.NETHER_WARTS) {
            int dataValue = b.getData();

            if (dataValue != 3) {
              b.setData((byte) 3);
              netherWarts++;
            } else if (dataValue == (byte) 7) {
              // DO NOTHING
            }
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+netherWarts+" netherwarts");
    return true;
  }
  
  public boolean growSugarCane(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int sugarCane = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (b.getType() == Material.SUGAR_CANE_BLOCK) {
            Location caneLocation = b.getLocation(); 

            if (caneLocation.clone().subtract(0, 1, 0).getBlock().getType() == Material.SUGAR_CANE_BLOCK && caneLocation.clone().subtract(0,2,0).getBlock().getType() != Material.SUGAR_CANE_BLOCK && caneLocation.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
              caneLocation.clone().add(0, 1, 0).getBlock().setType(Material.SUGAR_CANE_BLOCK);
              sugarCane++;
            } else if (caneLocation.clone().subtract(0,1,0).getBlock().getType() != Material.SUGAR_CANE_BLOCK && caneLocation.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
              caneLocation.clone().add(0,1,0).getBlock().setType(Material.SUGAR_CANE_BLOCK);
              caneLocation.clone().add(0,2,0).getBlock().setType(Material.SUGAR_CANE_BLOCK);
              sugarCane++;
            }          
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+sugarCane+" SugarCane");
    return true;
  }
  
  public boolean growCactus(Player player, String[] args, int radius) {
    Block playerCenter = player.getLocation().getBlock(); // Get Centrepoint (Player)    
    int cactus = 0;    

    for (int x = -radius; x < radius; x++) {
      for (int y = -radius; y < radius; y++) {
        for (int z = -radius; z < radius; z++) {
          Block b = playerCenter.getRelative(x,y,z);

          if (b.getType() == Material.CACTUS) {
            Location caneLocation = b.getLocation(); 

            if (caneLocation.clone().subtract(0, 1, 0).getBlock().getType() == Material.CACTUS && caneLocation.clone().subtract(0,2,0).getBlock().getType() != Material.CACTUS && caneLocation.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
              caneLocation.clone().add(0, 1, 0).getBlock().setType(Material.CACTUS);
              cactus++;
            } else if (caneLocation.clone().subtract(0,1,0).getBlock().getType() != Material.CACTUS && caneLocation.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
              caneLocation.clone().add(0,1,0).getBlock().setType(Material.CACTUS);
              caneLocation.clone().add(0,2,0).getBlock().setType(Material.CACTUS);
              cactus++;
            }          
          }
        }
      }
    }
    player.sendMessage(ChatColor.GREEN+"Grown "+cactus+" cactus");
    return true;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {    
    Player player = null;
    if (sender instanceof Player) {
      player = (Player) sender;
    }
    
    if (player == null) {
      sender.sendMessage(ChatColor.RED+"This command can only be run by a player");
      return true;
    }
    
    int radius = 0;

    if (args.length == 0) {      
      radius = 20;
      growTrees(player, args, radius);
      growCrops(player, args, radius);
      growPumpkins(player, args, radius);
      growMelons(player, args, radius);
      growCarrots(player, args, radius);
      growPotatoes(player, args, radius);
      growCoCoa(player, args, radius);
      growNetherWart(player, args, radius);
      growSugarCane(player, args, radius);
      growCactus(player, args, radius);
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
    } else if (args[0].equalsIgnoreCase("-pu")) {
      if (args.length == 1) {
        radius = 20;
        growPumpkins(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growPumpkins(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-po")) {
      if (args.length == 1) {
        radius = 20;
        growPotatoes(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growPotatoes(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-ca")) {
      if (args.length == 1) {
        radius = 20;
        growCarrots(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growCarrots(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-co")) {
      if (args.length == 1) {
        radius = 20;
        growCoCoa(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growCoCoa(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-n")) {
      if (args.length == 1) {
        radius = 20;
        growNetherWart(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growNetherWart(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-s")) {
      if (args.length == 1) {
        radius = 20;
        growSugarCane(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growSugarCane(player, args, radius);
        return true;
      }
    } else if (args[0].equalsIgnoreCase("-cac")) {
      if (args.length == 1) {
        radius = 20;
        growCactus(player, args, radius);
        return true;
      } else if (args.length == 2) {
        radius = Integer.parseInt(args[1]);
        growCactus(player, args, radius);
        return true;
      }


    } else if (args.length == 1 && !args[0].equalsIgnoreCase("help")) {

      for (char c : args[0].toCharArray()) {       
        if (!Character.isDigit(c)) {
          sender.sendMessage(ChatColor.WHITE+args[0]+ChatColor.RED+" not a Radius or recognized paramater [See /grow help]");
          return true;
        }
      }

      radius = Integer.parseInt(args[0]);
      growTrees(player, args, radius);
      growCrops(player, args, radius);
      growPumpkins(player, args, radius);
      growMelons(player, args, radius);
      growCarrots(player, args, radius);
      growPotatoes(player, args, radius);
      growCoCoa(player, args, radius);
      growNetherWart(player, args, radius);
      growSugarCane(player, args, radius);
      growCactus(player, args, radius);
      return true;


    } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
      sender.sendMessage(ChatColor.GOLD+"[ "+ChatColor.WHITE+"Grow"+ChatColor.GOLD+" ]");
      sender.sendMessage(ChatColor.YELLOW+"[] = Optional");
      sender.sendMessage(ChatColor.GOLD+" /Grow [-t/-c/-m/-pu/-po/-ca/-co/-n/-s/cac] [radius]");
      sender.sendMessage(ChatColor.YELLOW+"Flags");
      sender.sendMessage(" -t (Trees)");
      sender.sendMessage(" -c (Crops)");
      sender.sendMessage(" -m (Melons)");
      sender.sendMessage(" -pu (Pumpkins)");
      sender.sendMessage(" -po (Potatoes)");
      sender.sendMessage(" -ca (Carrots)");      
      sender.sendMessage(" -co (CoCoa)");
      sender.sendMessage(" -n (NetherWart)");
      sender.sendMessage(" -s (SugarCane)");
      sender.sendMessage(" -cac (Cactus)");
      return true;


    }
    return true;
  }

}