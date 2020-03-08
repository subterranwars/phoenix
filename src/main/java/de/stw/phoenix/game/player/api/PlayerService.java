package de.stw.phoenix.game.player.api;

import java.util.List;
import java.util.Optional;

public interface PlayerService extends MutablePlayerAccessor {
    List<ImmutablePlayer> getPlayers();

    Optional<ImmutablePlayer> find(long playerId);
    ImmutablePlayer get(long playerId);

    Optional<ImmutablePlayer> find(String playerName);

    void save(ImmutablePlayer player);
}
