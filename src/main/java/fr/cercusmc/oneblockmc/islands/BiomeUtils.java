package fr.cercusmc.oneblockmc.islands;

import fr.cercusmc.oneblockmc.islands.pojo.Biome;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BiomeUtils {

    public static List<Biome> loadBiomes(String fileName){
        try {
            FileConfiguration configuration = new YamlConfiguration();
            configuration.load(fileName);
            List<Biome> biomes = new ArrayList<>();
            for(String i : configuration.getConfigurationSection("biomes").getKeys(false)) {
                Biome b = new Biome();
                b.setId(configuration.getInt("biomes."+i+".id"));
                b.setDescription(MessageUtil.format(configuration.getStringList("biomes."+i+".description")));
                b.setPermission(configuration.getBoolean("biomes."+i+".permission"));
                b.setName(MessageUtil.format(configuration.getString("biomes."+i+".name")));
                String matString = configuration.getString("biomes."+i+"icon", "").toUpperCase();
                String biomeString = configuration.getString(i, "").toUpperCase();

                b.setIcon(Material.valueOf(matString));
                b.setBiome(org.bukkit.block.Biome.valueOf(biomeString));
                biomes.add(b);
            }

            return biomes;
        } catch(IOException | InvalidConfigurationException | IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }


    public static Optional<Biome> getBiomeById(List<Biome>biomes, int id) {

        return biomes.stream().filter(k -> k.getId().equals(id)).findFirst();
    }
}
