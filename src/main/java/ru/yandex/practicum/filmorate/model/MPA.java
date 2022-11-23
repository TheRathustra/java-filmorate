package ru.yandex.practicum.filmorate.model;

public enum MPA {

    G ("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private String name;
    MPA(String name) {
        this.name = name;
    }
}
