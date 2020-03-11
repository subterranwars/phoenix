package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.engine.resources.api.ProductionValue;
import de.stw.phoenix.game.engine.resources.api.Resource;

public interface ResourceProduction extends PlayerUpdate {

    default int getPhase() {
        return Phases.ResourceProduction;
    }

    Resource getResource();
    ProductionValue getProductionValue();

}
