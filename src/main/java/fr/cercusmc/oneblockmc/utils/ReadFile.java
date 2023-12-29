package fr.cercusmc.oneblockmc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReadFile {

    /**
     * Convertie un fichier yml en une classe POJO
     * @param clazz Classe POJO
     * @param fileName Nom du fichier
     * @return Un objet POJO prêt a utiliser
     */
    public static <T> T yamlToObject(final Class<T> clazz, final String fileName) {

        try {
            Yaml yaml = new Yaml(new Constructor(clazz, new LoaderOptions()));
            return yaml.load(Files.newInputStream(new File(fileName).toPath()));
        } catch(IOException e) {
            return null;
        }
    }

    public static <U> List<U> yamlToList(final Class<U> clazz, final String fileName, final String firstLine) {
        List<U> res = new ArrayList<>();
        try {
            FileConfiguration configuration = new YamlConfiguration();
            configuration.load(fileName);
            Field[] fields = clazz.getDeclaredFields();
            for(String i : configuration.getConfigurationSection(firstLine).getKeys(false)) {
                U obj = clazz.getDeclaredConstructor().newInstance();
                for(Field f : fields) {
                    Method method = clazz.getDeclaredMethod("set"+f.getName().substring(0, 1).toUpperCase()+f.getName().substring(1), f.getType());

                    if(f.getType().equals(String.class)) {
                        method.invoke(obj, configuration.getString(firstLine+"."+i+"."+f.getName()));
                    }
                }
            }

            return null;
        } catch(Exception e) {
            return null;
        }
    }

    public static <T> Map<String, T> yamlToMap(final String fileName, Class<T> clazz) {

        Yaml yaml = new Yaml();
        try {
            Map<String, Object> map = yaml.load(new FileInputStream(fileName));

            Map<String, T> res = new HashMap<>();
            for(Map.Entry<String, Object> m : map.entrySet()) {
                res.put(m.getKey(), clazz.cast(m.getValue()));
            }

            return res;

        } catch (FileNotFoundException e) {
            return new HashMap<>();
        }
    }

    public static <T> T jsonToObject(final Class<T> clazz, final String fileName) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(fileName), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
