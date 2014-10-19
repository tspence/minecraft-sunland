package net.spence.minecraft.sunland;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
        // Basic command
        if (cmd.getName().equalsIgnoreCase("basic")) { 
            Player player = (Player) sender;

            // Figure out theplayer's location
            Location loc = player.getLocation();
            
            // Build a diamond block 5 spaces ahead of size 3
            loc.setZ(loc.getZ() + 5);
            Builder.generateCube(loc, 3, Material.DIAMOND_BLOCK);
            
        // Skeleton command
        } else if (cmd.getName().equalsIgnoreCase("skeleton")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                Player player = (Player) sender;

                // Figure out theplayer's location
                Location loc = player.getLocation();
                
                // Spawn a skeleton five spaces ahead
                loc.setZ(loc.getZ() + 5);
                player.getWorld().spawnEntity(loc, EntityType.SKELETON);
            }
            return true;
            
        // Maze command
        } else if (cmd.getName().equalsIgnoreCase("maze")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                Player player = (Player) sender;

                // Figure out theplayer's location
                Location loc = player.getLocation();
                
                // Spawn a skeleton five spaces ahead
                Builder.generateMaze(loc, 8);
            }
            return true;
            
        // Mazeclear command
        } else if (cmd.getName().equalsIgnoreCase("mazeclear")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                Player player = (Player) sender;

                // Figure out theplayer's location
                Location loc = player.getLocation();
                
                // Spawn a skeleton five spaces ahead
                Builder.BuildSurface(loc.getWorld(), loc.getBlockX() - 20, loc.getBlockY() - 1, loc.getBlockZ() - 20, loc.getBlockX() + 20, loc.getBlockY() + 4, loc.getBlockZ() + 20, Material.AIR);
            }
            return true;
        }
        return false;
    }

}
