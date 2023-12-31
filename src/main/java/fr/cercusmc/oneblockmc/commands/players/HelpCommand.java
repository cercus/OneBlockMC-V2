package fr.cercusmc.oneblockmc.commands.players;

import fr.cercusmc.oneblockmc.OneBlockMC;
import fr.cercusmc.oneblockmc.commands.OneBlockCommand;
import fr.cercusmc.oneblockmc.commands.SubCommand;
import fr.cercusmc.oneblockmc.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "oneblockmc.player.help";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1) {
            for(SubCommand s : OneBlockCommand.getSubCommands()) {
                if(s.getSyntax() == null || s.getDescription() == null) continue;
                MessageUtil.sendMessage(((Player) sender).getUniqueId(), OneBlockMC.getMessages().get("format_help_command").replace("%syntax%", s.getSyntax()).replace("%description%", s.getDescription()));
            }

        } else {
            MessageUtil.sendMessage(((Player)sender).getUniqueId(), OneBlockMC.getMessages().get("command_too_many_args"));
        }
    }

    @Override
    public String getDescription() {
        return MessageUtil.format(OneBlockMC.getMessages().get("command_help_description"));
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("h");
    }

    @Override
    public String getSyntax() {
        return MessageUtil.format(OneBlockMC.getMessages().get("command_help_syntax"));
    }
}
