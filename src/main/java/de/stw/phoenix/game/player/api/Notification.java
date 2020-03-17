package de.stw.phoenix.game.player.api;

import java.time.Instant;
import java.util.Objects;

public class Notification {
    private final Instant completionDate;
    private final String label;
    private final String content;
    private final boolean read = false;

    public Notification(Instant completionDate, String label, String content) {
	Objects.requireNonNull(completionDate);
	Objects.requireNonNull(label);
	Objects.requireNonNull(content);

	this.completionDate = completionDate;
	this.content = content;
	this.label = label;
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