package fr.cercusmc.oneblockmc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class WriteFile {

    public static boolean objectToJson(Object object, String fileName){
        if(object == null) return false;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {

            String json = ow.writeValueAsString(object);
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(json);
            bw.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean objectToYml(Object object, String fileName){
        if(object == null) return false;

        try(PrintWriter writer = new PrintWriter(fileName)) {

            DumperOptions options = new DumperOptions();
            options.setIndent(2);
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Representer customRepresenter = new Representer(options);
            customRepresenter.addClassTag(object.getClass(), Tag.MAP);

            Yaml yaml = new Yaml(customRepresenter);
            yaml.dump(object, writer);

        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
