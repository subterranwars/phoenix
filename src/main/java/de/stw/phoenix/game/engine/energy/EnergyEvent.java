package de.stw.phoenix.game.engine.energy;

import de.stw.phoenix.game.player.api.PlayerRef;

import java.util.Objects;

public class EnergyEvent {
    private final PlayerRef player;
    private final EnergyLevel energyLevel;

    public EnergyEvent(PlayerRef player, EnergyLevel energyLevel) {
        this.energyLevel = Objects.requireNonNull(energyLevel);
        this.player = Objects.requireNonNull(player);
    }

    public PlayerRef getPlayer() {
        return player;
    }

    public EnergyLevel getEnergyLevel() {
        return energyLevel;
    }
}
