package net.spence.minecraft.sunland;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;

public class SunlandListener implements Listener
{
    
    @EventHandler
    public void onLogin(PlayerLoginEvent event) 
    {
        SunlandHelp.SendHelp(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) 
    {
        Player p = event.getPlayer();
        
        // Check to see if this is a shop interaction
        Block b = event.getClickedBlock();
        if ((b != null) && (b.getType() == Material.CHEST)) {
            SunflowerShop ss = ShopSystem.FindShop(b);
            if (ss != null) {
                ss.Interact(p);
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Inventory inv = event.getInventory();
        if ((inv.getType() == InventoryType.CHEST) && (event.getWhoClicked() instanceof Player)) {
            Player player = (Player) event.getWhoClicked();
            
            // Is this a shop inventory event?
            SunflowerShop ss = ShopSystem.FindShop(inv);
            if (ss != null) {
                ss.HandleEvent(event, player);
            }
        }
    }
}
