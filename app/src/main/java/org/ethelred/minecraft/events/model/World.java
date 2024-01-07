package org.ethelred.minecraft.events.model;

import org.jetbrains.annotations.NotNull;

import java.util.SortedSet;

public record World(String name, SortedSet<Player> players) implements Comparable<World> {
    @Override
    public int compareTo(@NotNull World o) {
        return name.compareTo(o.name);
    }
}
