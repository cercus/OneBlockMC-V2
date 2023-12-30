package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.OneBlockCommand;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CreateCommand implements SubCommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "oneblock.player.create";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1) {
            OneBlockCommand.teleportPlayerToIsland((Player) sender);
        } else{
            MessageUtil.sendMessage(((Player)sender).getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return MessageUtil.format(OneBlockMC.getMessages().get("command_create_description"));
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("c");
    }

    @Override
    public String getSyntax() {
        return MessageUtil.format(OneBlockMC.getMessages().get("command_create_syntax"));
    }
}
