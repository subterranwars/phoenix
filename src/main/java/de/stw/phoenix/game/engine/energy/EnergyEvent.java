package de.stw.phoenix.game.engine.energy;

import de.stw.phoenix.game.player.impl.Player;

import java.util.Objects;

public class EnergyEvent {
    private final Player player;
    private final EnergyLevel energyLevel;

    public EnergyEvent(Player player, EnergyLevel energyLevel) {
        this.energyLevel = Objects.requireNonNull(energyLevel);
        this.player = Objects.requireNonNull(player);
    }

    public Player getPlayer() {
        return player;
    }

    public EnergyLevel getEnergyLevel() {
        return energyLevel;
    }
}
