package de.stw.phoenix.game.engine.research.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long> {
}
