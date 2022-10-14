package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController userController = new UserController();

    @Test
    void isValid() {
        User user = new User();
        user.setName("Name");
        user.setEmail("email@yandex.ru");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2020, Month.JANUARY, 1));

        boolean valid = userController.isValid(user);
        Assertions.assertTrue(valid, "Пользователь не прошел валидацию");

        user.setLogin("lo gin");

        valid = userController.isValid(user);
        Assertions.assertFalse(valid, "Пользователь прошел валидацию");


        user.setLogin("");
        user.setName("");

        valid = userController.isValid(user);
        Assertions.assertFalse(valid, "Пользователь прошел валидацию");
    }
}