package fr.cercusmc.oneblockmc.generators;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BiomeGenerator extends BiomeProvider {

    private final Biome biome;

    public BiomeGenerator(Biome biome) {
        this.biome = biome;
    }

    @Override
    public Biome getBiome(WorldInfo worldInfo, int i, int i1, int i2) {
        return this.biome;
    }

    @Override
    public List<Biome> getBiomes(WorldInfo worldInfo) {
        return Collections.singletonList(this.biome);
    }
}
