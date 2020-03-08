package de.stw.phoenix.auth.api;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TokenTest {

    @Test
    public void verifyExpiration() {
        final Instant now = Instant.now();
        final Token token = new Token("token", now, Duration.ofHours(1));

        // Verify that the token is at least one hour valid
        for (int i=0; i<=60; i++) {
            assertThat(token.isValid(now.plus(Duration.ofMinutes(i))), is(true));
        }
        // Now it should be invalid
        assertThat(token.isValid(now.plus(Duration.ofMinutes(60).plusMillis(1))), is(false));
    }

}