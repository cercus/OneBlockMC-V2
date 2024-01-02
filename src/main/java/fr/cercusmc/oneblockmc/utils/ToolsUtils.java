package fr.cercusmc.oneblockmc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolsUtils {

    /**
     * Convert a string into a list of string, delimited by a delimiter
     * @param s String
     * @param delimiter Delimiter
     */
    public static List<String> convertRawStringListToList(String s, String delimiter) {
        if(!s.startsWith("[") || !s.endsWith("]")) return new ArrayList<>();

        s = s.replace("[", "").replace("]", "");
        String[] split = s.split("\""+delimiter+ " +");

        return Arrays.stream(split).map(k -> k.replaceAll("(^\")|(\"$)", "")).toList();
    }
}
