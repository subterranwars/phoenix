package de.stw.phoenix.game.data.resources;

public interface Resources {
    int MAX_STORAGE_CAPACITY = 100000;
    int DEFAULT_AMOUNT = 100000;

    Resource Iron = new Resource(1, "iron");
    Resource Stone = new Resource(2, "stone");
    Resource Food = new Resource(3, "food");
    Resource Oil = new Resource(4, "oil");
}
