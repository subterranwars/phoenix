package de.stw.phoenix.game.player;

import de.stw.phoenix.game.player.impl.Player;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends Repository<Player, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Player> findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Player> findById(Long aLong);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select p from Player p where p.id = ?1")
    Player readForUpdate(long playerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select p from Player p")
    List<Player> readAllForUpdate();

    void save(Player player);
}
