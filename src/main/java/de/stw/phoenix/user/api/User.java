package de.stw.phoenix.user.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name="users")
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    private User() {}

    private User(Builder builder) {
        Objects.requireNonNull(builder);
        this.username = Objects.requireNonNull(builder.username);
        this.email = Objects.requireNonNull(builder.email);
        this.password = Objects.requireNonNull(builder.password);
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
        private String username;
        private String email;
        private String password;

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
