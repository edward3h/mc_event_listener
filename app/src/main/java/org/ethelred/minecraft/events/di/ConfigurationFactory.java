package org.ethelred.minecraft.events.di;

import io.avaje.config.Config;
import io.avaje.config.Configuration;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

@Factory
public class ConfigurationFactory {
    @Bean
    public Configuration getConfiguration() {
        return Config.asConfiguration();
    }
}
