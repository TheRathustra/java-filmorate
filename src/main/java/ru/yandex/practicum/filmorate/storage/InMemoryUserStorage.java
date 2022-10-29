package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.error.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.*;
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
        User friend = getUser(friendId);
        friend.getFriends().add(user.getId());
    }

    @Override
    public void deleteFriend(User user, long friendId) {
        user.getFriends().remove(friendId);
        User friend = getUser(friendId);
        friend.getFriends().remove(user.getId());
    }

    @Override
    public List<User> getUserFriends(User user) {
        List<User> friends = user.getFriends().stream().map(this::getUser).collect(Collectors.toList());
        return friends;
    }

    @Override
    public List<User> getCommonFriends(User user, User friend) {

        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();
        List<User> common = userFriends.stream().filter(friendFriends::contains).map(this::getUser).collect(Collectors.toList());

        return common;
    }
}
