package de.stw.phoenix.game.player.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name="player_notifications")
public class Notification {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="creationDate")
    private Instant creationDate;

    @Column(name="label")
    private String label;

    @Column(name="content", length = 20000)
    private String content;

    @Column(name="read")
    private boolean read;

    private Notification() {

    }

    // TODO MVR use builder pattern instead (everywhere :D)
    public Notification(Instant creationDate, String label, String content) {
	    this(creationDate, label, content, false);
    }

    public Notification(Instant creationDate, String label, String content, boolean read) {
        Objects.requireNonNull(creationDate);
        Objects.requireNonNull(label);
        Objects.requireNonNull(content);

        this.id = id;
        this.creationDate = creationDate;
        this.content = content;
        this.label = label;
        this.read = read;
    }

    public long getId() {
	return id;
    }

    public Instant getCreationDate() {
	return creationDate;
    }

    public String getLabel() {
	return label;
    }

    public String getContent() {
	return content;
    }

    public boolean isRead() {
	return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}