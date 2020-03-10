package de.stw.phoenix.game.engine.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PlayerUpdate {

    @JsonIgnore
    GameBehaviour getBehaviour();
}
