package org.wyncore.Framework.Command.Argument.Resolvers;

import org.bukkit.command.CommandSender;
import org.wyncore.Framework.Command.Argument.ArgumentResolver;

import java.util.Collections;
import java.util.List;

public class IntegerResolver implements ArgumentResolver<Integer> {

    public Integer resolve(CommandSender sender, String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> tabComplete(CommandSender sender, String input) {
        return Collections.emptyList();
    }

    public Class<Integer> type() {
        return Integer.class;
    }
}