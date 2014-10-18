package net.spence.minecraft.sunland;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class SunlandListener implements Listener
{
    
    @EventHandler
    public void onLogin(PlayerLoginEvent event) 
    {
        SunlandHelp.SendHelp(event.getPlayer());
    }
}
