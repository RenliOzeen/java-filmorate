package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;


    /**
     * Получение списка всех жанров
     * @return
     */
    public List<Genre> findAll() {
        List<Genre> genreList = new ArrayList<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT * FROM genre_type");
        while (genreRows.next()) {
            Genre genre = Genre.builder()
                    .id(genreRows.getLong("genre_id"))
                    .name(genreRows.getString("name"))
                    .build();
            genreList.add(genre);
        }
        return genreList;
    }

    /**
     * Получение множества всех жанров конкретного фильма по его id
     * @param id
     * @return
     */
    public Set<Genre> getGenreForCurrentFilm(Long id) {
        Set<Genre> genreSet = new HashSet<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT * FROM genre ORDER BY genre_id ASC");
        while (genreRows.next()) {
            if (genreRows.getLong("film_id") == id) {
                genreSet.add(getGenreForId(genreRows.getLong("genre_id")));
            }
        }
        return genreSet;
    }

    /**
     * Добавление жанров конкретному фильму
     * @param film
     * @return
     */
    public Film addGenresForCurrentFilm(Film film) {
        if(film.getGenres() == null){
            return film;
        }
        film.getGenres().forEach(g -> {
            String sqlQuery = "INSERT INTO genre(film_id, genre_id) VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery,
                    film.getId(),
                    g.getId());
        });
        return film;
    }

    /**
     * Обновление жанров фильма
     * @param film
     * @return
     */
    public Film updateGenresForCurrentFilm(Film film) {
        String sqlQuery = "DELETE FROM genre WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        addGenresForCurrentFilm(film);
        return film;
    }

    /**
     * Получение жанра по его id
     * @param id
     * @return
     */
    public Genre getGenreForId(Long id) {
        String sqlQuery = "SELECT * FROM genre_type WHERE genre_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (RuntimeException e) {
            throw new NotFoundException("This genre was not found in the database");
        }

    }

    /**
     * Метод для добавления названия жанра в объект жанра, хранящийся в фильме
     * @param film
     * @return
     */
    public Film addGenreNameToFilm(Film film){
        if(film.getGenres()==null){
            return film;
        }
        film.getGenres().forEach(g -> g.setName(getGenreForId(g.getId()).getName()));
        return film;
    }

    /**
     * Вспомогательный метод преобразования жанров
     */
    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = Genre.builder()
                .id(resultSet.getLong("genre_id"))
                .name(resultSet.getString("name"))
                .build();
        return genre;
    }
}
