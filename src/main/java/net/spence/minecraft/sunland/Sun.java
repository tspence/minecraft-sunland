package net.spence.minecraft.sunland;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Sun
{
    public static BlockFace GetFacing(Location loc)
    {
        Vector v = loc.getDirection();
        double x = v.getX();
        double z = v.getZ();
        if (Math.abs(x) > Math.abs(z)) {
            if (x > 0.0) {
                return BlockFace.EAST;
            } else {
                return BlockFace.WEST;
            }
        } else {
            if (z > 0.0) {
                return BlockFace.SOUTH;
            } else {
                return BlockFace.NORTH;
            }
        }
    }
    
    public static void Forward(Location loc, int blocks)
    {
        BlockFace bf = GetFacing(loc);
        if (bf == BlockFace.SOUTH) {
            loc.setZ(loc.getZ() + blocks);
        } else if (bf == BlockFace.EAST) {
            loc.setX(loc.getX() + blocks);
        } else if (bf == BlockFace.NORTH) {
            loc.setZ(loc.getZ() - blocks);
        } else if (bf == BlockFace.WEST) {
            loc.setX(loc.getX() - blocks);
        }
    }
    
    public static void GiveMoney(Player p, int amount)
    {
        ItemStack money = new ItemStack(Material.DOUBLE_PLANT, amount);
        p.getInventory().addItem(money);
    }
    
    public static boolean TakeMoney(Player p, int amount)
    {
        // First count all money in the player's inventory
        int money = CountMoney(p);
        if (money < amount) return false;
        
        // Now subtract that amount of money
        Inventory inv = p.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if ((item != null) && (item.getType() == Material.DOUBLE_PLANT)) {
                int amt_this_stack = item.getAmount();
                int take = Math.min(amount, amt_this_stack);
                amount -= take;
                if (amt_this_stack == take) {
                    inv.setItem(i, null);
                } else {
                    item.setAmount(amt_this_stack - take);
                    break;
                }
            }
        }
        return true;
    }

    public static int CountMoney(Player p)
    {
        Inventory inv = p.getInventory();
        int count = 0;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if ((item != null) && (item.getType() == Material.DOUBLE_PLANT)) {
                count += item.getAmount();
            }
        }
        return count;
    }
}
