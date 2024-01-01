package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.Position;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class WarpCommand implements SubCommand {
    @Override
    public String getName() {
        return "warp";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.warp";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 2) {

            Optional<OfflinePlayer> offlinePlayer = Arrays.stream(Bukkit.getOfflinePlayers()).filter(k -> Objects.equals(k.getName(), args[1])).findFirst();

            if(offlinePlayer.isEmpty() || offlinePlayer.get().getPlayer() == null || !offlinePlayer.get().isOnline()) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("player_not_exist"));
                return;
            }

            Optional<Island> is = IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), offlinePlayer.get().getUniqueId());

            if(is.isEmpty()) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("target_not_island"));
                return;
            }

            if(is.get().getLocations().getWarp() == null) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("not_warp"));
                return;
            }

            player.teleport(is.get().getLocations().getWarp().getLocation().toLocation());
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("teleport_player_warp"));
            MessageUtil.sendMessage(offlinePlayer.get().getUniqueId(), OneBlockMC.getMessages().get("teleport_target_warp"), Collections.singletonMap("%player%", player.getName()));



        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_warp_description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("w");
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_warp_syntax");
    }
}
