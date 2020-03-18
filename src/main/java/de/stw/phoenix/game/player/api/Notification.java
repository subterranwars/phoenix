package de.stw.phoenix.game.player.api;

import java.time.Instant;
import java.util.Objects;

public class Notification {
    private final long id;
    private final Instant completionDate;
    private final String label;
    private final String content;
    private final boolean read;

    public Notification(long id, Instant completionDate, String label, String content) {
	    this(id, completionDate, label, content, false);
    }

    public Notification(long id, Instant completionDate, String label, String content, boolean read) {
	Objects.requireNonNull(completionDate);
	Objects.requireNonNull(label);
	Objects.requireNonNull(content);

	this.id = id;
	this.completionDate = completionDate;
	this.content = content;
	this.label = label;
	this.read = read;
    }

    public long getId() {
	return id;
    }

    public Instant getCompletionDate() {
	return completionDate;
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
}