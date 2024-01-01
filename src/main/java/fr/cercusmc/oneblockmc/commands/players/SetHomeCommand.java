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

public class SetHomeCommand implements SubCommand {
    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.sethome";
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
            if (!is.get().getId().equals(player.getUniqueId().toString())) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("not_owner_island"));
                return;
            }

            IslandUtils.modifyHome(is.get(), player.getLocation());
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("success_sethome"));


        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }

    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_sethome_description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("sh");
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_sethome_syntax");
    }
}
