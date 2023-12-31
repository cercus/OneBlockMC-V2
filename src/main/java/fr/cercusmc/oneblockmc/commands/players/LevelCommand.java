package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LevelCommand implements SubCommand {
    @Override
    public String getName() {
        return "level";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.level";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1) {
            Optional<Island> is = IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), ((Player)sender).getUniqueId());
            is.ifPresentOrElse(
                    island -> IslandUtils.computeIslandLevel(island, ((Player) sender).getUniqueId()),
                    () -> MessageUtil.sendMessage(((Player)sender).getUniqueId(), OneBlockMC.getMessages().get("player_no_island")));
        } else {
            MessageUtil.sendMessage(((Player)sender).getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return MessageUtil.format(OneBlockMC.getMessages().get("command_level_description"));
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("l");
    }

    @Override
    public String getSyntax() {
        return MessageUtil.format(OneBlockMC.getMessages().get("command_level_syntax"));
    }
}
