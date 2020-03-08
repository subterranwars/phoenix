package de.stw.phoenix.auth.api;

public interface PasswordEncoder {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String storedHash);
}
