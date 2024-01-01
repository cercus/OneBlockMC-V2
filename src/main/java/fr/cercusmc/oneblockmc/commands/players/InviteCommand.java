package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class InviteCommand implements SubCommand {
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.invite";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Optional<Island> is = IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), player.getUniqueId());

        if(is.isEmpty()) {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("player_no_island"));
            return;
        }

        if(args.length == 2) {
            Optional<OfflinePlayer> offlinePlayer = Arrays.stream(Bukkit.getOfflinePlayers()).filter(k -> Objects.equals(k.getName(), args[1])).findFirst();
            if(offlinePlayer.isEmpty() || offlinePlayer.get().getPlayer() == null || !offlinePlayer.get().isOnline()) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("player_not_exist"));
                return;
            }

            if(IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), offlinePlayer.get().getUniqueId()).isPresent()) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("target_has_island"));
                return;
            }

            if(OneBlockMC.getInvites().containsValue(offlinePlayer.get().getUniqueId())) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("player_already_in_invitation"));
                return;
            }

            OneBlockMC.getInvites().put(player.getUniqueId(), offlinePlayer.get().getUniqueId());
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("success_send_invite_player"), Collections.singletonMap("%player%", offlinePlayer.get().getName()));
            MessageUtil.sendMessage(offlinePlayer.get().getUniqueId(), OneBlockMC.getMessages().get("success_send_invite_target"), Collections.singletonMap("%player%", player.getName()));

        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_invite_description");
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("inv", "in");
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_invite_syntax");
    }
}
