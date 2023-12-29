package fr.cercusmc.oneblockmc.commands;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.players.*;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.*;

import static fr.cercusmc.oneblockmc.islands.IslandUtils.createIsland;

public class OneBlockCommand implements CommandExecutor {

    private final List<SubCommand> subCommands = Arrays.asList(new CreateCommand(), new DeleteCommand(),
            new SetHomeCommand(), new DelHomeCommand(), new HomeCommand(), new BanCommand(),
            new KickCommand(), new SetWarpCommand(), new WarpCommand(), new LeaveCommand(),
            new InviteCommand(), new AcceptCommand(), new DenyCommand());


    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull  String label, @Nonnull String[] args) {
        if(commandSender instanceof Player player) {
            if (args.length > 0) {
                Optional<SubCommand> sub = subCommands.stream().filter(k -> checkArg(args[0], k)).findFirst();
                if (sub.isPresent()) {
                    if (sub.get().hasPermission(commandSender, sub.get().getPermission())) {
                        sub.get().execute(commandSender, args);
                    } else {
                        MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_no_permission"));
                    }
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("%args%", subCommands.stream().map(SubCommand::getName).toList());
                    MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_arg_unknown"), map);
                }
            } else {
                teleportPlayerToIsland(player);
            }
        }

        return true;
    }

    private void teleportPlayerToIsland(Player player) {

        Optional<Island> is = IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), player.getUniqueId());
        is.ifPresentOrElse(
                (value)-> player.teleport(value.getLocations().getSpawn().getLocation().toLocation()),
                () -> OneBlockMC.getIslands().add(IslandUtils.createIsland(player.getUniqueId())));
    }

    private boolean checkArg(String arg, SubCommand k) {
        return k.getName().equals(arg) || k.getAliases().contains(arg);
    }
}
