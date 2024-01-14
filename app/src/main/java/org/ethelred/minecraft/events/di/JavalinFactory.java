package org.ethelred.minecraft.events.di;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.javalin.Javalin;
import io.javalin.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Factory
public class JavalinFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavalinFactory.class);
    @Bean
    public Javalin getJavalin(List<Plugin> plugins) {
        var r = Javalin.create();
        plugins.forEach(p -> {
            LOGGER.info("Adding plugin {}", p);
            p.apply(r);
        });
        return r;
    }
}
