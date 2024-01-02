package org.ethelred.minecraft.events;

import io.avaje.jsonb.Json;

import java.util.List;

@Json
public record EventUpdate(
        String world,
        List<PlayerUpdate> players
) {
}
