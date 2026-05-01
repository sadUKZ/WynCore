package org.wyncore.Framework.MenuGUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public final class GuiListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        GuiSession session = GuiAPI.session(player);
        if (session == null) return;

        if (!event.getView().getTopInventory().equals(session.inventory())) return;

        int rawSlot = event.getRawSlot();

        // Bloqueia shift-click, number-key, hotbar swap e movimentos no menu.
        event.setCancelled(true);

        if (rawSlot < 0 || rawSlot >= session.inventory().getSize()) return;

        GuiButton button = session.gui().buttonAt(rawSlot);
        if (button == null) return;

        GuiClick click = new GuiClick(
                player,
                session,
                rawSlot,
                event.getClick(),
                event.getAction(),
                event.getCurrentItem() == null ? null : event.getCurrentItem().clone()
        );

        // Próximo tick evita mexer em inventário dentro do InventoryClickEvent.
        GuiAPI.sync(() -> button.click(click));
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        GuiSession session = GuiAPI.session(player);
        if (session == null) return;

        if (!event.getView().getTopInventory().equals(session.inventory())) return;

        for (int slot : event.getRawSlots()) {
            if (slot < session.inventory().getSize()) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        GuiSession session = GuiAPI.session(player);
        if (session == null) return;

        if (!event.getView().getTopInventory().equals(session.inventory())) return;

        GuiAPI.remove(player);
    }
}