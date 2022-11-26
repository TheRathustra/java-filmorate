package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.DAO.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql({"classpath:schema.sql", "classpath:data.sql", "classpath:tests.sql"})
class FilmorateApplicationTests {

	private JdbcTemplate jdbcTemplate;
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;

	@Test()
	void FindUserById() {

		User user = userStorage.getUser(1);
		Assertions.assertNotNull(user);
		Assertions.assertEquals(1, user.getId(), "Пользователь не получен");

	}

	@Test
	void FindAllUsers() {
		List<User> users = userStorage.getUsers();
		Assertions.assertNotEquals(0, users.size(), "Список пользователей не должен быть пустым");
	}

	@Test
	void addUser() {
		User newUser = new User();
		newUser.setName("New user");
		User dbUser = userStorage.add(newUser);
		dbUser = userStorage.getUser(dbUser.getId());
		Assertions.assertEquals(newUser.getName(), dbUser.getName(), "Пользователь не добавлен");
	}

	@Test
	void updateUser() {
		long id = 1;
		User dbUser = userStorage.getUser(id);
		dbUser.setName("updated user");
		userStorage.update(dbUser);
		User updatedUser = userStorage.getUser(id);
		Assertions.assertEquals(dbUser.getName(), updatedUser.getName(), "Пользователь не обновился");
	}

	@Test
	void deleteUser() {
		long id = 1;
		User dbUser = userStorage.getUser(id);
		userStorage.delete(dbUser);
		User deletedUser = userStorage.getUser(id);
		Assertions.assertNull(deletedUser);
	}

	@Test
	void addFriend() {
		long id = 1;
		User dbUser = userStorage.getUser(id);
		long friendId = 2;
		userStorage.addFriend(dbUser, friendId);
		List<User> userFriends = userStorage.getUserFriends(dbUser);
		boolean friendAdded = false;
		for (User userFriend : userFriends) {
			if (userFriend.getId() == friendId) {
				friendAdded = true;
				break;
			}
		}

		Assertions.assertTrue(friendAdded);

	}

	@Test
	void deleteFriend() {
		long id = 1;
		User dbUser = userStorage.getUser(id);
		long friendId = 3;
		userStorage.addFriend(dbUser, friendId);
		userStorage.deleteFriend(dbUser, 3);
		List<User> userFriends = userStorage.getUserFriends(dbUser);
		boolean friendAdded = false;
		for (User userFriend : userFriends) {
			if (userFriend.getId() == friendId) {
				friendAdded = true;
				break;
			}
		}
		Assertions.assertFalse(friendAdded);
	}

	@Test
	void getUserFriend() {
		long id = 1;
		User dbUser = userStorage.getUser(id);
		userStorage.addFriend(dbUser, 3);
		List<User> userFriends = userStorage.getUserFriends(dbUser);
		Assertions.assertNotEquals(0, userFriends.size(), "Список друзей не должен быть пустым");
	}

	@Test
	void getCommonFriends() {
		User user = userStorage.getUser(1);
		User friend = userStorage.getUser(2);
		userStorage.addFriend(user, 4);
		userStorage.addFriend(friend, 4);
		List<User> commonFriends = userStorage.getCommonFriends(user, friend);
		Assertions.assertNotEquals(0, commonFriends.size(), "Список друзей не должен быть пустым");
	}

	@Test()
	void FindFilmById() {

		Film film = filmStorage.getFilm(1);
		Assertions.assertNotNull(film);
		Assertions.assertEquals(1, film.getId(), "Фильм не получен");

	}

	@Test()
	void getFilms() {

		List<Film> films = filmStorage.getFilms();
		Assertions.assertNotEquals(0, films.size(), "Фильмы не получены");

	}

	@Test()
	void addFilm() {
		MPA mpa = new MPA();
		mpa.setId(1);
		mpa.setName("test");

		Film newFilm = new Film();
		newFilm.setDuration(1);
		newFilm.setReleaseDate(LocalDate.of(2020, 1, 1));
		newFilm.setDescription("test");
		newFilm.setMpa(mpa);
		newFilm.setName("New film");

		Film dbFilm = filmStorage.add(newFilm);
		dbFilm = filmStorage.getFilm(dbFilm.getId());
		Assertions.assertEquals(newFilm.getName(), dbFilm.getName(), "Фильм не добавлен");
	}

	@Test()
	void updateFilm() {
		long id = 1;
		Film dbFilm = filmStorage.getFilm(id);
		dbFilm.setName("updated user");
		filmStorage.update(dbFilm);
		Film updatedFilm = filmStorage.getFilm(id);
		Assertions.assertEquals(dbFilm.getName(), updatedFilm.getName(), "Фильм не обновился");
	}

	@Test()
	void deleteFilm() {
		long id = 1;
		Film dbFilm = filmStorage.getFilm(id);
		filmStorage.delete(dbFilm);
		Film deletedFilm = filmStorage.getFilm(id);
		Assertions.assertNull(deletedFilm);
	}

	@Test()
	void addLike() {
		long id = 1;
		Film film = filmStorage.getFilm(id);
		long userId = 2;
		filmStorage.addLike(film, userId);

		Film dbFilm = filmStorage.getFilm(id);
		boolean likeAdded = dbFilm.getLikes().contains(userId);
		Assertions.assertTrue(likeAdded);
	}

	@Test()
	void deleteLike() {
		long id = 2;
		Film film = filmStorage.getFilm(id);
		long userId = 3;
		filmStorage.addLike(film, userId);
		filmStorage.deleteLike(film, userId);

		Film dbFilm = filmStorage.getFilm(id);
		boolean likeAdded = dbFilm.getLikes().contains(userId);
		Assertions.assertFalse(likeAdded);
	}

	@Test()
	void getMPA() {
		List<MPA> mpa = filmStorage.getMPA();
		Assertions.assertNotEquals(0, mpa.size(), "MPA не получены");
	}

	@Test()
	void getGenres() {
		List<Genre> genres = filmStorage.getGenres();
		Assertions.assertNotEquals(0, genres.size(), "Жанры не получены");
	}

}