package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.Position;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class AcceptCommand implements SubCommand {
    @Override
    public String getName() {
        return "accept";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.accept";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length == 1) {
            if(!OneBlockMC.getInvites().containsValue(player.getUniqueId())) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("no_invitation"));
                return;
            }

            Optional<UUID> uuidTarget = findMatch(player.getUniqueId());

            if(uuidTarget.isEmpty()) return;

            Optional<Island> is = IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), uuidTarget.get());
            if(is.isEmpty()) return;

            IslandUtils.addMember(is.get(), player.getUniqueId());
            player.teleport(Position.getCenterOfBlock(is.get().getLocations().getSpawn().getLocation().toLocation()));
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("success_invite_player"), Collections.singletonMap("%player%", Objects.requireNonNull(Bukkit.getPlayer(uuidTarget.get())).getName()));
            MessageUtil.sendMessage(uuidTarget.get(), OneBlockMC.getMessages().get("success_invite_target"), Collections.singletonMap("%player%", player.getName()));
            OneBlockMC.getInvites().remove(uuidTarget.get());
        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    public static Optional<UUID> findMatch(UUID uuid) {

        return OneBlockMC.getInvites().entrySet().stream().filter(entry -> uuid.equals(entry.getValue()))
                .map(Map.Entry::getKey).findFirst();

    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_accept_description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_accept_syntax");
    }
}
