package de.stw.phoenix.user.api.password;

/**
 * Interface to allow handling of unhashed (unsecure) passwords but ensure they
 * are actually hashed (secure) before persisting.
 */
public interface Password {
    boolean isEncoded();
    String getValue();
}
