package de.stw.phoenix.user.api;

import com.google.common.base.Preconditions;
import de.stw.phoenix.user.api.password.Password;
import de.stw.phoenix.user.api.password.UnsecurePassword;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

@Table(name="users")
@Entity
public class User {

    @Id
    private long id;
    private String username;
    private String email;

    @Transient
    private Password password;

    private User() {}

    private User(Builder builder) {
        Objects.requireNonNull(builder);
        this.id = builder.id;
        this.username = Objects.requireNonNull(builder.username);
        this.email = Objects.requireNonNull(builder.email);
        this.password = Objects.requireNonNull(builder.password);
    }

    public static Builder builder(final User user) {
        return builder()
                .id(user.id)
                .username(user.username)
                .email(user.email).password(user.password);
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

    public Password getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private long id;
        private String username;
        private String email;
        private Password password;


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
            return password(new UnsecurePassword(password));
        }

        public Builder password(Password password) {
            Objects.requireNonNull(password);
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

}
