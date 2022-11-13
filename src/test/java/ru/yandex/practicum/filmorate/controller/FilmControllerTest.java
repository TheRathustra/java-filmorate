package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    /*
    FilmController filmController = new FilmController();

    @Test
    void isValid() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2020, Month.JANUARY, 1));
        film.setDuration(100);

        boolean valid = filmController.isValid(film);
        Assertions.assertTrue(valid, "Фильм не прошел валидацию");

        film.setReleaseDate(LocalDate.of(1700, Month.JANUARY, 1));
        valid = filmController.isValid(film);
        Assertions.assertFalse(valid, "Фильм прошел валидацию");

        film.setReleaseDate(LocalDate.of(2020, Month.JANUARY, 1));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= 205; i++ ) {
            stringBuilder.append("c");
        }

        film.setDescription(stringBuilder.toString());
        Assertions.assertFalse(valid, "Фильм прошел валидацию");
    }

     */
}