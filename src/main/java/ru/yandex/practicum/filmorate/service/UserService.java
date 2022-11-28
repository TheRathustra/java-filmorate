package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private UserStorage userDao;

    public UserService(@Qualifier("userDbStorage") @Autowired UserStorage userDao) {
        this.userDao = userDao;
    }

    public User getUser(long userId) {
        User user = userDao.getUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");
        }
        return user;
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User add(User user) {
        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел превалидацию");
        }

        return userDao.add(user);
    }

    public User update(User user) {

        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел превалидацию");
        }

        if (userDao.getUser(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не найден");
        }

        return userDao.update(user);

    }

    public User delete(User user) {

        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел превалидацию");
        }

        if (userDao.getUser(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не найден");
        }

        return userDao.delete(user);

    }

    public void addFriend(long userId, long friendId) {

        User user = userDao.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");
        }
        User friend = userDao.getUser(friendId);
        if (friend == null) {
            log.info("В базе нет пользователя с id " + friendId);
            throw new IllegalArgumentException("Пользователь с id " + friendId + " не найден");
        }

        userDao.addFriend(user, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        User user = userDao.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");
        }
        User friend = userDao.getUser(friendId);
        if (friend == null) {
            log.info("В базе нет пользователя с id " + friendId);
            throw new IllegalArgumentException("Пользователь с id " + friendId + " не найден");
        }

        userDao.deleteFriend(user, friendId);
    }

    public List<User> getUserFriends(long userId) {
        User user = userDao.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");
        }
        List<User> friends = userDao.getUserFriends(user);
        return friends;
    }

    public List<User> getCommonFriends(long userId, long friendId) {
        User user = userDao.getUser(userId);
        if (user == null) {
            log.info("В базе нет пользователя с id " + userId);
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");
        }
        User friend = userDao.getUser(friendId);
        if (friend == null) {
            log.info("В базе нет пользователя с id " + friendId);
            throw new IllegalArgumentException("Пользователь с id " + friendId + " не найден");
        }
        return userDao.getCommonFriends(user, friend);
    }

}
