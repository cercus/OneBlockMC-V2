package fr.cercusmc.oneblockmc.islands.pojo;

import org.bukkit.Material;

import java.util.List;

public class Biome {

    private Integer id;
    private String name;
    private List<String> description;
    private Material icon;
    private boolean permission;

    private org.bukkit.block.Biome biome;


    public Biome() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public org.bukkit.block.Biome getBiome() {
        return biome;
    }

    public void setBiome(org.bukkit.block.Biome biome) {
        this.biome = biome;
    }
}
