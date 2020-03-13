package de.stw.phoenix.game.engine.impl;

import de.stw.phoenix.game.engine.api.Context;
import de.stw.phoenix.game.engine.api.GameElement;
import de.stw.phoenix.game.time.Tick;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultContext implements Context {

    private final List<GameElement> elements;
    private Tick currentTick;

    public DefaultContext(List<GameElement> elements, Tick currentTick) {
        Objects.requireNonNull(elements);
        this.elements = Collections.unmodifiableList(elements);
        this.currentTick = Objects.requireNonNull(currentTick);
    }

    @Override
    public <T extends GameElement> List<T> findElements(Class<T> elementClass) {
        return elements.stream()
            .filter(e -> elementClass.isAssignableFrom(e.getClass()))
            .map(e -> (T) e)
            .collect(Collectors.toList());
    }

    @Override
    public Tick getCurrentTick() {
        return currentTick;
    }
}
