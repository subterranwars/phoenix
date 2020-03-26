package de.stw.phoenix.game.player.impl;

import com.google.common.collect.ImmutableList;
import de.stw.phoenix.game.player.PlayerRepository;
import de.stw.phoenix.game.player.api.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
public class DefaultPlayerService implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.copyOf(playerRepository.readAllForUpdate());
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void modify(Player detachedPlayer, Consumer<Player> consumer) {
        Objects.requireNonNull(detachedPlayer);
        Objects.requireNonNull(consumer);
        final Player attachedPlayer = playerRepository.readForUpdate(detachedPlayer.getId());
        consumer.accept(attachedPlayer);
        playerRepository.save(attachedPlayer);
    }
}
