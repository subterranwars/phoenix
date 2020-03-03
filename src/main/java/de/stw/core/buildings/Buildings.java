package de.stw.core.buildings;

import com.google.common.collect.Lists;

import java.util.List;

public interface Buildings {
    Building Headquarter = Building.builder(1, "hq")
            .costsIron(5000)
            .costsStone(6000)
            .build();

    Building Resourcefacility = Building.builder(2, "resource-building")
            .costsIron(5000)
            .costsStone(5000)
            .build();

    Building Powerplant = Building.builder(3, "power-plant")
            .costsIron(2500)
            .costsStone(2000)
            .build();

    List<Building> ALL = Lists.newArrayList(Headquarter, Resourcefacility, Powerplant);
}
