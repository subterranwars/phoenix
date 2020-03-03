package de.stw.rest;

import com.google.common.collect.Lists;
import de.stw.core.Player;
import de.stw.core.resources.ResourceProduction;
import de.stw.core.resources.ResourceStorage;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PlayerState {
    private int id;
    private String name;
    private List<ResourceProduction> resourceProductions;

    public PlayerState(Player player) {
        Objects.requireNonNull(player);
        this.resourceProductions = player.getResources().stream()
                .map(storage -> new ResourceProduction(storage, 60).convert(TimeUnit.MINUTES))
                .collect(Collectors.toList());
        this.id = player.getId();
        this.name = player.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ResourceProduction> getResourceProductions() {
        return resourceProductions;
    }
}
