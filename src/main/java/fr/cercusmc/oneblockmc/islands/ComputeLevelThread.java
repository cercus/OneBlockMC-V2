package fr.cercusmc.oneblockmc.islands;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class ComputeLevelThread implements Runnable {

    private Island is;

    private Double level;

    private UUID uuid;

    public ComputeLevelThread(Island is, UUID uuid) {
        this.is = is;
        this.level = 0.0;
        this.uuid = uuid;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        World world = Bukkit.getWorld(is.getLocations().getCenter().getLocation().getName());
        System.out.println("world="+world);
        if(world == null) return;
        Location center = is.getLocations().getCenter().getLocation().toLocation();
        int radius = is.getStats().getRadius();

        for(int i = center.getBlockX()-radius; i <= center.getBlockX()+radius; i++) {
            for(int j = center.getBlockZ()-radius; j <= center.getBlockZ()+radius; j++) {
                for(int k = Objects.requireNonNull(center.getWorld()).getMinHeight(); k <= center.getWorld().getMaxHeight(); k++) {
                    Block b = world.getBlockAt(i, k, j);
                    if(OneBlockMC.getLevels().containsKey(b.getType().name())) {
                        this.level += OneBlockMC.getLevels().get(b.getType().name());
                    }
                }
            }
        }
        MessageUtil.sendMessage(this.uuid, OneBlockMC.getMessages().get("display_level"), Collections.singletonMap("%level", this.level));

    }

    public Island getIs() {
        return is;
    }

    public void setIs(Island is) {
        this.is = is;
    }

    public Double getLevel() {
        return level;
    }
}
