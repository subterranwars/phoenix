package de.stw.rest;

import de.stw.core.Player;
import de.stw.core.clock.Tick;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameState {
    private final List<PlayerState> playerStates;
    private final Tick tick = new Tick(0, 1000);

    public GameState(final List<Player> playerList) {
        Objects.requireNonNull(playerList);
        playerStates = playerList.stream().map(PlayerState::new).collect(Collectors.toList());
    }

    public Tick getTick() {
        return tick;
    }

    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }
}
