package de.stw.phoenix.game.player.impl;

import com.google.common.collect.ImmutableList;
import de.stw.phoenix.game.player.PlayerRepository;
import de.stw.phoenix.game.player.api.PlayerRef;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class DefaultPlayerService implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.copyOf(playerRepository.findAll());
    }

    @Override
    public Optional<Player> find(long playerId) {
        return playerRepository.findById(playerId);
    }

    @Override
    public Player get(long playerId) {
        return find(playerId).orElseThrow(() -> new NoSuchElementException("Player with id '" + playerId + "' not found"));
    }

    @Override
    public Player get(String name) {
        return find(name).orElseThrow(() -> new NoSuchElementException("Player with name '" + name + "' not found"));
    }

    @Override
    public Optional<Player> find(String playerName) {
        return playerRepository.findByName(playerName);
    }

    @Override
    public void save(Player player) {
        Objects.requireNonNull(player);
        playerRepository.save(player);
    }

    @Override
    @Transactional
    public void modify(String playerName, Consumer<Player> consumer) {
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(playerName);
        final Player player = playerRepository.readForUpdate(playerName);
        consumer.accept(player);
        playerRepository.save(player);
    }

    @Override
    @Transactional
    public void modify(Player player, Consumer<Player> consumer) {
        Objects.requireNonNull(player);
        modify(player.getName(), consumer);
    }

    @Override
    @Transactional
    public void modify(PlayerRef playerRef, Consumer<Player> consumer) {
        Objects.requireNonNull(playerRef);
        final Player player = get(playerRef.getId());
        modify(player, consumer);
    }
}
