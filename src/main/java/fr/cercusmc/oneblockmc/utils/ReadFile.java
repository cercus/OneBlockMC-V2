package fr.cercusmc.oneblockmc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
