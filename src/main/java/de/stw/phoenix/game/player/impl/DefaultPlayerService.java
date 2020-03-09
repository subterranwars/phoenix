package de.stw.phoenix.game.player.impl;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.player.api.ImmutablePlayer;
import de.stw.phoenix.game.player.api.MutablePlayer;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class DefaultPlayerService implements PlayerService {

    private List<MutablePlayer> players = Lists.newCopyOnWriteArrayList();

    @Override
    public List<ImmutablePlayer> getPlayers() {
        final List<ImmutablePlayer> immutablePlayers = players.stream().map(MutablePlayer::asImmutable).collect(Collectors.toList());
        return Collections.unmodifiableList(immutablePlayers);
    }

    @Override
    public Optional<ImmutablePlayer> find(long playerId) {
        return findInternal(playerId).map(MutablePlayer::asImmutable);
    }

    @Override
    public ImmutablePlayer get(long playerId) {
        return find(playerId).orElseThrow(() -> new NoSuchElementException("Player with id '" + playerId + "' not found"));
    }

    @Override
    public ImmutablePlayer get(String name) {
        return find(name).orElseThrow(() -> new NoSuchElementException("Player with name '" + name + "' not found"));
    }

    @Override
    public Optional<ImmutablePlayer> find(String playerName) {
        return findInternal(playerName).map(MutablePlayer::asImmutable);
    }

    @Override
    public void save(ImmutablePlayer player) {
        Objects.requireNonNull(player);
        final MutablePlayerImpl mutablePlayer = new MutablePlayerImpl(player);
        this.players.add(mutablePlayer);
    }

    @Override
    public void modify(String playerName, Consumer<MutablePlayer> consumer) {
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(playerName);
        final MutablePlayer mutablePlayer = findInternal(playerName).get();
        synchronized (mutablePlayer) {
            consumer.accept(mutablePlayer);
        }
    }

    private Optional<MutablePlayer> findInternal(long playerId) {
        return players.stream().filter(p -> p.getId() == playerId).findAny();
    }

    private Optional<MutablePlayer> findInternal(String playerName) {
        return players.stream().filter(p -> Objects.equals(p.getName(), playerName)).findAny();
    }
}
