package org.ethelred.minecraft.events.model;

import io.avaje.jsonb.Json;

@Json
public record Vector3(
        double x,
        double y,
        double z
) {
}
