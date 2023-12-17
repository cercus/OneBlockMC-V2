package fr.cercusmc.oneblockmc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class WriteFile {

    public static boolean objectToXml(final Object object, String fileName) {
        if(object == null) return false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            OutputStream os = new FileOutputStream(fileName);
            jaxbMarshaller.marshal(object, os);
            return true;
        } catch(JAXBException | FileNotFoundException e) {
            return false;
        }

    }

    public static boolean objectToJson(Object object, String fileName){
        if(object == null) return false;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(object);
            System.out.println(json);
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(json);
            bw.close();

            return true;
        } catch (IOException e) {
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
