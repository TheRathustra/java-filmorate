package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private long id = 1;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User getUser(long userId) {
        return users.get(userId);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User add(User user) {
        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        user.setId(id);
        id++;

        users.put(user.getId(), user);

        log.info("Пользователь с id " + user.getId() + " добавлен");

        return user;
    }

    @Override
    public User update(User user) {

        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        if (users.get(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        users.put(user.getId(), user);

        log.info("Пользователь с id " + user.getId() + " обновлен");

        return user;

    }

    @Override
    public User delete(User user) {

        if (!ValidationService.isValid(user)) {
            throw new ValidationException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        if (users.get(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не прошел валидацию");
        }

        User deletedUser = users.remove(user.getId());

        log.info("Пользователь с id " + user.getId() + " удален");

        return deletedUser;

    }

    @Override
    public void addFriend(User user, long friendId) {
        user.getFriends().add(friendId);
    }

    @Override
    public void deleteFriend(User user, long friendId) {
        user.getFriends().remove(friendId);
    }

    @Override
    public List<Long> getUserFriends(User user) {
        return new ArrayList<>(user.getFriends());
    }

    @Override
    public List<Long> getCommonFriends(User user, User friend) {

        List<Long> userFriends = getUserFriends(user);
        List<Long> friendFriends = getUserFriends(friend);
        List<Long> common = userFriends.stream().filter(friendFriends::contains).collect(Collectors.toList());

        return common;
    }
}
