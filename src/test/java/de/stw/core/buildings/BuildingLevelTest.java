package de.stw.core.buildings;

import org.junit.jupiter.api.Test;

import static de.stw.core.buildings.Buildings.Headquarter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BuildingLevelTest {

    @Test
    public void verifyGetCosts() {
        assertThat(Headquarter.calculateCosts(1), is(new BuildingLevel(Headquarter, 0).getCosts()));
        assertThat(Headquarter.calculateCosts(2), is (new BuildingLevel(Headquarter, 1).getCosts()));
    }

}