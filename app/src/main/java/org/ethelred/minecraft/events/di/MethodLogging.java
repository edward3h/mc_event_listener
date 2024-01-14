package org.ethelred.minecraft.events.di;

import io.avaje.inject.aop.Aspect;
import org.slf4j.event.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Aspect(ordering = 1)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLogging {
    Level value() default Level.DEBUG;
}
