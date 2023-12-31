package fr.cercusmc.oneblockmc.islands;


import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComputeLevelIsland {

    public static CompletableFuture<Void> calcIsland(Island is, Player p) {
        return CompletableFuture.runAsync(() -> {
            List<CompletableFuture<Void>> completableFutures = Arrays.asList(calcIsland(is, OneBlockMC.getInstance().getOverworld(), p)
            );
            completableFutures.forEach(CompletableFuture::join);

        });
    }

    private static CompletableFuture<Void> calcIsland(Island is, World world, Player p) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        if(world == null)
            completableFuture.complete(null);
        else
            Bukkit.getScheduler().runTask(OneBlockMC.getInstance(), () -> {
                Location center = is.getLocations().getCenter().getLocation().toLocation();
                double level = 0;
                for(int x = center.getBlockX()-is.getStats().getRadius(); x <= center.getBlockX()+is.getStats().getRadius(); x++) {
                    for(int z = center.getBlockX()-is.getStats().getRadius(); z <= center.getBlockX()+is.getStats().getRadius(); z++) {
                        for(int y = world.getMinHeight(); y <= world.getMaxHeight(); y++) {
                            Material b = world.getBlockAt(x, y, z).getType();
                            level += OneBlockMC.getLevels().getOrDefault(b.name(), 0.0);
                        }
                    }
                }
                MessageUtil.sendMessage(p.getUniqueId(), OneBlockMC.getMessages().get("display_level"), Collections.singletonMap("%level%", level));

            });
        return completableFuture;
    }
}
