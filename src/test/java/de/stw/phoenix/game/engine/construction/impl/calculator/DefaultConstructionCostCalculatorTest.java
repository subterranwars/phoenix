package de.stw.phoenix.game.engine.construction.impl.calculator;

import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionCostCalculator;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.stw.phoenix.game.engine.buildings.Buildings.Headquarter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

class DefaultConstructionCostCalculatorTest {

    private ConstructionCostCalculator costCalculator;
    private Player player;

    @BeforeEach
    public void beforeEach() {
        costCalculator = new DefaultConstructionCostCalculator();
        player = Player.builder(1, "test").withDefaults().build();
    }

    @Test
    public void verifyCosts() {
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Iron, 5000d));
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Stone, 6000d));

        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 1), player), is(Headquarter.getCosts()));

        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 2), player), hasEntry(Resources.Iron, 10000d));
        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 2), player), hasEntry(Resources.Stone, 12000d));

        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 3), player), hasEntry(Resources.Iron, 13320d));
        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 3), player), hasEntry(Resources.Stone, 15990d));
    }

}