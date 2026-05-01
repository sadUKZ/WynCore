package org.wyncore.Framework.Command.Core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandContext {

    private final CommandSender sender;
    private final String label;
    private final String[] args;

    public CommandContext(CommandSender sender, String label, String[] args) {
        this.sender = sender;
        this.label = label;
        this.args = args;
    }

    public CommandSender sender() {
        return sender;
    }

    public Player getPlayer() {
        return sender instanceof Player player ? player : null;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public String label() {
        return label;
    }

    public String[] args() {
        return args;
    }

    public String arg(int index) {
        return index >= 0 && index < args.length ? args[index] : null;
    }
}