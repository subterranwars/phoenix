package de.stw.core.resources;

import de.stw.core.clock.Tick;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResourceProductionTest {

    @Test
    public void verifyProduction() {
        final ResourceProduction production = new ResourceProduction(new ResourceStorage(Resources.IRON, 0, Resources.MAX_STORAGE_CAPACITY), 3600);
        production.update(new Tick(0, TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)));
        assertThat(production.getStorage().getAmount(), is(3600d));
    }
}