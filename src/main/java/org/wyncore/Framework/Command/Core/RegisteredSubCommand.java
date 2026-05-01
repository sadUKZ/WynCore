package org.wyncore.Framework.Command.Core;

import org.wyncore.Framework.Command.Annotation.SubCommandInfo;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class RegisteredSubCommand {

    private final Object instance;
    private final Method method;
    private final SubCommandInfo info;

    public RegisteredSubCommand(Object instance, Method method, SubCommandInfo info) {
        this.instance = instance;
        this.method = method;
        this.info = info;
    }

    public Object instance() {
        return instance;
    }

    public Method method() {
        return method;
    }

    public SubCommandInfo info() {
        return info;
    }

    public boolean matches(String input) {
        if (info.name().equalsIgnoreCase(input)) return true;

        return Arrays.stream(info.aliases())
                .anyMatch(alias -> alias.equalsIgnoreCase(input));
    }

    public List<String> aliases() {
        return Arrays.asList(info.aliases());
    }
}