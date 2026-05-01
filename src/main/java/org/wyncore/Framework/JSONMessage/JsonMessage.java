package org.wyncore.Framework.JSONMessage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class JsonMessage {

    private final TextComponent root;

    private JsonMessage(String text) {
        this.root = new TextComponent(color(text));
    }

    public static JsonMessage of(String text) {
        return new JsonMessage(text);
    }

    public static JsonMessage empty() {
        return new JsonMessage("");
    }

    public JsonMessage append(String text) {
        root.addExtra(new TextComponent(color(text)));
        return this;
    }

    public JsonMessage append(JsonMessage message) {
        root.addExtra(message.root);
        return this;
    }

    public JsonMessage click(ClickType type, String value) {
        root.setClickEvent(new ClickEvent(type.getAction(), value));
        return this;
    }

    public JsonMessage runCommand(String command) {
        return click(ClickType.RUN_COMMAND, command);
    }

    public JsonMessage suggestCommand(String command) {
        return click(ClickType.SUGGEST_COMMAND, command);
    }

    public JsonMessage openUrl(String url) {
        return click(ClickType.OPEN_URL, url);
    }

    public JsonMessage copy(String text) {
        return click(ClickType.COPY_TO_CLIPBOARD, text);
    }

    public JsonMessage hoverText(String text) {
        root.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(color(text)).create()
        ));
        return this;
    }

    public JsonMessage showItem(HoverItem item) {
        root.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_ITEM,
                new ComponentBuilder(item.toJson()).create()
        ));
        return this;
    }

    public JsonMessage showItem(ItemStack itemStack) {
        return showItem(HoverItem.of(itemStack));
    }

    public JsonMessage space() {
        return append(" ");
    }

    public JsonMessage newLine() {
        return append("\n");
    }

    public BaseComponent[] build() {
        return new BaseComponent[]{root};
    }

    public String toLegacyText() {
        return root.toLegacyText();
    }

    public void send(Player player) {
        player.spigot().sendMessage(build());
    }

    public void send(CommandSender sender) {
        if (sender instanceof Player player) {
            send(player);
            return;
        }

        sender.sendMessage(toLegacyText());
    }

    public void broadcast() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(player);
        }
    }

    private static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}