package org.wyncore.Framework.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class CommandFramework {

    private final JavaPlugin plugin;
    private final CommandMap commandMap;

    public CommandFramework(JavaPlugin plugin) {
        this.plugin = plugin;
        this.commandMap = getCommandMap();
    }

    public void register(CommandBase command) {

        BukkitCommandWrapper wrapper = new BukkitCommandWrapper(command);

        commandMap.register(plugin.getName().toLowerCase(), wrapper);

        Bukkit.getConsoleSender().sendMessage(
                "§a[" + plugin.getName() + "] §f/" + command.name() + " registrado."
        );
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
}