package de.stw.phoenix.game.player.api;

import de.stw.phoenix.game.data.resources.Resources;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImmutableResourceStorageTest {

    @Test
    public void verifyMaxStorage() {
        final ImmutableResourceStorage storage = new ImmutableResourceStorage(Resources.Iron, 500, 1000);
    }

    @Test
    public void verifyConstructorNotOverflowing() {
        final ImmutableResourceStorage storage = new ImmutableResourceStorage(Resources.Iron, 5000, 1000);
        assertThat(storage.getAmount(), is(1000d));
    }

    @Test
    public void verifyConstructorAmountNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ImmutableResourceStorage(Resources.Iron, -500, 1000));
    }

    @Test
    public void verifyConstructorMaxStorageNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ImmutableResourceStorage(Resources.Iron, 0, -1000));
    }
}