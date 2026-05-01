package org.wyncore.Framework.ItemBuilder;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemNBT {

    private final JavaPlugin plugin;

    public ItemNBT(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean has(ItemStack item, String key) {
        if (!valid(item)) return false;
        return item.getItemMeta().getPersistentDataContainer().has(key(key));
    }

    public String getString(ItemStack item, String key) {
        if (!valid(item)) return null;
        return item.getItemMeta().getPersistentDataContainer()
                .get(key(key), PersistentDataType.STRING);
    }

    public Integer getInt(ItemStack item, String key) {
        if (!valid(item)) return null;
        return item.getItemMeta().getPersistentDataContainer()
                .get(key(key), PersistentDataType.INTEGER);
    }

    public Boolean getBoolean(ItemStack item, String key) {
        if (!valid(item)) return null;
        return item.getItemMeta().getPersistentDataContainer()
                .get(key(key), PersistentDataType.BOOLEAN);
    }

    public boolean isUnique(ItemStack item) {
        Boolean value = getBoolean(item, ItemKeys.UNIQUE_ITEM);
        return value != null && value;
    }

    public boolean isStackable(ItemStack item) {
        Boolean value = getBoolean(item, ItemKeys.STACKABLE);
        return value == null || value;
    }

    public String getItemId(ItemStack item) {
        return getString(item, ItemKeys.ITEM_ID);
    }

    public String getUniqueId(ItemStack item) {
        return getString(item, ItemKeys.UNIQUE_ID);
    }

    private boolean valid(ItemStack item) {
        return item != null && item.hasItemMeta();
    }

    private NamespacedKey key(String key) {
        return new NamespacedKey(plugin, key.toLowerCase());
    }
}