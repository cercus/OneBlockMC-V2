package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class DeleteCommand implements SubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.delete";
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

            IslandUtils.deleteIsland(is.get(), player.getUniqueId());

            for(String i : is.get().getMembers().getPlayers()) {

                Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(i))).teleport(IslandUtils.getSpawn());
            }

        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_delete_description");
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("d", "del");
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_delete_syntax");
    }
}
