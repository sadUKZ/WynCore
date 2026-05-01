package org.wyncore.Framework.MenuGUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

public final class GuiSession {

    private final Player player;
    private final Gui gui;
    private final Inventory inventory;
    private final ItemStack[] cache;
    private BukkitTask updateTask;
    private boolean destroyed;

    public GuiSession(Player player, Gui gui) {
        this.player = player;
        this.gui = gui;
        this.inventory = gui.createInventory();
        this.cache = new ItemStack[gui.size()];
    }

    public Player player() {
        return player;
    }

    public Gui gui() {
        return gui;
    }

    public Inventory inventory() {
        return inventory;
    }

    public void open() {
        gui.render(this);
        player.openInventory(inventory);
        gui.onOpen(this);

        if (gui.updateIntervalTicks() > 0) {
            updateTask = Bukkit.getScheduler().runTaskTimer(
                    GuiAPI.plugin(),
                    () -> {
                        if (!player.isOnline() || destroyed) {
                            destroy(false);
                            return;
                        }
                        gui.onTick(this);
                    },
                    gui.updateIntervalTicks(),
                    gui.updateIntervalTicks()
            );
        }
    }

    public void setItemDiff(int slot, ItemStack item) {
        if (slot < 0 || slot >= inventory.getSize()) return;

        ItemStack old = cache[slot];

        if (same(old, item)) return;

        cache[slot] = item == null ? null : item.clone();
        inventory.setItem(slot, item);
    }

    public void refresh() {
        gui.render(this);
    }

    public void destroy(boolean callClose) {
        if (destroyed) return;
        destroyed = true;

        if (updateTask != null) {
            updateTask.cancel();
            updateTask = null;
        }

        Arrays.fill(cache, null);

        if (callClose) {
            gui.onClose(this);
        }
    }

    private boolean same(ItemStack a, ItemStack b) {
        if (a == null || b == null) return a == b;
        return a.isSimilar(b) && a.getAmount() == b.getAmount();
    }
}