package de.stw.phoenix.user.api.password;

import java.util.Objects;

public class HashedPassword implements Password {

    private final String hash;

    public HashedPassword(String hash) {
        this.hash = Objects.requireNonNull(hash);
    }

    @Override
    public boolean isEncoded() {
        return true;
    }

    @Override
    public String getValue() {
        return hash;
    }
}
