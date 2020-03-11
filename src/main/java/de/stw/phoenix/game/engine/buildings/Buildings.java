package de.stw.phoenix.game.engine.buildings;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public interface Buildings {
    Building Headquarter = Building.builder(1, "hq")
            .label("Hauptquartier")
            .description("Dieses Gebäude ist das Herz jeder Kolonie. Von hier aus werden alle Bauvorhaben der Kolonie koordiniert sowie die Kolonieausbaustufe festgelegt. Pro Gebäudelevel erhältst du 25 Eisen, Stein & Nahrung, und es verringern sich die Bauzeiten deiner anderen Gebäude. Im weiteren Spielverlauf kannst du hier deine Kolonien zur nächsten Ausbaustufe aufsteigen lassen. Tipp: Vor allem am Anfang ist es sehrwichtig, das Hauptquartier intensiv auszubauen, um keine unmenschlichen Bauzeiten bei den anderen Gebäuden zu riskieren.")
            .energyConsumption(0)
            .buildTime(30, TimeUnit.SECONDS)
            .costsIron(5000)
            .costsStone(6000)
            .build();

    Building Resourcefacility = Building.builder(7, "resource-building")
            .label("Rohstoffgebäude")
            .description("Alle direkt förderbaren Rohstoffe können hier abgebaut werden, vorausgesetzt du hast die Rohstoffe überhaupt entdeckt. Dazu suchst du am besten zuerst nach ein paar Vorkommen. Sind diese gefunden kannst du Drohnen beauftragen dort Rohstoffe abzubauen. Besonders wichtig sind Eisen, Stein und Öl. Mit jedem Level dieses Gebäudes erhältst du 5 Drohnen zusätzlich. Tipp: Eine ausreichende Grundversorgung an Rohstoffen ist von herausragender Bedeutung. Wann immer es möglich ist, solltest du hier investieren.")
            .energyConsumption(30)
            .buildTime(20, TimeUnit.SECONDS)
            .costsIron(5000)
            .costsStone(5000)
            .build();

    Building Resourcedepot = Building.builder(15, "resource-depot")
            .label("Rohstofflager")
            .description("Standardmäßig hat deine Kolonie 20k Lagerplatz für alle Nicht-Radioaktiven Rohstoffe. Pro Level dieses Gebäude erhältst du für diese Rohstoffe 15k dazu. Tipp: Für Radioaktive Rohstoffe benötigst du das Sicherheitslager.")
            .energyConsumption(25)
            .buildTime(10, TimeUnit.SECONDS)
            .costsIron(5000)
            .costsStone(5000)
            .build();

    Building Powerplant = Building.builder(17, "power-plant")
            .label("Kraftwerk")
            .description("Die! Energieversorgung für deine Kolonie. Dieses Gebäude liefert maximal 100 GW pro Ausbaustufe, verbrennt allerdings dazu wertvolles Öl. Tipp: Achte darauf, dass du stets genug Leistung für deine Bauten zur Verfügung stellen kannst, und dass immer genügend Öl in Reserve ist. Bei kritischem Energieniveau dauert fast alles ein Vielfaches der eigentlichen Zeit.")
            .buildTime(10, TimeUnit.SECONDS)
            .costsIron(2500)
            .costsStone(2000)
            .build();

    List<Building> ALL = Lists.newArrayList(Headquarter, Resourcefacility, Resourcedepot, Powerplant);

    static Building findByRef(BuildingRef building) {
        Objects.requireNonNull(building);
        return findById(building.getId());
    }

    static Building findById(int buildingId) {
        return ALL.stream()
                .filter(b -> b.getId() == buildingId)
                .findAny().orElseThrow(() -> new NoSuchElementException("No building found with id '" + buildingId + "'"));
    }
}
