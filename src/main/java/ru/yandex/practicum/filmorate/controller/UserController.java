package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private long id = 1;

    private Map<Long, User> users = new HashMap<>();

    @GetMapping(value ="/users")
    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {

        if (!isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        user.setId(id);
        id++;

        users.put(user.getId(), user);

        log.info("Пользователь с id " + user.getId() + " добавлен");

        return user;
    }

    @PutMapping(value ="/users")
    public User updateUser(@Valid @RequestBody User user) {

        if (!isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        if (users.get(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        users.put(user.getId(), user);

        log.info("Пользователь с id " + user.getId() + " обновлен");

        return user;
    }

    public boolean isValid(User user) {

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
