package fr.cercusmc.oneblockmc.utils;

import fr.cercusmc.oneblockmc.OneBlockMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    private static final Pattern patternHex = Pattern.compile("#[a-fA-F0-9]{6}");

    private MessageUtil() {
    }

    /**
     * Translate a message given with a conversion of hex code and color minecraft
     * in real colors and replace placeholders given with values given <br />
     * Hex code format : #[a-fA-F0-9]{6} - Exemple : #A777E2, #000000, #FFFFFF
     * <br />
     * Color minecraft : &amp;[4c6e2ab319d5f780] - Exemple : &amp;e : yellow <br />
     * List of colors minecraft : <a href="https://htmlcolorcodes.com/minecraft-color-codes/">Colors</a>
     *
     * @param message The message to format
     * @param values Map who contains for each placeholder a value

     * @return A String with formatted message
     */
    public static String format(String message, Map<String, Object> values) {
        message = format(message);

        if(values == null) return ChatColor.translateAlternateColorCodes('&', message);

        for(Map.Entry<String, Object>entry : values.entrySet()) {
            message= message.replace(entry.getKey(), entry.getValue().toString());
        }
        return ChatColor.translateAlternateColorCodes('&', message);

    }

    /**
     * Translate a message given with a conversion of hex code and color minecraft
     * in real colors Hex code format : #[a-fA-F0-9]{6} - Exemple : #A777E2,
     * #000000, #FFFFFF <br />
     * Color minecraft : &amp;[4c6e2ab319d5f780] - Exemple : &amp;e : yellow <br />
     * List of colors minecraft : <a href="https://htmlcolorcodes.com/minecraft-color-codes/">Colors</a>
     *
     * @param message The message to format
     * @return A String with formatted message
     */
    public static String format(String message) {
        Matcher matcher = patternHex.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = patternHex.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }



    /**
     * Translate a collection of messages given with a conversion of hex code and
     * color minecraft in real colors and replace placeholders given with values
     * given <br />
     * Hex code format : #[a-fA-F0-9]{6} - Exemple : #A777E2, #000000, #FFFFFF
     * <br />
     * Color minecraft : &amp;[4c6e2ab319d5f780] - Exemple : &amp;e : yellow <br />
     * List of colors minecraft : <a href="https://htmlcolorcodes.com/minecraft-color-codes/">Colors</a>
     *
     * @param messages     List of messages to format
     * @param values       List of values for each placeholder to replace - Can be
     *                     null
     * @return List of String with formatted message
     */
    public static List<String> format(Collection<String> messages, @Nullable Map<String, Object> values) {

        return messages.stream().map(k -> format(k, values)).toList();
    }

    /**
     * Translate a collection of messages given with a conversion of hex code and
     * color minecraft in real colors Hex code format : #[a-fA-F0-9]{6} - Exemple :
     * #A777E2, #000000, #FFFFFF <br />
     * Color minecraft : &amp;[4c6e2ab319d5f780] - Exemple : &amp;e : yellow <br />
     * List of colors minecraft : <a href="https://htmlcolorcodes.com/minecraft-color-codes/">Colors</a>
     *
     * @param messages The message to format
     * @return A String with formatted message
     */
    public static List<String> format(Collection<String> messages) {
        return format(messages, null);
    }

    /**
     * Send a message to player by his UUID
     *
     * @param uuid    The UUID of player
     * @param message The message to send
     */
    public static void sendMessage(UUID uuid, String message) {
        if(uuid == null) return;
        Player p = Bukkit.getPlayer(uuid);
        if (p != null)
           p.sendMessage(format(message, null));
    }

    /**
     * Send a message to player by his UUID
     *
     * @param uuid         The UUID of player
     * @param message      The message to send
     * @param values       List of values for each placeholder to replace - Can be
     *                     null
     */
    public static void sendMessage(UUID uuid, String message, @Nullable Map<String, Object> values) {
        if(uuid == null) return;
        Player p = Bukkit.getPlayer(uuid);
        if (p != null)
            p.sendMessage(format(message, values));
    }

    /**
     * Send a list of messages to player by his UUID
     *
     * @param uuid     The UUID of player
     * @param messages A list of messages to send
     */
    public static void sendMessage(UUID uuid, Collection<String> messages) {
        messages.forEach(k -> sendMessage(uuid, k));

    }

    /**
     * Send a list of messages to player by his UUID
     *
     * @param uuid         The UUID of player
     * @param messages     The message to send
     * @param values       List of values for each placeholder to replace - Can be
     *                     null
     */
    public static void sendMessage(UUID uuid, Collection<String> messages, @Nullable Map<String, Object> values) {
        messages.forEach(k -> sendMessage(uuid, k, values));
    }

    /**
     * Broadcast a list of messages to all player
     *
     * @param messages     : List of messages to send to the player
     * @param values:      List of values associated in placeholder - Can be null
     */
    public static void broadcastMessage(Collection<String> messages, @Nullable Map<String, Object> values) {
        messages.forEach(k -> broadcastMessage(k, values));
    }

    /**
     * Broadcast a message to all player
     *
     * @param message      : List of messages to send to the player
     * @param values:      List of values associated in placeholder - Can be null
     */
    public static void broadcastMessage(String message, @Nullable Map<String, Object> values) {
        Bukkit.broadcastMessage(format(message, values));
    }

    /**
     * Broadcast a message to all player
     *
     * @param message : List of messages to send to the player
     *
     */
    public static void broadcastMessage(String message) {
        Bukkit.broadcastMessage(format(message, null));
    }
}
