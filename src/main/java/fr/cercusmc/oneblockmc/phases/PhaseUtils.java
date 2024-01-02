package fr.cercusmc.oneblockmc.phases;

import fr.cercusmc.oneblockmc.islands.pojo.Biome;
import fr.cercusmc.oneblockmc.islands.pojo.Phase;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.ToolsUtils;
import fr.cercusmc.oneblockmc.utils.ValidateUtils;
import fr.cercusmc.oneblockmc.utils.enums.Enchantments;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.*;

public class PhaseUtils {
    public static List<Phase> loadPhases(String fileName) {
        try {
            FileConfiguration configuration = new YamlConfiguration();
            configuration.load(fileName);
            List<Phase> phases = new ArrayList<>();
            for(String i : Objects.requireNonNull(configuration.getConfigurationSection("")).getKeys(false)) {

                Phase phase = new Phase();

                String iconName = configuration.getString(i+".icon");
                try {
                    phase.setIcon(Material.valueOf(iconName));
                } catch(IllegalArgumentException e) {
                    continue;
                }

                phase.setId(Integer.parseInt(i));
                phase.setName(MessageUtil.format(configuration.getString(i+".name")));
                phase.setDescription(MessageUtil.format(configuration.getStringList(i+".description")));
                phase.setProbaGenerateBlock(configuration.getInt(i+".probability_generate_block"));
                phase.setProbaGenerateChest(configuration.getInt(i+".probability_generate_chest"));
                phase.setProbaGenerateMob(configuration.getInt(i+".probability_generate_entities"));

                List<String> entities = configuration.getStringList(i+".mobs");
                System.out.println("entities="+entities);
                for(String e : entities) {
                    String name = e.split(" ")[0];
                    String proba = e.split(" ")[1];
                    if (ValidateUtils.checkEnum(EntityType.class, name))
                        phase.addEntity(Collections.singletonMap(EntityType.valueOf(name.toUpperCase()), Integer.parseInt(proba)));
                }

                List<String> blocks = configuration.getStringList(i+".blocs");

                for(String b : blocks) {
                    String name = b.split(" ")[0];
                    String proba = b.split(" ")[1];
                    if(ValidateUtils.checkEnum(Material.class, name)) phase.addBloc(Collections.singletonMap(Material.valueOf(name.toUpperCase()), Integer.parseInt(proba)));
                }

                List<String> items = configuration.getStringList(i+".items");
                for(String it : items) {
                    Map<String, String> decode = ValidateUtils.decodeItem(it);
                    ItemStack item = traitmentItems(i, decode);
                    if(item != null) phase.addItem(item);
                }

                phases.add(phase);
            }

            return phases;
        } catch(IOException | InvalidConfigurationException | IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    private static ItemStack traitmentItems(String i, Map<String, String> decode) {
        if(!decode.isEmpty() && ValidateUtils.checkEnum(Material.class, decode.get("material"))) {

            ItemStack itemStack = new ItemStack(Material.valueOf(decode.get("material")), Integer.parseInt(decode.get("count")));
            ItemMeta meta = itemStack.getItemMeta();
            if(meta == null ) return itemStack;
            if(decode.get("name") != null)
                meta.setDisplayName(MessageUtil.format(decode.get("name")));
            if(decode.get("lore") != null)
                meta.setLore(MessageUtil.format(ToolsUtils.convertRawStringListToList(decode.get("lore"), ",")));

            if(decode.get("enchantments") != null) {
                Map<Enchantment, Integer> ench = traitmentEnchantments(decode);
                itemStack.addEnchantments(ench);
            }
            itemStack.setItemMeta(meta);
            return itemStack;
        }
        return null;


    }

    private static Map<Enchantment, Integer> traitmentEnchantments(Map<String, String> decode) {
        Map<Enchantment, Integer> ench = new HashMap<>();
        List<String> list = ToolsUtils.convertRawStringListToList(decode.get("enchantments"), ",");
        for(String l : list){
            String enchName = l.split(":")[0];
            String power = l.split(":")[1];
            if(ValidateUtils.checkEnum(Enchantments.class, enchName) && power.matches("^[0-9]+$")) {
                ench.put(Enchantments.valueOf(enchName.toUpperCase()).getEnchantment(), Integer.parseInt(power));
            }
        }
        return ench;

    }


}
