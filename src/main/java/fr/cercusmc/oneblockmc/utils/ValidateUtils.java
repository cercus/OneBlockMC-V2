package fr.cercusmc.oneblockmc.utils;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateUtils {

    /**
     * Check if a value is valid in enum given
     * @param aEnum Enum class
     * @param value Value to test
     * @return true if value belong to enum given
     */
    public static <E extends Enum<E>> boolean checkEnum(Class<E> aEnum, String value) {
        if (value == null || !aEnum.isEnum())
            return false;
        try {
            Enum.valueOf(aEnum, value.toUpperCase());
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

    public static Map<String, String> decodeItem(String item) {
        if(!validateStringItem(item)) return new HashMap<>();

        Map<String, String> res = new HashMap<>();

        res.put("material", getMaterialFromItem(item));
        res.put("count", getCountFromItem(item)+"");

        if(hasMeta(item)) {
            String meta = item.substring(item.indexOf("{")+1, item.indexOf("}"));
            if(item.contains("name=")) {
                String name = meta.substring(meta.indexOf("name=[\"")+7, meta.indexOf("\"]"));
                meta = meta.replace("name=[\""+name+"\"],", "");

                res.put("name", name);
            }

            if(item.contains("lore=")) {
                String loreName = meta.substring(meta.indexOf("lore=")+5, meta.indexOf("\"]")+2);
                meta = meta.replace("lore="+loreName+",", "");
                res.put("lore", loreName);
            }

            if(item.contains("enchantments=")) {
                res.put("enchantments", meta.substring(meta.indexOf("enchantments=")+13));
            }
        }
        return res;

    }

    public static boolean validateStringItem(String item) {

        String material = null;
        int count = Integer.MIN_VALUE;

        if((item.contains("{") && !item.matches(".*}")) || (!item.contains("{") && item.matches(".*}"))) return false;


        if(item.matches("^[a-zA-Z_]+(\\[)?.+") || item.matches("^[a-zA-Z_]+:[0-9]+\\[.+")) {
            material = getMaterialFromItem(item);
            count = getCountFromItem(item);
        }

        if(!hasMeta(item) && count != Integer.MIN_VALUE && checkEnum(Material.class, material)) return true;

        if(item.contains("name=") && !item.matches(".*name=\\[\".+\"].*")) return false;

        if(item.contains("lore=") && !item.matches(".*lore=\\[(\".+\"(,\\s+)*)].*")) return false;

        return !item.contains("enchantments=") || item.matches(".*enchantments=\\[\".+\"].*");

    }



    private static boolean hasMeta(String item) {
        return item.contains("{") && item.contains("}");
    }

    private static String getMaterialFromItem(String item) {
        if(item.matches("^[a-zA-Z_]+:[0-9]+.+")) {
            return item.substring(0, item.indexOf(":"));
        } else if(item.matches("^[a-zA-Z_]+")) {
            return item;
        }
        return "";
    }

    private static int getCountFromItem(String item) {
        if(item.matches("^[a-zA-Z_]+:[0-9]+\\{.*")) {
            return Integer.parseInt(item.substring(item.indexOf(":")+1, item.indexOf("{")));
        } else if(item.matches("^[a-zA-Z_]+:[0-9]+$")) {
            return Integer.parseInt(item.substring(item.indexOf(":")+1));
        } else
        return 1;
    }

}
