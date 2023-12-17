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

    File fileXmlFolder;
    File fileJsonFolder;
    File fileYmlFolder;

    String pathIsland;

    @Override
    public void onEnable() {
        instance = this;

        pathIsland = instance.getDataFolder().getPath() + "/islands/";
        fileXmlFolder = new File(pathIsland + "xml/");
        fileJsonFolder = new File(pathIsland + "json/");
        fileYmlFolder = new File(pathIsland + "yaml/");

        saveDefaultConfig();
        reloadConfig();

        if(!fileXmlFolder.exists())
            fileXmlFolder.mkdirs();

        if(!fileYmlFolder.exists())
            fileYmlFolder.mkdirs();

        if(!fileJsonFolder.exists())
            fileJsonFolder.mkdirs();

        Island is = new Island();
        is.setId("7824-87283-djsk-2sd");
        is.setCustomName("Ile de Player 1");
        Bans b = new Bans();
        b.setPlayer(Arrays.asList("Player 1", "Player 2"));
        is.setBans(b);
        Members m = new Members();
        m.setPlayer(Arrays.asList("Player 3", "Player 4"));
        is.setMembers(m);
        is.setBiome("PLAINS");

        Locations locations = new Locations();
        Loc loc = new Loc();
        Location location = new Location();
        location.setX(0.0);
        location.setY(73.0);
        location.setZ(0.0);
        location.setName("Oneblock_Overworld");
        loc.setLocation(location);
        locations.setCenter(loc);
        locations.setHome(loc);
        locations.setSpawn(loc);
        locations.setWarp(loc);
        is.setLocations(locations);

        Stats s = new Stats();
        s.setNbBlock(10);
        s.setLevel(2.3);
        s.setPhase(1);
        s.setRadius(25);
        is.setStats(s);
        islands.put("7824-87283-djsk-2sd.json", is);
        //System.out.println("File location : " + Files.exists(instance.getDataFolder().toPath()));
        WriteFile.objectToJson(is, pathIsland+"json/"+"7824-87283-djsk-2sd.json");
        loadIslands();

        System.out.println(islands);

    }

    /**
     * Charger toutes les configurations d'îles
     */
    private void loadIslands() {

        FileType type = FileType.valueOf(getConfig().getString("file_format", "YAML").toUpperCase());

        File[] filesXml = fileXmlFolder.listFiles();
        File[] filesJson = fileJsonFolder.listFiles();
        File[] filesYaml = fileYmlFolder.listFiles();
        FileType beforeType = null;

        if(filesXml != null && Arrays.stream(filesXml).findAny().isPresent()) {
            System.out.println("xml ok");
            for(File f : filesXml) {
                Island is = ReadFile.xmlToObject(Island.class, f.getPath());
                if(is != null) islands.put(f.getName(), is);
            }
            beforeType = FileType.XML;
            if(!beforeType.equals(type)) {
                convertFile(type, filesXml);
            }
        } else if(filesJson != null && Arrays.stream(filesJson).findAny().isPresent()) {
            System.out.println("json ok " + Arrays.asList(filesJson));
            for(File f : filesJson) {
                Island is = ReadFile.jsonToObject(Island.class, f.getPath());
                if(is != null) islands.put(f.getName(), is);
            }
            beforeType = FileType.JSON;
            if(!beforeType.equals(type)) {
                convertFile(type, filesJson);
            }
        } else if(filesYaml != null && Arrays.stream(filesYaml).findAny().isPresent()) {
            System.out.println("yaml ok");
            for(File f : filesYaml) {
                Island is = ReadFile.yamlToObject(Island.class, f.getPath());
                if(is != null) islands.put(f.getName(), is);
            }
            beforeType = FileType.YAML;
            if(!beforeType.equals(type)) {
                convertFile(type, filesYaml);
            }
        }
    }

    private void convertFile(FileType type, File[] files) {
        System.out.println("Suppression dse fichiers...");
        for(File f : files) {
            f.delete();
        }
        System.out.println("Création des nouveaux fichiers...");
        for(Map.Entry<String, Island> entry : islands.entrySet()) {
            System.out.println(entry.getKey() + " / " + entry.getValue() + " / type = " + type);
            String name = entry.getKey();
            switch(type) {
                case XML -> WriteFile.objectToXml(entry.getValue(), pathIsland + "xml/"+ name.replace(name.substring(0, name.indexOf('.')), ".xml"));

                case JSON -> WriteFile.objectToJson(entry.getValue(), pathIsland + "json/"+name.replace(name.substring(0, name.indexOf('.')), ".json"));

                case YAML -> WriteFile.objectToYml(entry.getValue(), pathIsland + "yaml/"+name.replace(name.substring(0, name.indexOf('.')), ".yml"));
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
