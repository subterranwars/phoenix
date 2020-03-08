package de.stw.phoenix.game.player.impl;

import de.stw.phoenix.game.player.api.PlayerRef;

import java.util.Objects;

public class DefaultPlayerRef implements PlayerRef {

    private final Long id;
    private final String name;

    public DefaultPlayerRef(final long id, final String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
