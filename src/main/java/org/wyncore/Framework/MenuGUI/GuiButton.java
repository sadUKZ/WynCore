package org.wyncore.Framework.MenuGUI;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public final class GuiButton {

    private final Function<GuiSession, ItemStack> itemProvider;
    private final Consumer<GuiClick> clickHandler;

    private GuiButton(Function<GuiSession, ItemStack> itemProvider, Consumer<GuiClick> clickHandler) {
        this.itemProvider = itemProvider;
        this.clickHandler = clickHandler;
    }

    public static GuiButton of(ItemStack item, Consumer<GuiClick> clickHandler) {
        return new GuiButton(session -> item, clickHandler);
    }

    public static GuiButton dynamic(Function<GuiSession, ItemStack> itemProvider, Consumer<GuiClick> clickHandler) {
        return new GuiButton(itemProvider, clickHandler);
    }

    public ItemStack item(GuiSession session) {
        ItemStack item = itemProvider.apply(session);
        return item == null ? null : item.clone();
    }

    public void click(GuiClick click) {
        if (clickHandler != null) clickHandler.accept(click);
    }
}