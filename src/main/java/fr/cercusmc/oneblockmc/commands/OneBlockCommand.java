package fr.cercusmc.oneblockmc.commands;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.players.*;
import fr.cercusmc.oneblockmc.islands.IslandUtils;
import fr.cercusmc.oneblockmc.islands.pojo.Island;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import fr.cercusmc.oneblockmc.utils.Position;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.*;

public class OneBlockCommand implements CommandExecutor {

    private static final List<SubCommand> subCommands = Arrays.asList(new CreateCommand(), new DeleteCommand(),
            new SetHomeCommand(), new DelHomeCommand(), new HomeCommand(), new BanCommand(),
            new KickCommand(), new SetWarpCommand(), new WarpCommand(), new LeaveCommand(),
            new InviteCommand(), new AcceptCommand(), new DenyCommand(), new HelpCommand(), new LevelCommand());


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

    public static void teleportPlayerToIsland(Player player) {

        Optional<Island> is = IslandUtils.getIslandByUuid(OneBlockMC.getIslands(), player.getUniqueId());
        is.ifPresentOrElse(
                (value)-> player.teleport(Position.getCenterOfBlock(value.getLocations().getSpawn().getLocation().toLocation())),
                () -> OneBlockMC.getIslands().add(IslandUtils.createIsland(player.getUniqueId())));
    }

    private boolean checkArg(String arg, SubCommand k) {
        return k.getName() != null && k.getAliases() != null && (k.getName().equals(arg) || k.getAliases().contains(arg));
    }

    public static List<SubCommand> getSubCommands() {
        return subCommands;
    }
}
