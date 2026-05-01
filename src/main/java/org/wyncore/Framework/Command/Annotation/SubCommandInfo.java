package org.wyncore.Framework.Command.Annotation;

import org.wyncore.Framework.Command.Core.CommandSenderType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommandInfo {

    String name();

    String description() default "";

    String permission() default "";

    String[] aliases() default {};

    String usage() default "";

    CommandSenderType sender() default CommandSenderType.BOTH;
}