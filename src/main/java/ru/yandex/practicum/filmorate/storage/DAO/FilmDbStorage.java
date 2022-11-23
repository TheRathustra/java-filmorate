package ru.yandex.practicum.filmorate.storage.DAO;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Repository
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film getFilm(long filmId) {
        return jdbcTemplate.query("SELECT * FROM film where id=?", new Object[]{filmId}, new BeanPropertyRowMapper<>(Film.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Film> getFilms() {
        return jdbcTemplate.query("SELECT * FROM film", new BeanPropertyRowMapper<>(Film.class));
    }

    @Override
    public Film add(Film film) {
        String sqlQuery = "insert into film(name, description, releaseDate, duration, MPA) " +
                "values (?, ?, ?, ?, ?)";
        int i = jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa());
        film.setId(i);
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE film SET name=?, description=?, releaseDate=?, duration=?, mpa=? WHERE id=?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa(), film.getId());
        return film;
    }

    @Override
    public Film delete(Film film) {
        String sqlQuery = "DELETE FROM film WHERE id=?";
        jdbcTemplate.update(sqlQuery, film.getId());
        return film;
    }

    @Override
    public void addLike(Film film, long userId) {
        String sqlQuery = "insert into likes(film_id, user_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getId(),
                userId);
    }

    @Override
    public void deleteLike(Film film, long userId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id=? and user_id=?";
        jdbcTemplate.update(sqlQuery, film.getId(), userId);
    }
}
