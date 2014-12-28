package net.spence.minecraft.sunland;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sunland extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getLogger().info("Launched net.spence.minecraft.Sunland");
        Player[] coll = Bukkit.getOnlinePlayers();
        for (int i = 0; i < coll.length; i++) {
            SunlandHelp.SendHelp(coll[i]);
        }
        
        // Register to listen for player interact events
        getServer().getPluginManager().registerEvents(new SunlandListener(), this);
    }

    @Override
    public void onDisable()
    {
        Player[] coll = Bukkit.getOnlinePlayers();
        for (int i = 0; i < coll.length; i++) {
            coll[i].sendMessage("Bye from Sunland!");
        }
        getLogger().info("Closed net.spence.minecraft.Sunland");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args)
    {
        // Check to ensure this is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
        
        // Who is this player and where is he/she?
        Player player = (Player) sender;
        Location loc = player.getLocation();

        // Basic command
        if (cmd.getName().equalsIgnoreCase("basic")) {
            Sun.Forward(loc, 5);
            Builder.generateCube(loc, 3, Material.DIAMOND_BLOCK);
            
        // Skeleton command
        } else if (cmd.getName().equalsIgnoreCase("skeleton")) {
                
            // Spawn a skeleton five spaces ahead
            Sun.Forward(loc, 5);
            player.getWorld().spawnEntity(loc, EntityType.SKELETON);
            return true;
            
        // Maze command
        } else if (cmd.getName().equalsIgnoreCase("maze")) {

            // Build a maze of a specific x/y dimension, ahead 25 blocks 
            Sun.Forward(loc, 25);
            Builder.generateMaze(loc, 8);
            return true;
            
        // Test for a shop mod
        } else if (cmd.getName().equalsIgnoreCase("shop")) {
            SunflowerShop ss = ShopSystem.GetShop(player);
            ss.Interact(player);
            return true;
            
        // Give some coins for shop playing
        } else if (cmd.getName().equalsIgnoreCase("coins") || cmd.getName().equalsIgnoreCase("coin")) {
            if (args.length == 1) {
                int amount = Integer.parseInt(args[0]);
                Sun.GiveMoney(player, amount);
                player.sendMessage("Gained " + Integer.toString(amount) + " coins.");
            } else {
                player.sendMessage("Usage: /coins <amount>");
            }
            return true;
        }
        return false;
    }

}
