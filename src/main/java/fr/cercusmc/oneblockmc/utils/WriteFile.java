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

    public static boolean objectToXml(final Object object, String fileName) {
        if(object == null) return false;

        try {
            Class<?> clazz = object.getClass();

            Field[] fields = clazz.getDeclaredFields();
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            sb.append("<").append(clazz.getSimpleName().substring(0, 1).toLowerCase()).append(clazz.getSimpleName().substring(1)).append(">\n");
            for (Field f : fields) {
                writeSubObject(sb, f, clazz, object);
            }
            sb.append("</").append(clazz.getSimpleName().substring(0, 1).toLowerCase()).append(clazz.getSimpleName().substring(1)).append(">");
            bw.write(sb.toString());
            bw.close();
        } catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;

    }

    private static void writeSubObject(StringBuilder sb, Field f, Class<?> clazz, Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getMethodStr = "get" + f.getName().substring(0, 1).toUpperCase()+f.getName().substring(1);

        if(fieldIsPrimitive(f)) {
            sb.append("<").append(f.getName()).append(">");
            sb.append(clazz.getDeclaredMethod(getMethodStr).invoke(o));
            sb.append("</").append(f.getName()).append(">\n");
        } else if(f.getType().equals(List.class)) {

            List<String> list = (List<String>) clazz.getDeclaredMethod(getMethodStr).invoke(o);

            for(String i : list) {
                sb.append("<").append(f.getName()).append(">");
                sb.append(i);
                sb.append("</").append(f.getName()).append(">\n");
            }
        } else {
            sb.append("<").append(f.getName()).append(">\n");
            for(Field f2 : f.getType().getDeclaredFields()) {
                writeSubObject(sb, f2, f.getType(), clazz.getDeclaredMethod(getMethodStr).invoke(o));
            }
            sb.append("</").append(f.getName()).append(">\n");

        }

    }

    public static boolean fieldIsPrimitive(Field f) {

        return f.getType().equals(String.class) || f.getType().equals(Double.class) || f.getType().equals(Integer.class);
    }

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
            return false;
        }
        return true;
    }
}
