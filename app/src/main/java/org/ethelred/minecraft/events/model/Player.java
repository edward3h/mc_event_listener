package org.ethelred.minecraft.events.model;

import org.jetbrains.annotations.NotNull;

public record Player(String name, Location lastLocation) implements Comparable<Player> {
    @Override
    public int compareTo(@NotNull Player o) {
        return name.compareTo(o.name);
    }
}
