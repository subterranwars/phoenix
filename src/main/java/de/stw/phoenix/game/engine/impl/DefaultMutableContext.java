package de.stw.phoenix.game.engine.impl;

import de.stw.phoenix.game.engine.api.Context;
import de.stw.phoenix.game.engine.api.GameElement;
import de.stw.phoenix.game.engine.api.MutableContext;
import de.stw.phoenix.game.time.Tick;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultMutableContext implements MutableContext {

    private final List<GameElement> gameElements = new ArrayList<>();
    private final Tick currentTick;

    public DefaultMutableContext(Tick currentTick) {
        this.currentTick = Objects.requireNonNull(currentTick);
    }

    @Override
    public <T extends GameElement> List<T> findElements(final Class<T> elementClass) {
        return asImmutable().findElements(elementClass);
    }

    @Override
    public Tick getCurrentTick() {
        return currentTick;
    }

    @Override
    public void add(GameElement gameElement) {
        if (!gameElements.contains(gameElement)) {
            gameElements.add(gameElement);
        }
    }

    @Override
    public void addAll(List<GameElement> gameElements) {
        Objects.requireNonNull(gameElements);
        gameElements.forEach(e -> add(e));
    }

    @Override
    public Context asImmutable() {
        return new DefaultContext(gameElements, currentTick);
    }
}
