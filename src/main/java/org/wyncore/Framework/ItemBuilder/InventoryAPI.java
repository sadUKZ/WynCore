package org.wyncore.Framework.ItemBuilder;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryAPI {

    private final JavaPlugin plugin;
    private final ItemNBT nbt;

    public InventoryAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        this.nbt = new ItemNBT(plugin);
    }

    public void giveSmart(Player player, ItemStack item) {
        if (player == null || item == null) return;

        if (nbt.isUnique(item) || !nbt.isStackable(item)) {
            player.getInventory().addItem(item);
            return;
        }

        int maxStack = item.getMaxStackSize();
        int remaining = item.getAmount();

        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem == null) continue;
            if (!canSmartStack(invItem, item)) continue;

            int space = maxStack - invItem.getAmount();
            if (space <= 0) continue;

            int toAdd = Math.min(space, remaining);
            invItem.setAmount(invItem.getAmount() + toAdd);
            remaining -= toAdd;

            if (remaining <= 0) return;
        }

        while (remaining > 0) {
            ItemStack clone = item.clone();
            clone.setAmount(Math.min(maxStack, remaining));
            player.getInventory().addItem(clone);
            remaining -= clone.getAmount();
        }
    }

    public boolean canSmartStack(ItemStack a, ItemStack b) {
        if (a == null || b == null) return false;

        if (nbt.isUnique(a) || nbt.isUnique(b)) return false;
        if (!nbt.isStackable(a) || !nbt.isStackable(b)) return false;

        String idA = nbt.getItemId(a);
        String idB = nbt.getItemId(b);

        if (idA == null || idB == null) return false;
        if (!idA.equals(idB)) return false;

        ItemStack cloneA = a.clone();
        ItemStack cloneB = b.clone();

        cloneA.setAmount(1);
        cloneB.setAmount(1);

        return cloneA.isSimilar(cloneB);
    }

    public int removeByTag(Player player, String key, String value, int amount) {
        if (player == null || key == null || value == null || amount <= 0) return 0;

        NamespacedKey namespacedKey = new NamespacedKey(plugin, key.toLowerCase());
        int removed = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || !item.hasItemMeta()) continue;

            ItemMeta meta = item.getItemMeta();

            String tagValue = meta.getPersistentDataContainer()
                    .get(namespacedKey, PersistentDataType.STRING);

            if (!value.equals(tagValue)) continue;

            int canRemove = Math.min(item.getAmount(), amount - removed);
            item.setAmount(item.getAmount() - canRemove);
            removed += canRemove;

            if (removed >= amount) break;
        }

        player.updateInventory();
        return removed;
    }

    public int removeByItemId(Player player, String itemId, int amount) {
        return removeByTag(player, ItemKeys.ITEM_ID, itemId, amount);
    }

    public boolean removeUnique(Player player, String uuid) {
        if (player == null || uuid == null) return false;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;

            String itemUUID = nbt.getUniqueId(item);

            if (uuid.equals(itemUUID)) {
                item.setAmount(0);
                player.updateInventory();
                return true;
            }
        }

        return false;
    }
}