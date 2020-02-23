package de.stw.core.resources;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResourceStorageTest {

    @Test
    public void verifyStore() {
        final ResourceStorage storage = new ResourceStorage(Resources.IRON, 0, 1000);
        storage.store(100);
        assertThat(storage.getAmount(), is(100d));
    }

    @Test
    public void verifyRetrieve() {
        final ResourceStorage storage = new ResourceStorage(Resources.IRON, 500, 1000);
        storage.retrieve(100);
        assertThat(storage.getAmount(), is(400d));
    }

    @Test
    public void verifyMaxStorage() {
        final ResourceStorage storage = new ResourceStorage(Resources.IRON, 500, 1000);
    }

    @Test
    public void verifyConstructorNotOverflowing() {
        final ResourceStorage storage = new ResourceStorage(Resources.IRON, 5000, 1000);
        assertThat(storage.getAmount(), is(1000d));
    }

    @Test
    public void verifyAmountCanNeverBeNegative() {
        final ResourceStorage storage = new ResourceStorage(Resources.IRON, 1000, 1000);
        storage.retrieve(2000);
        assertThat(storage.getAmount(), is(0d));
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyConstructorAmountNotNegative() {
        new ResourceStorage(Resources.IRON, -500, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyConstructorMaxStorageNotNegative() {
        new ResourceStorage(Resources.IRON, 0, -1000);
    }

}