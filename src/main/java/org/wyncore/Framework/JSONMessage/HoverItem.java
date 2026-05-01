package org.wyncore.Framework.JSONMessage;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class HoverItem {

    private final Material material;
    private final int amount;

    private HoverItem(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public static HoverItem of(Material material) {
        return new HoverItem(material, 1);
    }

    public static HoverItem of(Material material, int amount) {
        return new HoverItem(material, amount);
    }

    public static HoverItem of(ItemStack itemStack) {
        return new HoverItem(itemStack.getType(), itemStack.getAmount());
    }

    public String toJson() {
        return "{\"id\":\"minecraft:" + material.name().toLowerCase() + "\",\"Count\":" + amount + "}";
    }
}