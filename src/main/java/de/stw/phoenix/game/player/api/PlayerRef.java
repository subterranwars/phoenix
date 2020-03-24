package de.stw.phoenix.game.player.api;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="players")
public class PlayerRef {

    @Id
    private long id;

    public PlayerRef() {

    }

    public PlayerRef(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
