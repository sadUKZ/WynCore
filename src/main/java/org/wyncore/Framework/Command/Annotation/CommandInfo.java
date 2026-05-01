package org.wyncore.Framework.Command.Annotation;

import org.wyncore.Framework.Command.Core.CommandSenderType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {

    String name();

    String description() default "";

    String permission() default "";

    String[] aliases() default {};

    CommandSenderType sender() default CommandSenderType.BOTH;
}