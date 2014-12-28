package net.spence.minecraft.sunland;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SunflowerShop
{
    // Persisted data
    protected String _owner;
    protected int _shopX, _shopY, _shopZ;
    protected String _name;
    protected ItemStack[] _item;
    protected int[] _price;
    protected int _coins;

    // Non persisted data
    protected Inventory _setup;
    protected Inventory _shop;

    public SunflowerShop(Player p, String Name)
    {
        Server s = Bukkit.getServer();
        
        // Create the shop
        _setup = s.createInventory(null, 18, Name + " Setup");
        _shop = s.createInventory(null, 9, Name);
        _item = new ItemStack[9];
        _price = new int[9];
        for (int i = 0; i < 9; i++) {
            _item[i] = null;
            _price[i] = 0;
        }
        UpdateShop();
        
        // This is how much money the store has earned
        _coins = 0;
        
        // Create the chest that will trigger this shop, one block forward
        Location l = p.getLocation();
        BlockFace dir = Sun.GetFacing(l);
        Sun.Forward(l, 1);
        World w = l.getWorld();
        Block b = w.getBlockAt(l);
        b.setType(Material.CHEST);
        
        // Keep track of shop location        
        _shopX = l.getBlockX();
        _shopY = l.getBlockY();
        _shopZ = l.getBlockZ();
        
        // Record our owner
        _owner = p.getName();
    }
    
    public void setName(String name)
    {
        _name = name;
    }
    
    public String getName()
    {
        return _name;
    }
    
    public void Interact(Player p)
    {
        if (IsOwner(p)) {
            ConfigureShop(p);
        } else {
            UseShop(p);
        }
    }
    
    private void ConfigureShop(Player p)
    {
        if (p != null) {
            
            // Are there any coins to pick up?
            if (_coins > 0) {
                p.sendMessage("Welcome back!  Your shop has earned " + Integer.toString(_coins) + " while you were away.");
                Sun.GiveMoney(p, _coins);
                _coins = 0;
            }
            p.openInventory(_setup);
        }
    }
    
    private void UseShop(Player p)
    {
        if (p != null) p.openInventory(_shop);
    }
    
    public boolean IsShop(Location loc)
    {
        return (_shopX == loc.getBlockX() && _shopY == loc.getBlockY() && _shopZ == loc.getBlockZ());
    }
    
    public boolean IsShop(Inventory inv)
    {
        return (inv.equals(_setup) || inv.equals(_shop));
    }
    
    public boolean IsOwner(Player p)
    {
        return (p != null && p.getName().equals(_owner));
    }
    
    private void HandleSetupEvent(InventoryClickEvent event, Player p)
    {
        // User is placing an item for sale - see if they're clicking a valid slot
        int slot = event.getRawSlot();
        if (slot < 0 || slot >= 18) return;
        
        // Are they setting up an item?
        if (slot < 9) {
            ItemStack s = event.getCursor().clone();
            _item[slot] = s;
            _price[slot] = 0;
            
        // Owner is changing the price of an item
        } else if (slot < 18) {
            slot = slot - 9;
            _price[slot]++;
        }
        
        // Always cancel the event and update the inventory
        UpdateShop();
        event.setCancelled(true);
    }
    
    private void UpdateShop()
    {
        // Go through items
        for (int i = 0; i < 9; i++) {
            if (_item[i] != null) {
                _setup.setItem(i, _item[i]);
                String price = "Price: " + Integer.toString(_price[i]) + " coins";
                setInvItem(_setup, i + 9, Material.DOUBLE_PLANT, price, null);
                setInvItem(_shop, i, _item[i].getType(), price, null);
            }
        }
    }

    private void HandlePurchase(InventoryClickEvent event, Player p)
    {
        // User is placing an item for sale - see if they're clicking a valid slot
        int slot = event.getRawSlot();
        if (slot < 0 || slot >= 9) return;

        // What did the player try to purchase?
        if (_item[slot] != null) {
            
            // You're going to purchase this item
            ItemStack purchase = _item[slot].clone();
            if (Sun.TakeMoney(p, _price[slot])) {
                p.sendMessage("You purchased item # " + Integer.toString(slot) + " material " + purchase.getType().toString() + " for price " + Integer.toString(_price[slot]));
                p.getInventory().addItem(purchase);
                _coins += _price[slot];
            }
        }
        
        // Always cancel the event
        event.setCancelled(true);
    }
    
    public void HandleEvent(InventoryClickEvent event, Player p)
    {
        if (IsOwner(p)) {
            HandleSetupEvent(event, p);
        } else {
            HandlePurchase(event, p);
        }
    }

    private void setInvItem(Inventory i, int slot, Material m, String name, ArrayList<String> lore)
    {
        ItemStack is = new ItemStack(m, 1);
        ItemMeta im = is.getItemMeta();
        if (im != null) {
            im.setDisplayName(name);
            im.setLore(lore);
            is.setItemMeta(im);
        }
        i.setItem(slot, is);
    }
}
