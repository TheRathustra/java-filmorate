package ru.yandex.practicum.filmorate.storage.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUser(long id) {
        return jdbcTemplate.query("SELECT * FROM Users where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM Users", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User add(User user) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        long id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();

        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users SET email=?, login=?, name=?, birthday=? WHERE id=?";
        int i = jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public User delete(User user) {
        String sqlQuery = "DELETE FROM users WHERE id=?";
        jdbcTemplate.update(sqlQuery, user.getId());
        return user;
    }

    @Override
    public void addFriend(User user, long friendId) {
        String sqlQuery = "insert into friends(user_id, friend_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getId(),
                friendId);
    }

    @Override
    public void deleteFriend(User user, long friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id=? and friend_id=?";
        jdbcTemplate.update(sqlQuery, user.getId(), friendId);
    }

    @Override
    public List<User> getUserFriends(User user) {
        String sqlQuery = "Select * From users where id in (\n" +
                "select FRIEND_ID from FRIENDS where USER_ID = ?)";
        return jdbcTemplate.query(sqlQuery, new Object[]{user.getId()}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> getCommonFriends(User user, User friend) {

        String sqlQuery = "SELECT * FROM USERS as u where u.ID in (\n" +
                "select userFriends.FRIEND_ID from FRIENDS as userFriends\n" +
                "  inner join (select f.FRIEND_ID from FRIENDS as f where f.USER_ID = ?) as friendFriends on userFriends.FRIEND_ID = friendFriends.FRIEND_ID\n" +
                "                 where userFriends.USER_ID = ?)";
        return jdbcTemplate.query(sqlQuery, new Object[]{friend.getId(), user.getId()}, new BeanPropertyRowMapper<>(User.class));
    }
}


