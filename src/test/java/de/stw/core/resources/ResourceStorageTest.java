package de.stw.core.resources;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourceStorageTest {

    @Test
    public void verifyStore() {
        final ResourceStorage storage = new ResourceStorage(Resources.Iron, 0, 1000);
        storage.store(100);
        assertThat(storage.getAmount(), is(100d));
    }

    @Test
    public void verifyRetrieve() {
        final ResourceStorage storage = new ResourceStorage(Resources.Iron, 500, 1000);
        storage.retrieve(100);
        assertThat(storage.getAmount(), is(400d));
    }

    @Test
    public void verifyMaxStorage() {
        final ResourceStorage storage = new ResourceStorage(Resources.Iron, 500, 1000);
    }

    @Test
    public void verifyConstructorNotOverflowing() {
        final ResourceStorage storage = new ResourceStorage(Resources.Iron, 5000, 1000);
        assertThat(storage.getAmount(), is(1000d));
    }

    @Test
    public void verifyAmountCanNeverBeNegative() {
        final ResourceStorage storage = new ResourceStorage(Resources.Iron, 1000, 1000);
        storage.retrieve(2000);
        assertThat(storage.getAmount(), is(0d));
    }

    @Test
    public void verifyConstructorAmountNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ResourceStorage(Resources.Iron, -500, 1000));
    }

    @Test
    public void verifyConstructorMaxStorageNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ResourceStorage(Resources.Iron, 0, -1000));
    }

}