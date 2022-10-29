package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User getUser(long userId);
    List<User> getUsers();
    User add(User user);
    User update(User user);
    User delete(User user);
    void addFriend(User user, long friendId);
    void deleteFriend(User user, long friendId);
    List<User> getUserFriends(User user);
    List<User> getCommonFriends(User user, User friend);
}
