package fr.cercusmc.oneblockmc;

import fr.cercusmc.oneblockmc.islands.pojo.*;
import fr.cercusmc.oneblockmc.utils.ReadFile;
import fr.cercusmc.oneblockmc.utils.WriteFile;
import fr.cercusmc.oneblockmc.utils.enums.FileType;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.util.*;

public final class OneBlockMC extends JavaPlugin {

    private static OneBlockMC instance;

    private final static HashMap<String, Island> islands = new HashMap<>();

    File fileJsonFolder;
    File fileYmlFolder;

    String pathIsland;

    @Override
    public void onEnable() {
        instance = this;

        pathIsland = instance.getDataFolder().getPath() + "/islands/";
        fileJsonFolder = new File(pathIsland + "json/");
        fileYmlFolder = new File(pathIsland + "yaml/");

        saveDefaultConfig();
        reloadConfig();


        if(!fileYmlFolder.exists())
            fileYmlFolder.mkdirs();

        if(!fileJsonFolder.exists())
            fileJsonFolder.mkdirs();

        loadIslands();

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
                if(is != null) islands.put(f.getName(), is);
            }

            if(!Objects.equals(type, FileType.JSON)) {
                convertFile(type, filesJson);
            }

        } else if(filesYaml != null && Arrays.stream(filesYaml).findAny().isPresent()) {
            for(File f : filesYaml) {
                Island is = ReadFile.yamlToObject(Island.class, f.getPath());
                if(is != null) islands.put(f.getName(), is);
            }

            if(!Objects.equals(type, FileType.YAML)) {
                convertFile(type, filesYaml);
            }
        }

    }

    private void convertFile(FileType type, File[] files) {
        System.out.println("Suppression des anciens fichiers...");
        for(File f : files) {
            f.delete();
        }
        System.out.println("Création des nouveaux fichiers...");
        for(Map.Entry<String, Island> entry : islands.entrySet()) {
            System.out.println(entry.getKey() + " / " + entry.getValue() + " / type = " + type);
            String name = entry.getKey();
            switch(type) {

                case JSON -> WriteFile.objectToJson(entry.getValue(), pathIsland + "json/"+name.replace(name.substring(name.indexOf('.')), ".json"));

                case YAML -> WriteFile.objectToYml(entry.getValue(), pathIsland + "yaml/"+name.replace(name.substring(name.indexOf('.')), ".yml"));
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static OneBlockMC getInstance() {
        return instance;
    }
}
