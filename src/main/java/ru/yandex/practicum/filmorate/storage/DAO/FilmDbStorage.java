package ru.yandex.practicum.filmorate.storage.DAO;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.DAO.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.storage.DAO.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.storage.DAO.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film getFilm(long filmId) {
        String sqlQuery = "SELECT f.ID, f.NAME, f.DESCRIPTION, f.RELEASEDATE, f.DURATION, m.ID as mpa_id, " +
                "m.NAME as mpa_name FROM FILM as f\n" +
                "left join MPA M on M.ID = f.MPA\n" +
                "where f.ID = ?";

        Film film = jdbcTemplate.query(sqlQuery, new Object[]{filmId},
                new FilmMapper()).stream().findAny().orElse(null);

        if (film == null) {
            return film;
        }

        sqlQuery = "SELECT * FROM USERS WHERE ID IN(\n" +
                "select USER_ID from LIKES WHERE FILM_ID=?)";
        List<User> users = jdbcTemplate.query(sqlQuery, new Object[]{filmId}, new UserMapper());
        Set<Long> likes = new HashSet<>();
        for (User user : users) {
            likes.add(user.getId());
        }
        film.setLikes(new HashSet<>(likes));
        sqlQuery = "SELECT * FROM GENRE WHERE ID IN (\n" +
                "SELECT GENRE_ID FROM FILM_GENRE where  FILM_ID = ?);\n" +
                "\n";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, new Object[]{filmId}, new GenreMapper());
        film.setGenres(genres);

        return film;
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "SELECT f.ID, f.NAME, f.DESCRIPTION, f.RELEASEDATE, f.DURATION, m.ID as mpa_id, " +
                "m.NAME as mpa_name FROM FILM as f\n" +
                "left join MPA M on M.ID = f.MPA";
        List<Film> films = jdbcTemplate.query(sqlQuery, new FilmMapper());

        String sqlQueryLikes = "SELECT * FROM USERS WHERE ID IN(\n" +
                "select USER_ID from LIKES WHERE FILM_ID=?)";
        String sqlQueryGenres = "SELECT * FROM GENRE WHERE ID IN (\n" +
                "SELECT GENRE_ID FROM FILM_GENRE where  FILM_ID = ?);\n" +
                "\n";

        for (Film film : films) {
            List<User> users = jdbcTemplate.query(sqlQueryLikes, new Object[]{film.getId()}, new UserMapper());
            Set<Long> likes = new HashSet<>();
            for (User user : users) {
                likes.add(user.getId());
            }
            film.setLikes(likes);
            List<Genre> genres = jdbcTemplate.query(sqlQueryGenres, new Object[]{film.getId()}, new GenreMapper());
            film.setGenres(genres);
        }

        return films;
    }

    @Override
    public Film add(Film film) {

        String sqlQuery = "insert into film(name, description, releaseDate, duration, MPA) " +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        film.setId(id);

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            String sqlQueryGenre = "INSERT INTO FILM_GENRE(FILM_ID, GENRE_ID) VALUES (?,?)";
            List<Genre> genres = film.getGenres();
            jdbcTemplate.batchUpdate(sqlQueryGenre, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, id);
                    ps.setLong(2, genres.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return genres.size();
                }
            });
        }

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
                film.getMpa().getId(), film.getId());

        sqlQuery = "DELETE FROM FILM_GENRE WHERE FILM_ID=?";
        jdbcTemplate.update(sqlQuery, film.getId());
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            sqlQuery = "INSERT INTO FILM_GENRE(FILM_ID, GENRE_ID) VALUES (?,?)";
            List<Genre> genres = film.getGenres().stream().distinct().collect(Collectors.toList());
            film.setGenres(genres);
            jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, film.getId());
                    ps.setLong(2, genres.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return genres.size();
                }
            });
        }
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
        User user = jdbcTemplate.query("SELECT * FROM Users where id=?",
                new Object[]{userId}, new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User userFromDB = new User();
                        userFromDB.setId(rs.getInt("id"));
                        return userFromDB;
                    }
                }).stream().findAny().orElse(null);

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        String sqlQuery = "DELETE FROM likes WHERE film_id=? and user_id=?";
        jdbcTemplate.update(sqlQuery, film.getId(), userId);
    }

    @Override
    public MPA getMpaByID(long id) {
        String sqlQuery = "SELECT * FROM MPA where ID=?";
        MPA mpa = jdbcTemplate.query(sqlQuery, new Object[]{id},
                new MpaMapper()).stream().findAny().orElse(null);
        return mpa;
    }

    @Override
    public List<MPA> getMPA() {
        String sqlQuery = "SELECT * FROM MPA";
        List<MPA> mpa = jdbcTemplate.query(sqlQuery, new MpaMapper());

        return mpa;
    }

    @Override
    public List<Genre> getGenres() {
        String sqlQuery = "SELECT * FROM GENRE";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, new GenreMapper());
        return genres;
    }

    @Override
    public Genre getGenre(long id) {
        String sqlQuery = "SELECT * FROM GENRE where ID=?";
        Genre genre = jdbcTemplate.query(sqlQuery, new Object[]{id},
                new GenreMapper()).stream().findAny().orElse(null);
        return genre;
    }
}
