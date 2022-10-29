package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

@Slf4j
public class ValidationService {
    private ValidationService() {
    }

    public static boolean isValid(Film film) {

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

    public static boolean isValid(User user) {

        boolean valid = true;
        if (user.getLogin().contains(" ")) {
            log.info("Логин содержит пробелы", user.getLogin());
            valid = false;
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        if (user.getName().isEmpty()) {
            log.info("Не заполнено имя", user.getName());
            valid = false;
        }

        return valid;

    }

}
