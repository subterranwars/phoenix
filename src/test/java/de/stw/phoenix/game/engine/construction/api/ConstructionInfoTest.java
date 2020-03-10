package de.stw.phoenix.game.engine.construction.api;

import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static de.stw.phoenix.game.engine.buildings.Buildings.Headquarter;
import static de.stw.phoenix.game.engine.buildings.Buildings.Powerplant;
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
    @Disabled("Enable until build times are fixed/known") // TODO MVR enable me
    public void verifyBuildTime() {
        assertThat(new ConstructionInfo(Powerplant, 1, 1).getBuildTime(), is(Duration.ofSeconds(60)));
        assertThat(new ConstructionInfo(Powerplant, 2, 1).getBuildTime(), is(Duration.ofSeconds(190)));
        assertThat(new ConstructionInfo(Powerplant, 3, 1).getBuildTime(), is(Duration.ofSeconds(620)));

        assertThat(new ConstructionInfo(Powerplant, 1, 2).getBuildTime(), is(Duration.ofSeconds(60)));
        assertThat(new ConstructionInfo(Powerplant, 2, 2).getBuildTime(), is(Duration.ofSeconds(100L)));
        assertThat(new ConstructionInfo(Powerplant, 3, 2).getBuildTime(), is(Duration.ofSeconds(190L)));

        assertThat(new ConstructionInfo(Powerplant, 1, 3).getBuildTime(), is(Duration.ofSeconds(60L)));
        assertThat(new ConstructionInfo(Powerplant, 2, 3).getBuildTime(), is(Duration.ofSeconds(60L)));
        assertThat(new ConstructionInfo(Powerplant, 3, 3).getBuildTime(), is(Duration.ofSeconds(100L)));
        assertThat(new ConstructionInfo(Powerplant, 4, 3).getBuildTime(), is(Duration.ofSeconds(190L)));
        assertThat(new ConstructionInfo(Powerplant, 5, 3).getBuildTime(), is(Duration.ofSeconds(190L)));
        assertThat(new ConstructionInfo(Powerplant, 6, 3).getBuildTime(), is(Duration.ofSeconds(340L)));
    }

}