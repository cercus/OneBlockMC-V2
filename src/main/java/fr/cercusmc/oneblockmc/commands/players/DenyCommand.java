package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class DenyCommand implements SubCommand {
    @Override
    public String getName() {
        return "deny";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.deny";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length == 1) {
            if (!OneBlockMC.getInvites().containsValue(player.getUniqueId())) {
                MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("no_invitation"));
                return;
            }

            Optional<UUID> uuidTarget = AcceptCommand.findMatch(player.getUniqueId());

            if(uuidTarget.isEmpty()) return;

            OneBlockMC.getInvites().remove(uuidTarget.get());
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("success_deny_player"), Collections.singletonMap("%player%", Objects.requireNonNull(Bukkit.getPlayer(uuidTarget.get())).getName()));
            MessageUtil.sendMessage(uuidTarget.get(), OneBlockMC.getMessages().get("success_deny_target"), Collections.singletonMap("%player%", player.getName()));

        } else {
            MessageUtil.sendMessage(player.getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));

        }

    }

    @Override
    public String getDescription() {
        return OneBlockMC.getMessages().get("command_deny_description");
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getSyntax() {
        return OneBlockMC.getMessages().get("command_deny_syntax");
    }
}
