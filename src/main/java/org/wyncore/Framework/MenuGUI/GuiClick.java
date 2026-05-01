package org.wyncore.Framework.MenuGUI;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public record GuiClick(
        Player player,
        GuiSession session,
        int slot,
        ClickType clickType,
        InventoryAction action,
        ItemStack currentItem
) {
    public void refresh() {
        session.refresh();
    }

    public void close() {
        GuiAPI.sync(() -> GuiAPI.close(player));
    }

    public void open(Gui gui) {
        GuiAPI.sync(() -> GuiAPI.open(player, gui));
    }

    public boolean isLeft() {
        return clickType.isLeftClick();
    }

    public boolean isRight() {
        return clickType.isRightClick();
    }

    public boolean isShift() {
        return clickType.isShiftClick();
    }
}