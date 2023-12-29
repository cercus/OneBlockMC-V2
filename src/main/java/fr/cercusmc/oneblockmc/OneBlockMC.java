package fr.cercusmc.oneblockmc;

import fr.cercusmc.oneblockmc.islands.BiomeUtils;
import fr.cercusmc.oneblockmc.islands.SaveFileScheduler;
import fr.cercusmc.oneblockmc.islands.pojo.*;
import fr.cercusmc.oneblockmc.utils.Logger;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.ReadFile;
import fr.cercusmc.oneblockmc.utils.WriteFile;
import fr.cercusmc.oneblockmc.utils.enums.FileType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.nio.file.Paths;
import java.util.*;

import static fr.cercusmc.oneblockmc.islands.IslandUtils.computeIslandLevel;

public final class OneBlockMC extends JavaPlugin {

    private static OneBlockMC instance;

    private final static List<Island> islands = new ArrayList<>();

    private static Logger log;

    private static Map<String, String> messages;
    private static Map<String, Double> levels;

    private static List<Biome> biomes;

    File fileJsonFolder;
    File fileYmlFolder;
    String pathIsland;

    @Override
    public void onEnable() {
        instance = this;



        pathIsland = instance.getDataFolder().getPath() + "/islands/";
        fileJsonFolder = new File(pathIsland + "json/");
        fileYmlFolder = new File(pathIsland + "yaml/");

        createAndLoadFiles();

        messages = ReadFile.yamlToMap(instance.getDataFolder().getPath()+"/messages.yml", String.class);
        biomes = BiomeUtils.loadBiomes( instance.getDataFolder().getPath()+"/biomes.yml");


        log = new Logger(messages.get("prefix"));

        saveDefaultConfig();
        reloadConfig();

        loadIslands();

        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, new SaveFileScheduler(FileType.valueOf(getConfig().getString("file_format", "YAML").toUpperCase())), 0, 20*60*5);

    }


    private void createAndLoadFiles() {
        if(!fileYmlFolder.exists())
            fileYmlFolder.mkdirs();

        if(!fileJsonFolder.exists())
            fileJsonFolder.mkdirs();

        File messagesFile = new File(instance.getDataFolder().getPath()+"/messages.yml");
        if(!messagesFile.exists()) instance.saveResource("messages.yml", false);

        File biomeFile = new File(instance.getDataFolder().getPath()+"/biomes.yml");
        if(biomeFile.exists()) instance.saveResource("biome.yml", false);

        File levelsFile = new File(instance.getDataFolder().getPath()+"/levels.yml");
        if(!levelsFile.exists()) instance.saveResource("levels.yml", false);

        levels = ReadFile.yamlToMap(instance.getDataFolder().getPath()+"/levels.yml", Double.class);

        File phasesFile = new File(instance.getDataFolder().getPath()+"/phases.yml");
        if(!phasesFile.exists()) instance.saveResource("phases.yml", false);



    }



    /**
     * Charger toutes les configurations d'îles
     */
    private void loadIslands() {

        FileType type = FileType.valueOf(getConfig().getString("file_format", "YAML").toUpperCase());

        File[] filesJson = fileJsonFolder.listFiles();
        File[] filesYaml = fileYmlFolder.listFiles();

        if(filesJson != null && Arrays.stream(filesJson).findAny().isPresent()) {
            for(File f : filesJson) {
                Island is = ReadFile.jsonToObject(Island.class, f.getPath());
                if(is != null) islands.add(is);
            }

            if(!Objects.equals(type, FileType.JSON)) {
                convertFile(type, filesJson);
            }

        } else if(filesYaml != null && Arrays.stream(filesYaml).findAny().isPresent()) {
            for(File f : filesYaml) {
                Island is = ReadFile.yamlToObject(Island.class, f.getPath());
                if(is != null) islands.add(is);
            }

            if(!Objects.equals(type, FileType.YAML)) {
                convertFile(type, filesYaml);
            }
        }

    }

    private void convertFile(FileType type, File[] files) {
        MessageUtil.sendMessage(null, OneBlockMC.getMessages().get("delete_old_file"));
        for(File f : files) {
            f.delete();
        }
        MessageUtil.sendMessage(null, OneBlockMC.getMessages().get("create_new_file"));
        for(Island entry : islands) {

            String name = entry.getId();
            switch(type) {

                case JSON -> WriteFile.objectToJson(entry, pathIsland + "json/"+name.replace(name.substring(name.indexOf('.')), ".json"));

                case YAML -> WriteFile.objectToYml(entry, pathIsland + "yaml/"+name.replace(name.substring(name.indexOf('.')), ".yml"));
            }
        }

    }

    @Override
    public void onDisable() {
        SaveFileScheduler.saveFiles(pathIsland, FileType.valueOf(getConfig().getString("file_format", "YAML").toUpperCase()));
    }

    public static OneBlockMC getInstance() {
        return instance;
    }


    public static Logger getLog() {
        return log;
    }

    public static Map<String, String> getMessages() {
        return messages;
    }

    public static Map<String, Double> getLevels() {
        return levels;
    }

    public static List<Island> getIslands() {
        return islands;
    }

    public static List<Biome> getBiomes() {
        return biomes;
    }

    public String getPathIsland() {
        return pathIsland;
    }
}
