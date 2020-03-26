package de.stw.phoenix.auth.impl;

import com.google.common.annotations.VisibleForTesting;
import de.stw.phoenix.auth.api.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BCryptPasswordEncoder implements PasswordEncoder {

    private final org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder delegate;

    public BCryptPasswordEncoder(@Value("${de.stw.auth.bcrypt.strength | 10}") int strength) {
        this.delegate = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(
                org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2Y, strength);
    }

    @VisibleForTesting
    public BCryptPasswordEncoder() {
        this(10);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String storedHash) {
        return delegate.matches(rawPassword, storedHash);
    }
}
