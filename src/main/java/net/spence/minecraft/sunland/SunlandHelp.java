package net.spence.minecraft.sunland;

import org.bukkit.entity.Player;

public class SunlandHelp
{
    public static void SendHelp(Player p)
    {
        p.sendMessage("Welcome to Sunland!");
        p.sendMessage("Commands:");
        p.sendMessage("  basic - Basic command");
        p.sendMessage("  cube - Creates a cube of diamonds");
        p.sendMessage("  maze - Creates a maze in front of you");
        p.sendMessage("  shop - Configure a shop");
        p.sendMessage("  skeleton - Summon a skeleton in front of you");
        p.sendMessage("  coins <amount> - Create a number of coins for using the shop system");
    }
}
