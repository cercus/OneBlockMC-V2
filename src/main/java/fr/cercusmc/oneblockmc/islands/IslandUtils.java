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
import org.bukkit.entity.Player;
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
            BoundingBox box = BoundingBox.of(is.getLocations().getCenter().getLocation().toLocation(), OneBlockMC.getInstance().getConfig().getDouble("max_radius", 250.0), 250.0, OneBlockMC.getInstance().getConfig().getDouble("max_radius", 250.0));
            if(box.contains(loc.getX(), loc.getY(), loc.getZ())) return Optional.of(is);

        }
        return Optional.empty();
    }

    /**
     * Check if player is on his island
     * @param p Player
     * @return true if player is on his island
     */
    public static boolean playerIsInHisIsland(Player p) {
        Optional<Island> is = getIslandByUuid(OneBlockMC.getIslands(), p.getUniqueId());
        return is.filter(island -> playerBelongToIsland(island, p.getUniqueId()) && Objects.requireNonNull(getIslandBox(island)).contains(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ())).isPresent();
    }

    public static BoundingBox getIslandBox(Island is) {
        org.bukkit.World world = is.getLocations().getCenter().getLocation().toLocation().getWorld();
        if(world == null) return null;
        return getIslandBox(is, OneBlockMC.getInstance().getConfig().getDouble("max_radius", 250.0));

    }

    public static BoundingBox getIslandBox(Island is, double radius) {
        org.bukkit.World world = is.getLocations().getCenter().getLocation().toLocation().getWorld();
        if(world == null) return null;
        return BoundingBox.of(is.getLocations().getCenter().getLocation().toLocation(), radius, Math.abs(world.getMinHeight())+world.getMaxHeight(), radius);
    }

    /**
     * Check if player belong to island given
     * @param is Island
     * @param uuid UUID of player
     * @return true if player belongs to island
     */
    public static boolean playerBelongToIsland(Island is, UUID uuid){
        return playerIsOwner(is, uuid) || playerIsMemberOfIsland(is, uuid);
    }

    /**
     * Check if player is in effective island
     * @param is Island
     * @param uuid UUID of player
     * @param loc Location
     * @return true if player is in effective island
     */
    public static boolean playerIsInEffectiveIsland(Island is, UUID uuid, Location loc) {
        return playerBelongToIsland(is, uuid) && Objects.requireNonNull(getIslandBox(is, is.getStats().getRadius())).contains(loc.getX(), loc.getY(), loc.getZ());
    }

    /**
     * Check if player has island
     * @param uuid UUID of player
     * @return true if player has island
     */
    public static boolean playerHasIsland(UUID uuid){
        return OneBlockMC.getIslands().stream().anyMatch(k -> playerIsOwner(k, uuid) || playerIsMemberOfIsland(k, uuid));
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

    /**
     * Check if a player is member of island
     * @param is Island
     * @param uuid UUID of player
     * @return true if player is member of island
     */
    public static boolean playerIsMemberOfIsland(final Island is, final UUID uuid) {
        if(is.getMembers().getPlayers() == null) return false;
        return is.getMembers().getPlayers().contains(uuid.toString());
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
        OneBlockMC.getIslands().add(newIs);

        FileType type = FileType.valueOf(OneBlockMC.getInstance().getConfig().getString("file_format", "YAML").toUpperCase());

        switch(type) {
            case JSON -> WriteFile.objectToJson(newIs, OneBlockMC.getInstance().getPathIsland() + "json/"+uuid+".json");
            case YAML -> WriteFile.objectToYml(newIs, OneBlockMC.getInstance().getPathIsland() + "yaml/"+uuid+".yml");
        }

        OneBlockMC.getInstance().getOverworld().getBlockAt(loc.toLocation()).setType(Material.GRASS_BLOCK);

        MessageUtil.sendMessage(uuid, OneBlockMC.getMessages().get("create_island_finished"));

        Objects.requireNonNull(Bukkit.getPlayer(uuid)).teleport(Position.getCenterOfBlock(loc.toLocation()));

        OneBlockMC.getInstance().getConfig().set("number_of_island", OneBlockMC.getInstance().getConfig().getInt("number_of_island")+1);
        OneBlockMC.getInstance().saveConfig();
        return newIs;
    }

    public static Island deleteIsland(Island is, final UUID uuid) {

        MessageUtil.sendMessage(uuid, OneBlockMC.getMessages().get("delete_island_progress"));
        DeleteIsland.deleteIsland(is, Bukkit.getPlayer(uuid));

        FileType type = FileType.valueOf(OneBlockMC.getInstance().getConfig().getString("file_format", "YAML").toUpperCase());
        WriteFile.deleteFile(is.getId(), type);
        MessageUtil.sendMessage(uuid, OneBlockMC.getMessages().get("delete_island_success"));

        return is;
    }

    public static Island modifyBiome(Island is, final Biome biome) {
        return is;
    }

    public static Island addMember(Island is, final UUID uuid) {

        OneBlockMC.getIslands().remove(is);
        is.getMembers().addPlayer(uuid.toString());
        OneBlockMC.getIslands().add(is);
        return is;
    }

    public static Island removeMember(Island is, final UUID uuid) {
        OneBlockMC.getIslands().remove(is);
        is.getMembers().removePlayer(uuid.toString());
        OneBlockMC.getIslands().add(is);
        return is;
    }

    public static Island addBan(Island is, final UUID uuid) {

        OneBlockMC.getIslands().remove(is);
        is.getBans().addPlayer(uuid.toString());
        OneBlockMC.getIslands().add(is);

        return is;
    }

    public static Island removeBan(Island is, final UUID uuid) {

        OneBlockMC.getIslands().remove(is);
        is.getBans().removePlayer(uuid.toString());
        OneBlockMC.getIslands().add(is);
        return is;
    }

    public static Island updateSizeIsland(Island is, final int radius) {

        OneBlockMC.getIslands().remove(is);
        Stats s = is.getStats();
        s.setRadius(radius);
        is.setStats(s);
        OneBlockMC.getIslands().add(is);
        return is;
    }

    public static Island modifyCustomName(Island is, final String name) {

        OneBlockMC.getIslands().remove(is);
        is.setCustomName(name);
        OneBlockMC.getIslands().add(is);

        return is;
    }

    public static Island modifyWarp(Island is, final Location newLoc) {

        OneBlockMC.getIslands().remove(is);
        Loc loc = new Loc();
        fr.cercusmc.oneblockmc.islands.pojo.Location location = new fr.cercusmc.oneblockmc.islands.pojo.Location();
        location.setName(Objects.requireNonNull(newLoc.getWorld()).getName());
        location.setX(newLoc.getX());
        location.setY(newLoc.getY());
        location.setZ(newLoc.getZ());
        loc.setLocation(location);
        is.getLocations().setWarp(loc);
        OneBlockMC.getIslands().add(is);
        return is;
    }

    public static Island modifyHome(Island is, final Location newLoc) {

        OneBlockMC.getIslands().remove(is);
        Loc loc = new Loc();
        fr.cercusmc.oneblockmc.islands.pojo.Location location = new fr.cercusmc.oneblockmc.islands.pojo.Location();
        location.setName(Objects.requireNonNull(newLoc.getWorld()).getName());
        location.setX(newLoc.getX());
        location.setY(newLoc.getY());
        location.setZ(newLoc.getZ());
        loc.setLocation(location);
        is.getLocations().setHome(loc);
        OneBlockMC.getIslands().add(is);

        return is;
    }

    public static Island incrementPhaseNumber(Island is) {

        OneBlockMC.getIslands().remove(is);
        Stats s = is.getStats();
        s.setPhase((s.getPhase()+1));
        is.setStats(s);
        OneBlockMC.getIslands().add(is);

        return is;
    }

    public static void computeIslandLevel(Island is, UUID uuid) {
        MessageUtil.sendMessage(uuid, OneBlockMC.getMessages().get("computing_level_island"));
        ComputeLevelIsland.calcIsland(is, Bukkit.getPlayer(uuid));
    }

    public static Location getSpawn() {
        String worldName = OneBlockMC.getInstance().getConfig().getString("spawn.world", "world");
        double x = OneBlockMC.getInstance().getConfig().getDouble("spawn.x");
        double y = OneBlockMC.getInstance().getConfig().getDouble("spawn.y");
        double z = OneBlockMC.getInstance().getConfig().getDouble("spawn.z");
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }


}
