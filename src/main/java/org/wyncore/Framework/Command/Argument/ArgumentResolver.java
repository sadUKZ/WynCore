package org.wyncore.Framework.Command.Argument;

import org.bukkit.command.CommandSender;
import java.util.List;

public interface ArgumentResolver<T> {

    T resolve(CommandSender sender, String input);

    List<String> tabComplete(CommandSender sender, String input);

    Class<T> type();
}