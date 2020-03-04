package de.stw.core.buildings;

import de.stw.core.resources.Resources;
import org.junit.jupiter.api.Test;

import static de.stw.core.buildings.Buildings.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

public class BuildingTest {

    @Test
    public void verifyCosts() {
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Iron, 5000));
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Stone, 6000));
        assertThat(Headquarter.calculateCosts(1), is(Headquarter.getCosts()));

        assertThat(Headquarter.calculateCosts(2), hasEntry(Resources.Iron, 10000));
        assertThat(Headquarter.calculateCosts(2), hasEntry(Resources.Stone, 12000));

        assertThat(Headquarter.calculateCosts(3), hasEntry(Resources.Iron, 13320));
        assertThat(Headquarter.calculateCosts(3), hasEntry(Resources.Stone, 15990));
    }

}