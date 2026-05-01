package org.wyncore.Framework.Command.Argument.Resolvers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.wyncore.Framework.Command.Argument.ArgumentResolver;

import java.util.List;

public class PlayerResolver implements ArgumentResolver<Player> {

    public Player resolve(CommandSender sender, String input) {
        return Bukkit.getPlayerExact(input);
    }

    public List<String> tabComplete(CommandSender sender, String input) {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .filter(n -> n.toLowerCase().startsWith(input.toLowerCase()))
                .toList();
    }

    public Class<Player> type() {
        return Player.class;
    }
}