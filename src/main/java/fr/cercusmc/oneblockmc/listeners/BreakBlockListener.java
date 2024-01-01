package fr.cercusmc.oneblockmc.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        World currentWorld = e.getPlayer().getWorld();
        if(ListenerUtils.denyActionBlock(currentWorld, e.getPlayer(), e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }
}
