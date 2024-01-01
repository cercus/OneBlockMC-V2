package fr.cercusmc.oneblockmc.listeners;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;

import java.util.Optional;

public class ListenerUtils {

    public static boolean denyActionBlock(World world, Player player, Location locBlock) {
        if(world == null) return false;
        if(!OneBlockMC.getInstance().getOverworld().getName().equals(world.getName())) return false;

        // On récupère l'île où le bloc est posé
        Optional<Island> is = IslandUtils.getIslandByLocation(OneBlockMC.getIslands(), locBlock);

        if(is.isPresent()) {

            // Si le joueur n'est pas sur son île => Annulation de l'event
            if(!IslandUtils.playerIsInEffectiveIsland(is.get(), player.getUniqueId(), locBlock)) {

                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("player_not_in_his_island"));
                return true;
            }

        } else {
            // Cas où le joueur est en dehors des îles existantes
            if (player.isOp()) {

                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("player_not_in_his_island"));
                return true;
            }
        }
        return false;
    }
}
