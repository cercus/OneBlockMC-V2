package fr.cercusmc.oneblockmc.islands;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DeleteIsland {

    public static CompletableFuture<Void> deleteIsland(Island is, Player p) {
        return CompletableFuture.runAsync(() -> {
            List<CompletableFuture<Void>> completableFutures = Arrays.asList(deleteIsland(is, OneBlockMC.getInstance().getOverworld(), p)
            );
            completableFutures.forEach(CompletableFuture::join);

        });
    }

    private static CompletableFuture<Void> deleteIsland(Island is, World world, Player p) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        if(world == null) {
            completableFuture.complete(null);
        } else {
            Location center = is.getLocations().getCenter().getLocation().toLocation();

            Collection<Entity> entities = world.getNearbyEntities(Objects.requireNonNull(IslandUtils.getIslandBox(is, is.getStats().getRadius())));
            entities.forEach(Entity::remove);

            Bukkit.getScheduler().runTask(OneBlockMC.getInstance(), () -> {

                for (int x = center.getBlockX() - is.getStats().getRadius(); x <= center.getBlockX() + is.getStats().getRadius(); x++) {
                    for (int z = center.getBlockX() - is.getStats().getRadius(); z <= center.getBlockX() + is.getStats().getRadius(); z++) {
                        for (int y = world.getMinHeight(); y <= world.getMaxHeight(); y++) {
                            world.getBlockAt(x, y, z).setType(Material.AIR);
                        }
                    }
                }

            });
        }
        return completableFuture;
    }
}
