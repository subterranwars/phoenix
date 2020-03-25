package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.player.impl.Player;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface PlayerService {
    List<Player> getPlayers();

    Optional<Player> find(long playerId);
    Optional<Player> find(String playerName);
    Player get(long playerId);
    Player get(String name);

    void save(Player player);

    // Helper method to modify a detached (player) object in a save way (no LazyInitializationException)
    void modify(Player player, Consumer<Player> consumer);
}
