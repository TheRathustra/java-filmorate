package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    UserStorage userStorage;

    public UserService(@Autowired UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getUser(long userId) {
        return userStorage.getUser(userId);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User add(User user) {
        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        return userStorage.add(user);
    }

    public User update(User user) {

        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        if (userStorage.getUser(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        return userStorage.update(user);

    }

    public User delete(User user) {

        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        if (userStorage.getUser(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        return userStorage.delete(user);

    }

    public void addFriend(long userId, long friendId) {

        User user = userStorage.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
        }
        User friend = userStorage.getUser(friendId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + friendId);
        }

        userStorage.addFriend(user, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
        }
        User friend = userStorage.getUser(friendId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + friendId);
        }

        userStorage.deleteFriend(user, friendId);
    }

    public List<Long> getUserFriends(long userId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
        }
        return userStorage.getUserFriends(user);
    }

    public List<Long> getCommonFriends(long userId, long friendId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
        }
        User friend = userStorage.getUser(friendId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + friendId);
        }
        return userStorage.getCommonFriends(user, friend);
    }

}
