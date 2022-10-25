package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FilmController {

    private long id = 1;

    private Map<Long, Film> films = new HashMap<>();

    @GetMapping(value = "/films")
    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {

        if (!isValid(film)) {
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел валидацию");
        }

        film.setId(id);
        id++;

        films.put(film.getId(), film);

        log.info("Фильм с id " + film.getId() + " добавлен");

        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) {

        if (!isValid(film)) {
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел валидацию");
        }

        if (films.get(film.getId()) == null) {
            throw new IllegalArgumentException("Фильм с id " + film.getId() + " не найден");
        }

        films.put(film.getId(), film);

        log.info("Фильм с id " + film.getId() + " обновлен");

        return film;
    }

    public boolean isValid(Film film) {

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
