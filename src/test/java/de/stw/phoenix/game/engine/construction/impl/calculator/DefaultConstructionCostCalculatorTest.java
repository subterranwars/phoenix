package de.stw.phoenix.game.engine.construction.impl.calculator;

import com.google.common.collect.Lists;
import de.stw.phoenix.game.engine.construction.api.calculator.ConstructionCostCalculator;
import de.stw.phoenix.game.engine.impl.DefaultContext;
import de.stw.phoenix.game.engine.resources.api.Resources;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.impl.ImmutablePlayerImpl;
import de.stw.phoenix.game.time.Tick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.stw.phoenix.game.engine.buildings.Buildings.Headquarter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

class DefaultConstructionCostCalculatorTest {

    private ConstructionCostCalculator costCalculator;
    private DefaultContext context;
    private ImmutablePlayerImpl player;

    @BeforeEach
    public void beforeEach() {
        costCalculator = new DefaultConstructionCostCalculator();
        context = new DefaultContext(Lists.newArrayList(), new Tick(0, 5000));
        player = ImmutablePlayerImpl.builder(1, "test").withDefaults().build();
    }

    @Test
    public void verifyCosts() {
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Iron, 5000));
        assertThat(Headquarter.getCosts(), hasEntry(Resources.Stone, 6000));

        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 1), context, player), is(Headquarter.getCosts()));

        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 2), context, player), hasEntry(Resources.Iron, 10000));
        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 2), context, player), hasEntry(Resources.Stone, 12000));

        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 3), context, player), hasEntry(Resources.Iron, 13320));
        assertThat(costCalculator.calculateConstructionCosts(new BuildingLevel(Headquarter, 3), context, player), hasEntry(Resources.Stone, 15990));
    }

}