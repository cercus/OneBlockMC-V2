package fr.cercusmc.oneblockmc.listeners;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class MovePlayerListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        World world = e.getPlayer().getWorld();
        if(!OneBlockMC.getInstance().getOverworld().getName().equals(world.getName())) return;

        Location from = e.getFrom();
        Location to = e.getTo();

        Optional<Island> isFrom = IslandUtils.getIslandByLocation(OneBlockMC.getIslands(), from);
        Optional<Island> isTo = IslandUtils.getIslandByLocation(OneBlockMC.getIslands(), to);
        if(isFrom.isEmpty() && isTo.isEmpty()) return;

        if(isFrom.isPresent() && isTo.isPresent() && isFrom.get().equals(isTo.get())) return;

        isFrom.ifPresent(island -> MessageUtil.sendMessage(e.getPlayer().getUniqueId(), OneBlockMC.getMessages().get("quit_island"), Collections.singletonMap("%player%", Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(island.getId()))).getName())));


        if(isTo.isPresent() && isTo.get().getBans().getPlayers().contains(e.getPlayer().getUniqueId().toString())) {
            MessageUtil.sendMessage(e.getPlayer().getUniqueId(), OneBlockMC.getMessages().get("ban_message_when_join"));
            return;
        }

        isTo.ifPresent(island -> MessageUtil.sendMessage(e.getPlayer().getUniqueId(), OneBlockMC.getMessages().get("join_island"), Collections.singletonMap("%player%", Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(island.getId()))).getName())));

    }
}
