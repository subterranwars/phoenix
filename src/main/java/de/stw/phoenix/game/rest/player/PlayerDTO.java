package de.stw.phoenix.game.rest.player;

import de.stw.phoenix.game.engine.construction.api.ConstructionEvent;
import de.stw.phoenix.game.engine.energy.EnergyOverview;
import de.stw.phoenix.game.engine.research.api.ResearchEvent;
import de.stw.phoenix.game.engine.resources.api.ResourceOverview;
import de.stw.phoenix.game.engine.resources.impl.ResourceSearchEvent;
import de.stw.phoenix.game.player.api.BuildingLevel;
import de.stw.phoenix.game.player.api.EventVisitor;
import de.stw.phoenix.game.player.api.GameEvent;
import de.stw.phoenix.game.player.api.Notification;
import de.stw.phoenix.game.player.api.ResearchLevel;
import de.stw.phoenix.game.player.api.ResourceSite;
import de.stw.phoenix.game.player.impl.Player;
import de.stw.phoenix.game.rest.GameEventDTO;
import de.stw.phoenix.game.rest.construction.ConstructionEventDTO;
import de.stw.phoenix.game.rest.research.ResearchEventDTO;
import de.stw.phoenix.game.rest.resources.ResourceSearchEventDTO;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PlayerDTO {
    private long id;
    private String name;
    private long totalDroneCount;
    private List<ResourceOverview> resourceOverviews;
    private List<BuildingLevel> buildings;
    private final List<ResearchLevel> researchs;
    private List<GameEventDTO> events;
    private List<ResourceSite> resourceSites;
    private final EnergyOverview energy;
    private List<Notification> notifications;

    public PlayerDTO(final Player player, final List<ResourceOverview> resourceOverviews, final EnergyOverview energyOverview) {
        Objects.requireNonNull(player);
        this.buildings = player.getBuildings();
        this.researchs = player.getResearchs();
        this.events = player.getEvents().stream().map(e -> convert(e)).collect(Collectors.toList());
        this.id = player.getId();
        this.name = player.getName();
        this.totalDroneCount = player.getTotalDroneCount();
        this.energy = Objects.requireNonNull(energyOverview).convert(TimeUnit.HOURS);
        this.resourceOverviews = Objects.requireNonNull(resourceOverviews).stream()
                .map(overview -> overview.convert(TimeUnit.MINUTES))
                .collect(Collectors.toList());
        this.resourceSites = Objects.requireNonNull(player.getResourceSites());
        this.notifications = player.getNotifications();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ResourceOverview> getResourceProductions() {
        return resourceOverviews;
    }

    public List<BuildingLevel> getBuildings() {
        return buildings;
    }

    public List<GameEventDTO> getEvents() {
        return events;
    }

    public List<ResourceSite> getResourceSites() {
        return resourceSites;
    }

    public EnergyOverview getEnergy() {
        return energy;
    }

    public long getTotalDroneCount() {
        return totalDroneCount;
    }
    
    public List<Notification> getNotifications() {
	return notifications;
    }

    public List<ResearchLevel> getResearchs() {
        return researchs;
    }

    // Converts the given gameEvent updating the time to subtract already passed ticks
    private static GameEventDTO convert(final GameEvent event) {
        final EventVisitor<GameEventDTO> visitor = new EventVisitor<GameEventDTO>() {

            @Override
            public GameEventDTO visit(ConstructionEvent constructionEvent) {
                return new ConstructionEventDTO(constructionEvent.getProgress(), constructionEvent.getBuilding(), constructionEvent.getLevelToBuild());
            }

            @Override
            public GameEventDTO visit(ResourceSearchEvent resourceSearchEvent) {
                return new ResourceSearchEventDTO(resourceSearchEvent.getProgress(), resourceSearchEvent.getResource());
            }

            @Override
            public GameEventDTO visit(ResearchEvent researchEvent) {
                return new ResearchEventDTO(researchEvent.getProgress(), researchEvent.getResearch(), researchEvent.getLevelToResearch());
            }
        };
        return event.accept(visitor);
    }
}