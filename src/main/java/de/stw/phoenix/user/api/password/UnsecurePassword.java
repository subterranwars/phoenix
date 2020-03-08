package de.stw.phoenix.user.api.password;

import java.util.Objects;

public class UnsecurePassword implements Password {

    private final String password;

    public UnsecurePassword(String password) {
        this.password = Objects.requireNonNull(password);
    }

    @Override
    public boolean isEncoded() {
        return false;
    }

    @Override
    public String getValue() {
        return password;
    }
}
