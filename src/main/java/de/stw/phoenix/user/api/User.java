package de.stw.phoenix.user.api;

import com.google.common.base.Preconditions;

import java.util.Objects;

public class User {
    private long id;
    private String username;
    private String email;
    private String password;

    private User(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private long id;
        private String username;
        private String email;
        private String password;


        public Builder id(long id) {
            Preconditions.checkArgument(id > 0);
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            Objects.requireNonNull(username);
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            Objects.requireNonNull(email);
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            Objects.requireNonNull(password);
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

}
