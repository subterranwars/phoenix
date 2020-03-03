package de.stw.core.resources;

public interface Resources {
    int MAX_STORAGE_CAPACITY = 20000;

    Resource Iron = new Resource(1, "iron");
    Resource Stone = new Resource(2, "stone");
    Resource Food = new Resource(3, "food");
    Resource Oil = new Resource(4, "oil");
}
