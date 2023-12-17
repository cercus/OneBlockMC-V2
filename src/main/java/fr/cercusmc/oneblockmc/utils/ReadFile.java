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
     * Convertie un fichier xml en une classe POJO
     * @param clazz Classe POJO
     * @param fileName Nom du fichier
     * @return Un objet POJO prêt a utiliser
     */

    public static <T> T xmlToObject(final Class<T> clazz, final String fileName) {
        StringBuilder resultStringBuilder = new StringBuilder();

        try (InputStream inputStream = new FileInputStream(fileName);
             BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
           
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }

           return convertToObject(clazz, resultStringBuilder);

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return null;
        }

    }

    public static <T> T convertToObject(Class<T> clazz, StringBuilder resultStringBuilder) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String[] split = resultStringBuilder.toString().split("\n");

        StringBuilder sb = new StringBuilder();
        for(int i = 2; i < split.length-1; i++) {
            sb.append(split[i]);
        }

        System.out.println(sb);

        T o = clazz.getDeclaredConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for(Field f : fields) {

            readSubObject(clazz, f, o, sb.toString());
        }

        return o;

    }

    private static void readSubObject(Class<?> clazz, Field f, Object o, String sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        String setMethodStr = "set" + f.getName().substring(0, 1).toUpperCase()+f.getName().substring(1);

        if(WriteFile.fieldIsPrimitive(f)){

            String split = sb.substring(sb.indexOf("<"+f.getName()+">"), sb.indexOf("</"+f.getName()+">")).replace("<"+f.getName()+">", "");
            if(f.getType().equals(String.class)) {
                clazz.getDeclaredMethod(setMethodStr, String.class).invoke(o, split);
            } else if(f.getType().equals(Double.class)){
                clazz.getDeclaredMethod(setMethodStr, Double.class).invoke(o, Double.valueOf(split));
            } else if(f.getType().equals(Integer.class)) {
                clazz.getDeclaredMethod(setMethodStr, Integer.class).invoke(o, Integer.valueOf(split));
            }
        } else if(f.getType().equals(List.class)) {

            Pattern pattern = Pattern.compile("<"+clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1) + ">" + "<"+f.getName()+">.*</"+f.getName()+">"+"</"+clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1) + ">");
            Matcher matcher = pattern.matcher(sb);
            List<String> list = new ArrayList<>();
            while(matcher.find()) {
                String res = matcher.group().replace("<"+clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1) + ">", "").replace("</"+clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1) + ">", "");
                String[] split2 = res.split("<"+f.getName()+">");

                for(int i = 1; i < split2.length; i++){
                    list.add(split2[i].replace("</"+f.getName()+">", ""));
                }
            }
            clazz.getDeclaredMethod(setMethodStr, List.class).invoke(o, list);
        } else {
            Object o2 = f.getType().getDeclaredConstructor().newInstance();
            for(Field f2 : f.getType().getDeclaredFields()) {

                readSubObject(f.getType(), f2, o2, sb);
            }
            clazz.getDeclaredMethod(setMethodStr, f.getType()).invoke(o, f.getType().cast(o2));
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
