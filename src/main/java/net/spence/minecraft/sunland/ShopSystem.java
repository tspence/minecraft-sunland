package net.spence.minecraft.sunland;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopSystem
{
    protected static ArrayList<SunflowerShop> _all_shops = null;
    
    public static SunflowerShop GetShop(Player p)
    {
        // Ensure shops exist
        if (_all_shops == null) {
            _all_shops = new ArrayList<SunflowerShop>();
        }
        
        // Build this shop
        String name = "Player " + p.getName() + " Shop #" + Integer.toString(_all_shops.size() + 1);
        SunflowerShop ss = new SunflowerShop(p, name);
        _all_shops.add(ss);
        return ss;
    }
    
    public static SunflowerShop FindShop(Block b)
    {
        Location loc = b.getLocation();
        
        // Ensure shops exist
        if (_all_shops != null) {
            for (int i = 0; i < _all_shops.size(); i++) {
                SunflowerShop ss = _all_shops.get(i);
                if (ss.IsShop(loc)) {
                    return ss;
                }
            }
        }
        
        // Not a shop
        return null;
    }
    
    public static SunflowerShop FindShop(Inventory inv)
    {
        // Ensure shops exist
        if (_all_shops != null) {
            for (int i = 0; i < _all_shops.size(); i++) {
                SunflowerShop ss = _all_shops.get(i);
                if (ss.IsShop(inv)) {
                    return ss;
                }
            }
        }
        
        // Not a shop
        return null;
    }
}
