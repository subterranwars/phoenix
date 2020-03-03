package de.stw.core.resources;

import com.google.common.collect.Lists;

import java.util.List;

public interface Buildings {
    Building Headquarter = new Building(1, "hq");
    Building Resourcefacility = new Building(2, "resource-building");
    Building Powerplant = new Building(3, "power-plant");

    List<Building> ALL = Lists.newArrayList(Headquarter, Resourcefacility, Powerplant);
}
