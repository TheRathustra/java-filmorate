package ru.yandex.practicum.filmorate.storage.DAO.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FilmMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
        film.setDuration(rs.getInt("duration"));

        MPA mpa = new MPA();
        mpa.setId(rs.getInt("mpa_id"));
        mpa.setName(rs.getString("mpa_name"));

        film.setMpa(mpa);
        return film;
    }
}

