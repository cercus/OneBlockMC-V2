package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LeaveCommand implements SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.leave";
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
            if(!IslandUtils.playerIsOwner(is.get(), player.getUniqueId())) {
               MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("owner_not_quit_island"));
               return;
            }

            IslandUtils.removeMember(is.get(), player.getUniqueId());
            player.teleport(IslandUtils.getSpawn());
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("success_leave_island"), Collections.singletonMap("%player%", Bukkit.getPlayer(UUID.fromString(is.get().getId()))));

        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_leave_description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("le");
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_leave_syntax");
    }
}
