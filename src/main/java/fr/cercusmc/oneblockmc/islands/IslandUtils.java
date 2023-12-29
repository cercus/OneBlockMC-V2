package fr.cercusmc.oneblockmc.islands;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.util.BoundingBox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IslandUtils {

    /**
     * Get island by owner
     * @param islands List of islands
     * @param uuid UUID of player
     * @return
     */
    public static Optional<Island> getIslandByUuid(List<Island> islands, UUID uuid) {
        return islands.stream().filter(k -> k.getId().equals(uuid.toString())).findFirst();
    }

    /**
     * Get island by location
     * @param islands List of island
     * @param loc Location
     * @return The island at given position
     */
    public static Optional<Island> getIslandByLocation(List<Island> islands, Location loc) {

        for(Island is : islands){
            BoundingBox box = BoundingBox.of(is.getLocations().getCenter().getLocation().toLocation(), Double.valueOf(is.getStats().getRadius()), 250.0, Double.valueOf(is.getStats().getRadius()));
            if(box.contains(loc.getX(), loc.getY(), loc.getZ())) return Optional.of(is);

        }
        return Optional.empty();
    }

    /**
     * Check if player is on his island
     * @param is Island
     * @param locPlayer Location of player
     * @return true if player is on his island
     */
    public static boolean playerIsInHisIsland(Island is, Location locPlayer) {
        return getIslandBox(is).contains(locPlayer.getX(), locPlayer.getY(), locPlayer.getZ());
    }

    public static BoundingBox getIslandBox(Island is) {
        return BoundingBox.of(is.getLocations().getCenter().getLocation().toLocation(), Double.valueOf(is.getStats().getRadius()), 250.0, Double.valueOf(is.getStats().getRadius()));
    }

    /**
     * Check if player has island
     * @param uuid UUID of player
     * @return true if player has island
     */
    public static boolean playerHasIsland(UUID uuid){
        return OneBlockMC.getIslands().entrySet().stream().anyMatch(k -> playerIsOwner(k.getValue(), uuid) || k.getValue().getMembers().getPlayers().contains(uuid.toString()));
    }

    /**
     * Check if player is owner of island
     * @param is Island
     * @param uuid UUID of player
     * @return true if player is owner
     */
    public static boolean playerIsOwner(final Island is, final UUID uuid) {
        return is.getId().equals(uuid.toString());
    }

    public static Island createIsland(final UUID uuid) {
        return null;
    }

    public static Island deleteIsland(Island is) {

        return is;
    }

    public static Island modifyBiome(Island is, final Biome biome) {
        return is;
    }

    public static Island addMember(Island is, final UUID uuid) {
        return is;
    }

    public static Island removeMember(Island is, final UUID uuid) {
        return is;
    }

    public static Island addBan(Island is, final UUID uuid) {
        return is;
    }

    public static Island removeBan(Island is, final UUID uuid) {
        return is;
    }

    public static Island updateSizeIsland(Island is, final int radius) {
        return is;
    }

    public static Island modifyCustomName(Island is, final String name) {
        return is;
    }

    public static Island modifyWarp(Island is, final Location newLoc) {
        return is;
    }

    public static Island modifyHome(Island is, final Location newLoc) {

        return is;
    }

    public static Island incrementPhaseNumber(Island is) {
        return is;
    }

    public static void computeIslandLevel(Island is) {
        ComputeLevelThread run = new ComputeLevelThread(is);
        Thread t = new Thread(run);
        t.start();


    }


}
