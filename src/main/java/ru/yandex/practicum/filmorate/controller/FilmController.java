package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    FilmService filmService;

    public FilmController(@Autowired FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = "/films/{id}")
    public Film getFilm(@PathVariable long id) {
        return filmService.getFilm(id);
    }

    @GetMapping(value = "/films")
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping(value = "/mpa")
    public List<MPA> getMpa() {
        return filmService.getMPA();
    }

    @GetMapping(value = "/mpa/{id}")
    public MPA getMpaById(@PathVariable long id) {
        return filmService.getMpaByID(id);
    }

    @GetMapping(value = "/genres")
    public List<Genre> getGenres() {
        return filmService.getGenres();
    }

    @GetMapping(value = "/genres/{id}")
    public Genre getGenre(@PathVariable long id) {
        return filmService.getGenre(id);
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        return filmService.add(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @DeleteMapping(value = "/films")
    public Film deleteFilm(@Valid @RequestBody Film film) {
        return filmService.delete(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }

}
