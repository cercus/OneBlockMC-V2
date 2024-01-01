package fr.cercusmc.oneblockmc.listeners;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;
import java.util.Optional;

public class PlaceBlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        World currentWorld = e.getPlayer().getLocation().getWorld();

        if(ListenerUtils.denyActionBlock(currentWorld, e.getPlayer(), e.getBlockPlaced().getLocation())) {
            e.setCancelled(true);
        }

    }
}
