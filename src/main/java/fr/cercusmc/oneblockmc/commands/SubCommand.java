package fr.cercusmc.oneblockmc.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {

    String getName();

    String getPermission();

    void execute(CommandSender sender, String[] args);

    String getDescription();

    List<String> getAliases();

    public default boolean hasPermission(CommandSender sender, String permission) {
        return sender.isOp() || sender.hasPermission(permission);
    }


}
