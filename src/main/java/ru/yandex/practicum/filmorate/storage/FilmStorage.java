package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface FilmStorage {

    Film getFilm(long filmId);

    List<Film> getFilms();

    Film add(Film film);

    Film update(Film film);

    Film delete(Film film);

    void addLike(Film film, long userId);

    void deleteLike(Film film, long userId);

    MPA getMpaByID(long id);

    List<MPA> getMPA();

    List<Genre> getGenres();

    Genre getGenre(long id);

}
