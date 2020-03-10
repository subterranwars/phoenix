package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;

public interface EventVisitor<T> {
    T visit(ConstructionEvent constructionEvent);
    T visit(ResourceSearchEvent resourceSearchEvent);
}
