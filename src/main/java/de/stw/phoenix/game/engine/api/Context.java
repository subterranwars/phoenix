package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.time.Tick;

import java.util.List;

public interface Context {
    <T extends GameElement> List<T> findElements(final Class<T> elementClass);
    Tick getCurrentTick();
}
