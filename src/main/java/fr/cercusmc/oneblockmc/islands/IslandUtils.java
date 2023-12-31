package fr.cercusmc.oneblockmc.islands;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.pojo.*;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.Position;
import fr.cercusmc.oneblockmc.utils.WriteFile;
import fr.cercusmc.oneblockmc.utils.enums.FileType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.util.BoundingBox;

import java.util.*;

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
        return OneBlockMC.getIslands().stream().anyMatch(k -> playerIsOwner(k, uuid) || k.getMembers().getPlayers().contains(uuid.toString()));
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

        MessageUtil.sendMessage(uuid, OneBlockMC.getMessages().get("create_island_in_progress"));

        Island newIs = new Island();
        newIs.setId(uuid.toString());
        newIs.setBiome(OneBlockMC.getInstance().getConfig().getString("biome_overworld_default", "PLAINS"));
        newIs.setCustomName(MessageUtil.format(OneBlockMC.getMessages().get("default_custom_name_island"), Collections.singletonMap("%player%", Bukkit.getPlayer(uuid))));

        Members members = new Members();
        members.addPlayer(uuid.toString());
        newIs.setMembers(members);

        Bans bans = new Bans();
        bans.setPlayers(new ArrayList<>());
        newIs.setBans(bans);

        Stats stats = new Stats();
        stats.setRadius(OneBlockMC.getInstance().getConfig().getInt("default_radius", 25));
        stats.setPhase(1);
        stats.setNbBlock(0);
        stats.setLevel(0.0);
        newIs.setStats(stats);

        Locations locs = new Locations();
        Position coordIsland = Position.findNextLocation(OneBlockMC.getInstance().getConfig().getInt("number_of_island", 0));
        System.out.println("coordIsland="+coordIsland);
        fr.cercusmc.oneblockmc.islands.pojo.Location loc = new fr.cercusmc.oneblockmc.islands.pojo.Location();
        loc.setName(OneBlockMC.getInstance().getConfig().getString("overworld_name", "Oneblock_overworld"));
        loc.setX(coordIsland.x());
        loc.setY(coordIsland.y());
        loc.setZ(coordIsland.z());

        Loc loc1 = new Loc();
        loc1.setLocation(loc);

        locs.setWarp(loc1);
        locs.setHome(loc1);
        locs.setSpawn(loc1);
        locs.setCenter(loc1);
        newIs.setLocations(locs);
        System.out.println("newIS="+newIs);
        OneBlockMC.getIslands().add(newIs);

        FileType type = FileType.valueOf(OneBlockMC.getInstance().getConfig().getString("file_format", "YAML").toUpperCase());

        switch(type) {
            case JSON -> WriteFile.objectToJson(newIs, OneBlockMC.getInstance().getPathIsland() + "json/"+uuid+".json");
            case YAML -> WriteFile.objectToYml(newIs, OneBlockMC.getInstance().getPathIsland() + "yaml/"+uuid+".yml");
        }

        OneBlockMC.getInstance().getOverworld().getBlockAt(loc.toLocation()).setType(Material.GRASS_BLOCK);

        MessageUtil.sendMessage(uuid, OneBlockMC.getMessages().get("create_island_finished"));

        Objects.requireNonNull(Bukkit.getPlayer(uuid)).teleport(Position.getCenterOfBlock(loc.toLocation()));

        return newIs;
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

    public static void computeIslandLevel(Island is, UUID uuid) {
        MessageUtil.sendMessage(uuid, OneBlockMC.getMessages().get("computing_level_island"));
        ComputeLevelThread run = new ComputeLevelThread(is, uuid);
        Thread t = new Thread(run);
        t.start();


    }


}
