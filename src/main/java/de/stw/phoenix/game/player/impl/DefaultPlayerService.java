package de.stw.phoenix.game.player.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultPlayerService implements PlayerService {

    private List<ImmutablePlayer> players = Lists.newCopyOnWriteArrayList();

    @Override
    public List<ImmutablePlayer> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public Optional<ImmutablePlayer> find(long playerId) {
        return players.stream().filter(p -> p.getId() == playerId).findAny();
    }

    @Override
    public Optional<ImmutablePlayer> find(String playerName) {
        return players.stream().filter(p -> Objects.equals(p.getName(), playerName)).findAny();
    }

    @Override
    public void save(ImmutablePlayer player) {
        Objects.requireNonNull(player);
        this.players.add(player);
    }

    @Override
    public void update(ImmutablePlayer player) {
        find(player.getId()).ifPresent(existingPlayer -> {
            players.remove(existingPlayer);
            players.add(player);
        });
    }
}
