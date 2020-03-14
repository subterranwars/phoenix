package de.stw.phoenix.game.player.impl;

import de.stw.phoenix.game.engine.energy.ConstructionTimeModifier;
import de.stw.phoenix.game.engine.energy.Modifiers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

class ImmutablePlayerImplTest {

    @Test
    public void verifyFindModifier() {
        ImmutablePlayerImpl player = ImmutablePlayerImpl.builder(1, "test")
                .withDefaults()
                .withModifier(Modifiers.CRITICAL_ENERGY_LEVEL)
                .build();
        assertThat(player.findModifier(ConstructionTimeModifier.class), hasSize(1));
        assertThat(player.findModifier(ConstructionTimeModifier.class), hasItem(Modifiers.CRITICAL_ENERGY_LEVEL));
    }

}