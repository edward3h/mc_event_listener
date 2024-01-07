package org.ethelred.minecraft.events.model;

import io.avaje.jsonb.Json;

@Json
public record PlayerUpdate(
        String name,
        String id,
        Dimension dimension,
        Vector3 location
) {
}
