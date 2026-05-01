package org.wyncore.Framework.Command.Argument;

import java.util.*;

public class ArgumentRegistry {

    private final Map<Class<?>, ArgumentResolver<?>> map = new HashMap<>();

    public <T> void register(ArgumentResolver<T> resolver) {
        map.put(resolver.type(), resolver);
    }

    @SuppressWarnings("unchecked")
    public <T> ArgumentResolver<T> get(Class<T> clazz) {
        return (ArgumentResolver<T>) map.get(clazz);
    }
}