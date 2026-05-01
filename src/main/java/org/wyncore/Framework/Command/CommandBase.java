package org.wyncore.Framework.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class CommandBase {

    private String name;
    private String permission;
    private String usage;
    private String description;
    private SenderType senderType;
    private List<String> aliases = new ArrayList<>();
    private final List<SubCommand> subCommands = new ArrayList<>();

    public CommandBase() {}

    public void registerSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
    }

    public abstract void execute(CommandContext context);

    public List<String> tabComplete(CommandContext context) {
        return new ArrayList<>();
    }

    public void executeCommand(CommandSender sender, String label, String[] args) {

        CommandContext context = new CommandContext(sender, label, args);

        if (!canUseSender(sender, senderType)) return;

        if (!hasPermission(sender)) {
            sender.sendMessage("§cSem permissão: §7" + permission);
            return;
        }

        if (args.length == 0) {
            execute(context);
            return;
        }

        SubCommand subCommand = findSubCommand(args[0]);

        if (subCommand == null) {
            execute(context);
            return;
        }

        if (!canUseSender(sender, subCommand.senderType())) return;

        if (!subCommand.hasPermission(sender)) {
            sender.sendMessage("§cSem permissão: §7" + subCommand.permission());
            return;
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        CommandContext subContext = new CommandContext(sender, label, subArgs);

        subCommand.execute(subContext);
    }

    public List<String> tab(CommandSender sender, String label, String[] args) {

        CommandContext context = new CommandContext(sender, label, args);

        if (args.length == 1) {
            List<String> result = new ArrayList<>();

            for (SubCommand sub : subCommands) {

                if (!sub.hasPermission(sender)) continue;

                if (sub.name().toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(sub.name());
                }

                for (String alias : sub.aliases()) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase())) {
                        result.add(alias);
                    }
                }
            }

            result.addAll(tabComplete(context));
            return result;
        }

        SubCommand sub = findSubCommand(args[0]);

        if (sub == null) {
            return tabComplete(context);
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        CommandContext subContext = new CommandContext(sender, label, subArgs);

        return sub.tabComplete(subContext);
    }

    private SubCommand findSubCommand(String input) {
        for (SubCommand sub : subCommands) {
            if (sub.matches(input)) return sub;
        }
        return null;
    }

    private boolean canUseSender(CommandSender sender, SenderType type) {

        if (type == SenderType.BOTH) return true;

        if (type == SenderType.PLAYER && !(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores.");
            return false;
        }

        if (type == SenderType.CONSOLE && sender instanceof Player) {
            sender.sendMessage("§cApenas console.");
            return false;
        }

        return true;
    }

    private boolean hasPermission(CommandSender sender) {
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