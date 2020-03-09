package de.stw.phoenix.game.data.resources;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.NoSuchElementException;

public interface Resources {
    int MAX_STORAGE_CAPACITY = 100000;
    int DEFAULT_AMOUNT = 100000;
    int HQ_PRODUCTION_PER_HOUR = 60;
    int SITE_PRODUCTION_PER_HOUR = 75;

    Resource Iron = Resource.builder().id(1)
            .name("iron")
            .occurrence(0.8f)
            .build();

    Resource Stone = Resource.builder()
            .id(2)
            .name("stone")
            .occurrence(0.9f)
            .build();

    Resource Food = Resource.builder()
            .id(3)
            .name("food")
            .occurrence(1)
            .build();

    Resource Oil = Resource
            .builder()
            .id(4)
            .name("oil")
            .occurrence(0.7f)
            .build();

    List<Resource> ALL = Lists.newArrayList(Iron, Stone, Food, Oil);

    List<Resource> BASICS = Lists.newArrayList(Iron, Stone, Food);

    static Resource findById(int resourceId) {
        return ALL.stream()
                .filter(b -> b.getId() == resourceId)
                .findAny().orElseThrow(() -> new NoSuchElementException("No resource found with id '" + resourceId + "'"));
    }
}
