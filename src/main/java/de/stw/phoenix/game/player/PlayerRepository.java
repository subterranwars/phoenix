package de.stw.phoenix.game.player;

import de.stw.phoenix.game.player.impl.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select p from Player p where p.name = ?1")
    Player readForUpdate(String playerName);

    @Query("Select p from Player p")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Player> readAllForUpdate();
}
