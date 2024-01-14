package org.ethelred.minecraft.events;

import io.avaje.inject.BeanScope;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            // Avaje DI entrypoint
            var applicationScope = BeanScope.builder().build();
            var javalin = applicationScope.get(Javalin.class);
            javalin.start();
        } catch (Exception e) {
            LOGGER.error("Oh no, an error.", e);
        }

    }
}
