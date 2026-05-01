package org.wyncore.Framework.MenuGUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class GuiAPI {

    private static JavaPlugin plugin;
    private static final Map<UUID, GuiSession> sessions = new ConcurrentHashMap<>();

    private GuiAPI() {}

    public static void init(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        Bukkit.getPluginManager().registerEvents(new GuiListener(), plugin);
    }

    public static JavaPlugin plugin() {
        return plugin;
    }

    public static void open(Player player, Gui gui) {
        GuiSession old = sessions.remove(player.getUniqueId());
        if (old != null) old.destroy(false);

        GuiSession session = new GuiSession(player, gui);
        sessions.put(player.getUniqueId(), session);
        session.open();
    }

    public static void close(Player player) {
        GuiSession session = sessions.remove(player.getUniqueId());
        if (session != null) session.destroy(true);
        player.closeInventory();
    }

    static GuiSession session(Player player) {
        return sessions.get(player.getUniqueId());
    }

    static void remove(Player player) {
        GuiSession session = sessions.remove(player.getUniqueId());
        if (session != null) session.destroy(false);
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static void syncLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }
}