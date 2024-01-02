package fr.cercusmc.oneblockmc.islands.pojo;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Phase {

    private int id;
    private String name;
    private List<String> description;
    private List<ItemStack> items;
    private List<Map<EntityType, Integer>> entities;
    private List<Map<Material, Integer>> blocs;
    private int probaGenerateBlock;
    private int probaGenerateMob;
    private int probaGenerateChest;

    private Material icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void addItem(ItemStack item) {
        if(this.items == null) this.items = new ArrayList<>();
        this.items.add(item);
    }

    public List<Map<EntityType, Integer>> getEntities() {
        return entities;
    }

    public void setEntities(List<Map<EntityType, Integer>> entities) {
        this.entities = entities;
    }

    public void addEntity(Map<EntityType, Integer> entity) {
        if(this.entities == null) this.entities = new ArrayList<>();
        this.entities.add(entity);
    }

    public List<Map<Material, Integer>> getBlocs() {
        return blocs;
    }

    public void setBlocs(List<Map<Material, Integer>> blocs) {
        this.blocs = blocs;
    }

    public void addBloc(Map<Material, Integer> bloc){
        if(this.blocs == null) this.blocs = new ArrayList<>();
        this.blocs.add(bloc);
    }

    public int getProbaGenerateBlock() {
        return probaGenerateBlock;
    }

    public void setProbaGenerateBlock(int probaGenerateBlock) {
        this.probaGenerateBlock = probaGenerateBlock;
    }

    public int getProbaGenerateMob() {
        return probaGenerateMob;
    }

    public void setProbaGenerateMob(int probaGenerateMob) {
        this.probaGenerateMob = probaGenerateMob;
    }

    public int getProbaGenerateChest() {
        return probaGenerateChest;
    }

    public void setProbaGenerateChest(int probaGenerateChest) {
        this.probaGenerateChest = probaGenerateChest;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + description +
                ", items=" + items +
                ", entities=" + entities +
                ", blocs=" + blocs +
                ", probaGenerateBlock=" + probaGenerateBlock +
                ", probaGenerateMob=" + probaGenerateMob +
                ", probaGenerateChest=" + probaGenerateChest +
                ", icon=" + icon +
                '}';
    }
}
