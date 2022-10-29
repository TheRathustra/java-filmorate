package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    FilmStorage filmStorage;

    public FilmService(@Autowired FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilm(long filmId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new IllegalArgumentException("Фильм с id " + filmId + " не найден");
        }
        return film;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film add(Film film) {
        if (!ValidationService.isValid(film)) {
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел валидацию");
        }

        return filmStorage.add(film);

    }

    public Film update(Film film) {
        if (!ValidationService.isValid(film)) {
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел валидацию");
        }

        if (filmStorage.getFilm(film.getId()) == null) {
            throw new IllegalArgumentException("Фильм с id " + film.getId() + " не найден");
        }

        return filmStorage.update(film);
    }

    public Film delete(Film film) {
        if (!ValidationService.isValid(film)) {
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел валидацию");
        }

        if (filmStorage.getFilm(film.getId()) == null) {
            throw new IllegalArgumentException("Фильм с id " + film.getId() + " не найден");
        }
        return filmStorage.delete(film);
    }

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new IllegalArgumentException("Фильм с id " + filmId+ " не найден");
        }
        filmStorage.addLike(film, userId);
    }

    public void deleteLike(long filmId, long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new IllegalArgumentException("Фильм с id " + filmId + " не найден");
        }

        if (!film.getLikes().contains(userId)) {
            throw new IllegalArgumentException("у фильма с id " + filmId + " нет лайка от " + userId);
        }

        filmStorage.deleteLike(film, userId);
    }

    public List<Film> getPopularFilms(int count) {
        if (count <= 0) {
            count = 10;
        }

        List<Film> films = filmStorage.getFilms().stream().sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())).limit(count).collect(Collectors.toList());
        return films;
    }

}
