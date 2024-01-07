package org.ethelred.minecraft.events.di;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.jstach.jstachio.JStachio;

@Factory
public class JStachioFactory {
    @Bean
    public JStachio getJStachio() {
        return JStachio.defaults();
    }
}
