package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.Position;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HomeCommand implements SubCommand {
    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.home";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        Optional<Island> is = IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), player.getUniqueId());

        if(is.isEmpty()) {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("player_no_island"));
            return;
        }

        if(args.length == 1) {


            if(is.get().getLocations().getHome() != null) {
                player.teleport(Position.getCenterOfBlock(is.get().getLocations().getHome().getLocation().toLocation()));

                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("teleport_player_home"));
            } else {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("no_home"));
            }
        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }


    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_home_description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_home_syntax");
    }
}
