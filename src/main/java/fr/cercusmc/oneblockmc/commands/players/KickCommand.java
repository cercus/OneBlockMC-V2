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

public class KickCommand implements SubCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.kick";
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

            offlinePlayer.get().getPlayer().teleport(IslandUtils.getSpawn());
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("success_kick_player"), Collections.singletonMap("%player%", offlinePlayer.get().getName()));
            MessageUtil.sendMessage(offlinePlayer.get().getUniqueId(), OneBlockMC.getMessages().get("success_kick_target"), Collections.singletonMap("%player%", player.getName()));

        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_kick_description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_kick_syntax");
    }
}
