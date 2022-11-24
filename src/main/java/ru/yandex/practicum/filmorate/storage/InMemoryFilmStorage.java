package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private long id = 1;
    private Map<Long, Film> films = new HashMap<>();

    @Override
    public Film getFilm(long filmId) {
        return films.get(filmId);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film add(Film film) {
        film.setId(id);
        id++;

        films.put(film.getId(), film);
        log.info("Фильм с id " + film.getId() + " добавлен");
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        log.info("Фильм с id " + film.getId() + " обновлен");
        return film;
    }

    @Override
    public Film delete(Film film) {
        Film deletedFilm = films.remove(film.getId());
        log.info("Фильм с id " + film.getId() + " удален");
        return deletedFilm;
    }

    @Override
    public void addLike(Film film, long userId) {
        film.getLikes().add(userId);
    }

    @Override
    public void deleteLike(Film film, long userId) {
        film.getLikes().remove(userId);
    }
    @Override
    public List<MPA> getMPA() {
        return null;
    }

    @Override
    public MPA getMpaByID(long id) {
        return null;
    }

    @Override
    public List<Genre> getGenres() {
        return null;
    }

    @Override
    public Genre getGenre(long id) {
        return null;
    }
}
