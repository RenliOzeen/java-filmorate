package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Получение списка всех Mpa
     * @return
     */
    public List<Mpa> findAll(){
        List<Mpa> mpaList=new ArrayList<>();
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("SELECT * FROM mpa_type");
        while (mpaRows.next()) {
            Mpa mpa=Mpa.builder()
                    .id(mpaRows.getLong("rating_mpa_id"))
                    .name(mpaRows.getString("name"))
                    .build();
            mpaList.add(mpa);
        }
        return mpaList;

    }

    /**
     * Получение экземпляра Mpa по его id
     * @param mpaId
     * @return
     */
    public Mpa getMpa(Long mpaId){
        String sqlQuery= "SELECT * FROM mpa_type WHERE rating_mpa_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, mpaId);
        } catch (RuntimeException e){
            throw new NotFoundException("This Mpa was not found in the database");
        }
    }

    /**
     * Добавление Mpa к экзмпляру фильма
     * @param film
     * @return
     */
    public Film addMpaToFilm(Film film){
        findAll().forEach(mpa -> {
            if(film.getMpa().getId()==mpa.getId()){
                film.setMpa(mpa);
            }
        });
        return film;
    }

    /**
     * Вспомогательный метод для преобразования Mpa-рейтингов
     * @param resultSet
     * @param rowNum
     * @return
     * @throws SQLException
     */
    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        Mpa mpa = Mpa.builder()
                .id(resultSet.getLong("rating_mpa_id"))
                .name(resultSet.getString("name"))
                .build();
        return mpa;
    }
}
