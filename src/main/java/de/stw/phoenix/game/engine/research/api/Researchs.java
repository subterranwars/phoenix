package de.stw.phoenix.game.engine.research.api;

import com.google.common.collect.ImmutableList;
import de.stw.phoenix.game.time.TimeDuration;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public interface Researchs {

    Research MilitaryService = Research.builder()
            .id(1)
            .label("Militärdienst")
            .description("Der Militärdienst ist eine feine Sache")
            .researchTime(TimeDuration.ofSeconds(10))
            .build();

    Research Construction = Research.builder()
            .id(2)
            .label("Gebäudebau")
            .description("Der Gebäudebau wird benötigt um Gebäude zu bauen.")
            .researchTime(TimeDuration.ofSeconds(10))
            .build();

    Research Farming = Research.builder()
            .id(3)
            .label("Gartenbau")
            .description("Der Gartenbau ist eigentlich Landwirtschaft, aber man muss ja alles selber machen hier")
            .researchTime(TimeDuration.ofSeconds(20))
            .build();

    Research Guild = Research.builder()
            .id(4)
            .label("Gilde")
            .description("Die Gilde ist auch ein Computerspiel")
            .researchTime(TimeDuration.ofSeconds(30))
            .build();

    List<Research> ALL = ImmutableList.of(MilitaryService, Construction, Farming, Guild);

    static Research findByRef(ResearchRef researchRef) {
        Objects.requireNonNull(researchRef);
        return findById(researchRef.getId());
    }

    static Research findById(int researchId) {
        return ALL.stream()
                .filter(r -> r.getId() == researchId)
                .findAny().orElseThrow(() -> new NoSuchElementException("No Research found with id '" + researchId + "'"));
    }
}
