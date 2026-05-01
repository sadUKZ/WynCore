package org.wyncore.Framework.MenuGUI;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Gui {

    private final String title;
    private final int rows;
    private final Map<Integer, GuiButton> buttons = new HashMap<>();
    private long updateIntervalTicks = 0L;

    public Gui(String title, int rows) {
        if (rows < 1 || rows > 6) throw new IllegalArgumentException("Rows deve ser 1-6");
        this.title = title;
        this.rows = rows;
    }

    public String title() {
        return title;
    }

    public int size() {
        return rows * 9;
    }

    public Gui button(int slot, GuiButton button) {
        buttons.put(slot, button);
        return this;
    }

    public Gui removeButton(int slot) {
        buttons.remove(slot);
        return this;
    }

    public Gui updateEvery(long ticks) {
        this.updateIntervalTicks = ticks;
        return this;
    }

    public long updateIntervalTicks() {
        return updateIntervalTicks;
    }

    public GuiButton buttonAt(int slot) {
        return buttons.get(slot);
    }

    public Inventory createInventory() {
        return Bukkit.createInventory(null, size(), title);
    }

    public void render(GuiSession session) {
        for (int slot = 0; slot < size(); slot++) {
            GuiButton button = buttons.get(slot);
            ItemStack item = button == null ? null : button.item(session);
            session.setItemDiff(slot, item);
        }
    }

    public void onOpen(GuiSession session) {}

    public void onClose(GuiSession session) {}

    public void onTick(GuiSession session) {
        render(session);
    }
}