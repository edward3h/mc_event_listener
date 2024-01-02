package org.ethelred.minecraft.events;

import io.avaje.inject.BeanScope;
import io.javalin.Javalin;
import io.javalin.plugin.Plugin;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Avaje DI entrypoint + Javalin
        Javalin javalin = Javalin.create();
        try (BeanScope beanScope = BeanScope.builder().build()) {
            List<Plugin> plugins = beanScope.list(Plugin.class);
            plugins.forEach(p -> p.apply(javalin));
            javalin.start();
        }
    }
}
