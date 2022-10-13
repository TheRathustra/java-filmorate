package ru.yandex.practicum.filmorate.controller;

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
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private int id = 1;

    private Map<Integer, User> users = new HashMap<>();

    @GetMapping(value ="/users")
    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) throws ValidationException {

        if (!isValid(user)) {
            throw new ValidationException();
        }

        int userId = user.getId();
        if (userId == 0) {
            user.setId(id);
            id++;
        } else {
            id = user.getId() + 1;
        }

        users.put(user.getId(), user);
        return user;
    }

    @PutMapping(value ="/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {

        if (!isValid(user)) {
            throw new ValidationException();
        }

        if (users.get(user.getId()) == null) {
            throw new IllegalArgumentException();
        }

        users.put(user.getId(), user);
        return user;
    }

    private boolean isValid(User user) {

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
