package de.stw.phoenix.game.time;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClockRepository extends JpaRepository<ArtificialClock, String> {
}
