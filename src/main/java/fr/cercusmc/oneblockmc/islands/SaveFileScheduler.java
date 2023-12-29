package fr.cercusmc.oneblockmc.islands;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.islands.pojo.Members;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.WriteFile;
import fr.cercusmc.oneblockmc.utils.enums.FileType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public class SaveFileScheduler implements Runnable {

    private FileType type;

    public SaveFileScheduler(FileType type) {
        this.type = type;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        OneBlockMC.getLog().info(MessageUtil.format(OneBlockMC.getMessages().get("save_file_in_progress")));
        final String pathIsland = OneBlockMC.getInstance().getPathIsland();

        saveFiles(pathIsland, this.type);
        OneBlockMC.getLog().info(MessageUtil.format(OneBlockMC.getMessages().get("save_file_finished")));
    }

    public static void saveFiles(String pathIsland, FileType type) {
        switch (type) {
            case JSON -> {
                for (File file : Objects.requireNonNull(new File(pathIsland + "json/").listFiles())) file.delete();
            }
            case YAML -> {
                for (File file : Objects.requireNonNull(new File(pathIsland + "yaml/").listFiles()))
                    file.delete();
            }
        }
        for(Map.Entry<String, Island> is : OneBlockMC.getIslands().entrySet()){

            switch(type) {
                case JSON -> WriteFile.objectToJson(is.getValue(), pathIsland +"json/"+is.getKey().replace(is.getKey().substring(is.getKey().indexOf('.')), ".json"));

                case YAML -> WriteFile.objectToYml(is.getValue(), pathIsland +"yaml/"+is.getKey().replace(is.getKey().substring(is.getKey().indexOf('.')), ".yml"));

            }
        }
    }
}
