package org.wyncore.Framework.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemAPI {

    private final JavaPlugin plugin;

    public ItemAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Builder create(Material material) {
        return new Builder(plugin, material, 1);
    }

    public Builder create(Material material, int amount) {
        return new Builder(plugin, material, amount);
    }

    public static class Builder {

        private final JavaPlugin plugin;
        private final ItemStack item;
        private final ItemMeta meta;

        private Builder(JavaPlugin plugin, Material material, int amount) {
            this.plugin = plugin;
            this.item = new ItemStack(material, amount);
            this.meta = item.getItemMeta();
        }

        public Builder id(String id) {
            tagString(ItemKeys.ITEM_ID, id);
            return this;
        }

        public Builder unique() {
            tagBoolean(ItemKeys.UNIQUE_ITEM, true);
            tagString(ItemKeys.UNIQUE_ID, UUID.randomUUID().toString());
            tagBoolean(ItemKeys.STACKABLE, false);
            item.setAmount(1);
            return this;
        }

        public Builder stackable(boolean value) {
            tagBoolean(ItemKeys.STACKABLE, value);
            return this;
        }

        public Builder name(String name) {
            meta.setDisplayName(color(name));
            return this;
        }

        public Builder lore(String... lines) {
            meta.setLore(Arrays.stream(lines).map(Builder::color).toList());
            return this;
        }

        public Builder lore(List<String> lines) {
            meta.setLore(lines.stream().map(Builder::color).toList());
            return this;
        }

        public Builder enchant(Enchantment enchantment, int level) {
            meta.addEnchant(enchantment, level, true);
            return this;
        }

        public Builder flag(ItemFlag flag) {
            meta.addItemFlags(flag);
            return this;
        }

        public Builder tagString(String key, String value) {
            meta.getPersistentDataContainer().set(
                    key(key),
                    PersistentDataType.STRING,
                    value
            );
            return this;
        }

        public Builder tagInt(String key, int value) {
            meta.getPersistentDataContainer().set(
                    key(key),
                    PersistentDataType.INTEGER,
                    value
            );
            return this;
        }

        public Builder tagBoolean(String key, boolean value) {
            meta.getPersistentDataContainer().set(
                    key(key),
                    PersistentDataType.BOOLEAN,
                    value
            );
            return this;
        }

        public ItemStack build() {
            item.setItemMeta(meta);
            return item;
        }

        private NamespacedKey key(String key) {
            return new NamespacedKey(plugin, key.toLowerCase());
        }

        private static String color(String text) {
            return text.replace("&", "§");
        }
    }
}