package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage;

    public FilmService(@Qualifier("filmDbStorage") @Autowired FilmStorage filmStorage) {
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
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел превалидацию");
        }

        return filmStorage.add(film);

    }

    public Film update(Film film) {
        if (!ValidationService.isValid(film)) {
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел превалидацию");
        }

        if (filmStorage.getFilm(film.getId()) == null) {
            throw new IllegalArgumentException("Фильм с id " + film.getId() + " не найден");
        }


        Film updatedFilm = filmStorage.update(film);

        return updatedFilm;
    }

    public Film delete(Film film) {
        if (!ValidationService.isValid(film)) {
            throw new ValidationException("Фильм с id " + film.getId() + " не прошел превалидацию");
        }

        if (filmStorage.getFilm(film.getId()) == null) {
            throw new IllegalArgumentException("Фильм с id " + film.getId() + " не найден");
        }
        return filmStorage.delete(film);
    }

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new IllegalArgumentException("Фильм с id " + filmId + " не найден");
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

        try {
            filmStorage.deleteLike(film, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("user с id " + userId + " отсутствует в базе");
        }

    }

    public List<Film> getPopularFilms(int count) {
        List<Film> films = filmStorage.getFilms().stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .limit(count).collect(Collectors.toList());
        return films;
    }

    public MPA getMpaByID(long id) {
        MPA mpa = filmStorage.getMpaByID(id);
        if (mpa == null) {
            throw new NoSuchElementException("MPA с id " + id + " не найден");
        }

        return mpa;
    }

    public List<MPA> getMPA() {
        return filmStorage.getMPA();
    }

    public List<Genre> getGenres() {
        return filmStorage.getGenres();
    }

    public Genre getGenre(long id) {
        Genre genre = filmStorage.getGenre(id);
        if (genre == null) {
            throw new NoSuchElementException("Жанр с id " + id + " не найден");
        }
        return genre;
    }

}
