package net.spence.minecraft.sunland;

import org.bukkit.entity.Player;

public class SunlandHelp
{
    public static void SendHelp(Player p)
    {
        p.sendMessage("Welcome to Sunland!");
        p.sendMessage("Commands:");
        p.sendMessage("  basic - Basic command");
        p.sendMessage("  basic2 - Basic2 command");
        p.sendMessage("  skeleton - Summon a skeleton in front of you");
    }
}
