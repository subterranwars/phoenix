package de.stw.phoenix.game.engine.api;

import java.util.List;

public interface MutableContext extends Context {
    void add(GameElement gameElement);
    void addAll(List<GameElement> gameElements);

    Context asImmutable();
}
