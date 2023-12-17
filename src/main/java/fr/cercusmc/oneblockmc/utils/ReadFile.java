package fr.cercusmc.oneblockmc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReadFile {

    /**
     * Convertie un fichier xml en une classe POJO
     * @param clazz Classe POJO
     * @param fileName Nom du fichier
     * @return Un objet POJO prêt a utiliser
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToPojo(final Class<T> clazz, final String fileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(new File(fileName));

        } catch(JAXBException e) {
            return null;
        }
    }

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
