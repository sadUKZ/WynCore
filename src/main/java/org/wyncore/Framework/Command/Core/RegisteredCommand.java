package org.wyncore.Framework.Command.Core;

import org.bukkit.command.*;
import org.wyncore.Framework.Command.Annotation.CommandInfo;

import java.lang.reflect.Method;
import java.util.*;

public class RegisteredCommand extends Command {

    private final Object commandInstance;
    private final CommandInfo info;
    private final List<RegisteredSubCommand> subCommands;
    private final CommandFramework framework;
    private Method defaultMethod;

    public RegisteredCommand(
            Object commandInstance,
            CommandInfo info,
            List<RegisteredSubCommand> subCommands,
            CommandFramework framework
    ) {
        super(info.name(), info.description(), "/" + info.name(), Arrays.asList(info.aliases()));
        this.commandInstance = commandInstance;
        this.info = info;
        this.subCommands = subCommands;
        this.framework = framework;

        if (!info.permission().isEmpty()) {
            setPermission(info.permission());
        }
        for (Method method : commandInstance.getClass().getDeclaredMethods()) {

            if (method.isAnnotationPresent(org.wyncore.Framework.Command.Annotation.DefaultCommand.class)) {
                method.setAccessible(true);
                this.defaultMethod = method;
            }
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!framework.canExecute(sender, info.sender())) return true;

        if (!info.permission().isEmpty() && !sender.hasPermission(info.permission())) {
            sender.sendMessage("§cSem permissão.");
            return true;
        }

        if (defaultMethod != null) {
            framework.invokeDefault(sender, label, args, commandInstance, defaultMethod);
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender, label);
            return true;
        }

        RegisteredSubCommand sub = subCommands.stream()
                .filter(s -> s.matches(args[0]))
                .findFirst()
                .orElse(null);

        if (sub == null) {
            sender.sendMessage("§cSubcomando inválido.");
            sendHelp(sender, label);
            return true;
        }

        if (!framework.canExecute(sender, sub.info().sender())) return true;

        if (!sub.info().permission().isEmpty() && !sender.hasPermission(sub.info().permission())) {
            sender.sendMessage("§cSem permissão.");
            return true;
        }

        framework.invokeSubCommand(sender, label, args, sub);
        return true;
    }


    private void sendHelp(CommandSender sender, String label) {
        sender.sendMessage("§eSubcomandos:");

        for (RegisteredSubCommand sub : subCommands) {
            sender.sendMessage(sub.info().usage() + " §8- §f" + sub.info().description());
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {

        if (args.length == 1) {
            return subCommands.stream()
                    .map(s -> s.info().name())
                    .filter(n -> n.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }

        RegisteredSubCommand sub = subCommands.stream()
                .filter(s -> s.matches(args[0]))
                .findFirst()
                .orElse(null);

        if (sub == null) return Collections.emptyList();

        return framework.tabCompleteArguments(sender, args, sub);
    }
}