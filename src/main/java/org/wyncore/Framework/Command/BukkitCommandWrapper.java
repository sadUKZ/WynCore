package org.wyncore.Framework.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BukkitCommandWrapper extends Command {

    private final CommandBase command;

    public BukkitCommandWrapper(CommandBase command) {
        super(command.name(), command.description(), command.usage(), command.aliases());
        this.command = command;

        if (command.permission() != null && !command.permission().isEmpty()) {
            setPermission(command.permission());
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        command.executeCommand(sender, label, args);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return command.tab(sender, alias, args);
    }
}