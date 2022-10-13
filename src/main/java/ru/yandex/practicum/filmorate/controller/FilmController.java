package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FilmController {

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private int id = 1;

    private Map<Integer, Film> films = new HashMap<>();

    @GetMapping(value = "/films")
    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {

        if (!isValid(film)) {
            throw new ValidationException();
        }

        int filmId = film.getId();
        if (filmId == 0) {
            film.setId(id);
            id++;
        } else {
            id = film.getId() + 1;
        }

        films.put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {

        if (!isValid(film)) {
            throw new ValidationException();
        }

        if (films.get(film.getId()) == null) {
            throw new IllegalArgumentException();
        }

        films.put(film.getId(), film);
        return film;
    }

    private boolean isValid(Film film) {

        boolean valid = true;
        if (film.getDescription().length() > 200) {
            log.info("Описание фильма длиннее 200 символов", film.getDescription());
            valid = false;
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            log.info("Дата релиза фильма раньше 28.12.1895", film.getReleaseDate());
            valid = false;
        }

        return valid;
    }

}
