package de.stw.core.buildings;

import de.stw.core.resources.Resources;
import org.junit.jupiter.api.Test;

import static de.stw.core.buildings.Buildings.Headquarter;
import static de.stw.core.buildings.Buildings.Powerplant;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

public class ConstructionInfoTest {

    @Test
    public void verifyCreationCosts() {
        assertThat(ConstructionInfo.calculateCosts(Headquarter, 1),
                is(Headquarter.getCosts()));
        assertThat(ConstructionInfo.calculateCosts(Headquarter, 2),
                is(new ConstructionInfo(new BuildingLevel(Headquarter, 2)).getCosts()));
    }

    @Test
    public void verifyCosts() {
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Iron, 5000));
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Stone, 6000));

        assertThat(new ConstructionInfo(Headquarter, 1).getCosts(), is(Headquarter.getCosts()));

        assertThat(new ConstructionInfo(Headquarter, 2).getCosts(), hasEntry(Resources.Iron, 10000));
        assertThat(new ConstructionInfo(Headquarter, 2).getCosts(), hasEntry(Resources.Stone, 12000));

        assertThat(new ConstructionInfo(Headquarter, 3).getCosts(), hasEntry(Resources.Iron, 13320));
        assertThat(new ConstructionInfo(Headquarter, 3).getCosts(), hasEntry(Resources.Stone, 15990));
    }

    @Test
    public void verifyBuildTime() {
        assertThat(new ConstructionInfo(Powerplant, 1, 1).getBuildTimeInSeconds(), is(60L));
        assertThat(new ConstructionInfo(Powerplant, 2, 1).getBuildTimeInSeconds(), is(190L));
        assertThat(new ConstructionInfo(Powerplant, 3, 1).getBuildTimeInSeconds(), is(620L));

        assertThat(new ConstructionInfo(Powerplant, 1, 2).getBuildTimeInSeconds(), is(60L));
        assertThat(new ConstructionInfo(Powerplant, 2, 2).getBuildTimeInSeconds(), is(100L));
        assertThat(new ConstructionInfo(Powerplant, 3, 2).getBuildTimeInSeconds(), is(190L));

        assertThat(new ConstructionInfo(Powerplant, 1, 3).getBuildTimeInSeconds(), is(60L));
        assertThat(new ConstructionInfo(Powerplant, 2, 3).getBuildTimeInSeconds(), is(60L));
        assertThat(new ConstructionInfo(Powerplant, 3, 3).getBuildTimeInSeconds(), is(100L));
        assertThat(new ConstructionInfo(Powerplant, 4, 3).getBuildTimeInSeconds(), is(190L));
        assertThat(new ConstructionInfo(Powerplant, 5, 3).getBuildTimeInSeconds(), is(190L));
        assertThat(new ConstructionInfo(Powerplant, 6, 3).getBuildTimeInSeconds(), is(340L));
    }

}