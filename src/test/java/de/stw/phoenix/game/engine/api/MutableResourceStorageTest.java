package de.stw.phoenix.game.engine.api;

import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.MutableResourceStorage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MutableResourceStorageTest {

    @Test
    public void verifyStore() {
        final MutableResourceStorage storage = new MutableResourceStorage(Resources.Iron, 0, 1000);
        storage.store(100);
        assertThat(storage.getAmount(), is(100d));
    }

    @Test
    public void verifyRetrieve() {
        final MutableResourceStorage storage = new MutableResourceStorage(Resources.Iron, 500, 1000);
        storage.retrieve(100);
        assertThat(storage.getAmount(), is(400d));
    }

    @Test
    public void verifyMaxStorage() {
        final MutableResourceStorage storage = new MutableResourceStorage(Resources.Iron, 500, 1000);
    }

    @Test
    public void verifyConstructorNotOverflowing() {
        final MutableResourceStorage storage = new MutableResourceStorage(Resources.Iron, 5000, 1000);
        assertThat(storage.getAmount(), is(1000d));
    }

    @Test
    public void verifyAmountCanNeverBeNegative() {
        final MutableResourceStorage storage = new MutableResourceStorage(Resources.Iron, 1000, 1000);
        storage.retrieve(2000);
        assertThat(storage.getAmount(), is(0d));
    }

    @Test
    public void verifyConstructorAmountNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new MutableResourceStorage(Resources.Iron, -500, 1000));
    }

    @Test
    public void verifyConstructorMaxStorageNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new MutableResourceStorage(Resources.Iron, 0, -1000));
    }
}