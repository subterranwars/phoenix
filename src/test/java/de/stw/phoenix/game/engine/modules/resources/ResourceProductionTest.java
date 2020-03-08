package de.stw.phoenix.game.engine.modules.resources;

import de.stw.phoenix.game.data.resources.Resources;
import de.stw.phoenix.game.player.api.ImmutableResourceStorage;
import de.stw.phoenix.game.player.impl.DefaultPlayerRef;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ResourceProductionTest {

    @Test
    public void verifyConvert() {
        final ResourceProduction resourceProduction = new ResourceProduction(new DefaultPlayerRef(0, "Dummy"), new ImmutableResourceStorage(Resources.Iron, 0, Resources.MAX_STORAGE_CAPACITY), 60);
        final ResourceProduction converted = resourceProduction.convert(TimeUnit.MINUTES);
        assertThat(converted.getProductionValue(), is(1d));
    }
}