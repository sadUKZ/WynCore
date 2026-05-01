package org.wyncore.Framework.Command.Argument.Resolvers;

import org.bukkit.command.CommandSender;
import org.wyncore.Framework.Command.Argument.ArgumentResolver;

import java.util.Collections;
import java.util.List;

public class StringResolver implements ArgumentResolver<String> {

    public String resolve(CommandSender sender, String input) {
        return input;
    }

    public List<String> tabComplete(CommandSender sender, String input) {
        return Collections.emptyList();
    }

    public Class<String> type() {
        return String.class;
    }
}