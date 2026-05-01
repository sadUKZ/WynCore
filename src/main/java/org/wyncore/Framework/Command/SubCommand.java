package org.wyncore.Framework.Command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    private String name;
    private String permission;
    private String usage;
    private String description;
    private SenderType senderType;
    private List<String> aliases = new ArrayList<>();

    public SubCommand() {}

    public abstract void execute(CommandContext context);

    public List<String> tabComplete(CommandContext context) {
        return new ArrayList<>();
    }

    public boolean matches(String input) {
        if (name.equalsIgnoreCase(input)) return true;

        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(input)) return true;
        }

        return false;
    }

    public boolean hasPermission(CommandSender sender) {
        return permission == null || permission.isEmpty() || sender.hasPermission(permission);
    }

    // SETTERS

    public void setName(String name) { this.name = name; }
    public void setPermission(String permission) { this.permission = permission; }
    public void setUsage(String usage) { this.usage = usage; }
    public void setDescription(String description) { this.description = description; }
    public void setSenderType(SenderType senderType) { this.senderType = senderType; }
    public void setAliases(List<String> aliases) { this.aliases = aliases; }

    // GETTERS

    public String name() { return name; }
    public String permission() { return permission; }
    public String usage() { return usage; }
    public String description() { return description; }
    public SenderType senderType() { return senderType; }
    public List<String> aliases() { return aliases; }
}