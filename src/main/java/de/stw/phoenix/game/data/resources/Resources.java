package de.stw.phoenix.game.data.resources;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.NoSuchElementException;

public interface Resources {
    int MAX_STORAGE_CAPACITY = 100000;
    int DEFAULT_AMOUNT = 100000;

    Resource Iron = Resource.builder().id(1)
            .name("iron")
            .occurrence(0.8f)
            .build();

    Resource Stone = Resource.builder()
            .id(2)
            .name("stone")
            .occurrence(0.9f)
            .build();

    Resource Oil = Resource
            .builder()
            .id(3)
            .name("oi")
            .occurrence(0.7f)
            .build();

    List<Resource> ALL = Lists.newArrayList(Iron, Stone, Oil);

    static Resource findById(int resourceId) {
        return ALL.stream()
                .filter(b -> b.getId() == resourceId)
                .findAny().orElseThrow(() -> new NoSuchElementException("No resource found with id '" + resourceId + "'"));
    }
}
