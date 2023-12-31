package fr.cercusmc.oneblockmc.listeners;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Optional;

public class PlaceBlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        World currentWorld = e.getPlayer().getLocation().getWorld();
        if(currentWorld == null) return;
        if(!OneBlockMC.getInstance().getOverworld().getName().equals(currentWorld.getName())) return;

        // On récupère l'île où le bloc est posé
        Optional<Island> is = IslandUtils.getIslandByLocation(OneBlockMC.getIslands(), e.getBlockPlaced().getLocation());

        if(is.isPresent()) {

            // Si le joueur n'est pas sur son île => Annulation de l'event
            if(!IslandUtils.playerBelongToIsland(is.get(), e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                MessageUtil.sendMessage(e.getPlayer().getUniqueId(), OneBlockMC.getMessages().get("player_not_in_his_island"));
            }

        } else {
            // Cas où le joueur est en dehors des îles existantes
            if (e.getPlayer().isOp()) {
                e.setCancelled(true);
                MessageUtil.sendMessage(e.getPlayer().getUniqueId(), OneBlockMC.getMessages().get("player_not_in_his_island"));

            }
        }

    }
}
