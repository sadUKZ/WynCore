package org.wyncore.Framework.Command.Core;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.wyncore.Framework.Command.Annotation.CommandInfo;
import org.wyncore.Framework.Command.Annotation.SubCommandInfo;
import org.wyncore.Framework.Command.Argument.ArgumentRegistry;
import org.wyncore.Framework.Command.Argument.ArgumentResolver;
import org.wyncore.Framework.Command.Argument.Resolvers.IntegerResolver;
import org.wyncore.Framework.Command.Argument.Resolvers.PlayerResolver;
import org.wyncore.Framework.Command.Argument.Resolvers.StringResolver;
import org.wyncore.WynCore;

import java.lang.reflect.*;
import java.util.*;

public class CommandFramework {

    private final JavaPlugin plugin;
    private final ArgumentRegistry registry = new ArgumentRegistry();

    public CommandFramework(JavaPlugin plugin) {
        this.plugin = plugin;

        registry.register(new StringResolver());
        registry.register(new IntegerResolver());
        registry.register(new PlayerResolver());
    }

    public void scan(String packageName) {
        Reflections reflections = new Reflections(packageName);

        for (Class<?> clazz : reflections.getTypesAnnotatedWith(CommandInfo.class)) {
            register(clazz);
        }
    }

    private void register(Class<?> clazz) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            CommandInfo info = clazz.getAnnotation(CommandInfo.class);

            List<RegisteredSubCommand> subs = new ArrayList<>();

            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(SubCommandInfo.class)) continue;

                method.setAccessible(true);

                subs.add(new RegisteredSubCommand(
                        instance,
                        method,
                        method.getAnnotation(SubCommandInfo.class)
                ));
            }

            RegisteredCommand command = new RegisteredCommand(instance, info, subs, this);

            getCommandMap().register(plugin.getName(), command);

            Bukkit.getConsoleSender().sendMessage(
                    "§7[§3" + plugin.getName() + "§7] §aCommand §f/" + info.name() + " §aSuccessfully registered "
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invokeSubCommand(CommandSender sender, String label, String[] args, RegisteredSubCommand sub) {
        try {
            List<Object> values = new ArrayList<>();
            int argIndex = 1;

            for (Class<?> param : sub.method().getParameterTypes()) {

                if (param == CommandContext.class) {
                    values.add(new CommandContext(sender, label, args));
                    continue;
                }

                ArgumentResolver<?> resolver = registry.get(param);

                if (resolver == null) {
                    sender.sendMessage("§cType not supported: " + param.getSimpleName());
                    return;
                }

                if (argIndex >= args.length) {
                    sender.sendMessage("§cUsage: /" + label + " " + sub.info().usage());
                    return;
                }

                Object value = resolver.resolve(sender, args[argIndex]);

                if (value == null) {
                    sender.sendMessage("§cInvalid value: " + args[argIndex]);
                    return;
                }

                values.add(value);
                argIndex++;
            }

            sub.method().invoke(sub.instance(), values.toArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> tabCompleteArguments(CommandSender sender, String[] args, RegisteredSubCommand sub) {
        int index = args.length - 2;
        int current = 0;

        for (Class<?> param : sub.method().getParameterTypes()) {

            if (param == CommandContext.class) continue;

            if (current == index) {
                ArgumentResolver<?> resolver = registry.get(param);
                return resolver != null
                        ? resolver.tabComplete(sender, args[args.length - 1])
                        : Collections.emptyList();
            }

            current++;
        }

        return Collections.emptyList();
    }

    public boolean canExecute(CommandSender sender, CommandSenderType type) {

        if (type == CommandSenderType.PLAYER && !(sender instanceof Player)) {
            sender.sendMessage("§cOnly in-game players.");
            return false;
        }

        if (type == CommandSenderType.CONSOLE && sender instanceof Player) {
            sender.sendMessage("§cConsole only.");
            return false;
        }

        return true;
    }

    private CommandMap getCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(Bukkit.getServer());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void invokeDefault(CommandSender sender, String label, String[] args, Object instance, Method method) {
        try {

            List<Object> values = new ArrayList<>();
            int argIndex = 0;

            for (Class<?> param : method.getParameterTypes()) {

                if (param == CommandContext.class) {
                    values.add(new CommandContext(sender, label, args));
                    continue;
                }

                ArgumentResolver<?> resolver = arguments().get(param);

                if (resolver == null) {
                    sender.sendMessage("§cTipo não suportado: " + param.getSimpleName());
                    return;
                }

                if (argIndex >= args.length) {
                    sender.sendMessage("§cUso incorreto.");
                    return;
                }

                Object value = resolver.resolve(sender, args[argIndex]);

                if (value == null) {
                    sender.sendMessage("§cArgumento inválido: " + args[argIndex]);
                    return;
                }

                values.add(value);
                argIndex++;
            }

            method.invoke(instance, values.toArray());

        } catch (Exception e) {
            sender.sendMessage("§cErro ao executar comando.");
            e.printStackTrace();
        }
    }

    public ArgumentRegistry arguments() {
        return registry;
    }
}