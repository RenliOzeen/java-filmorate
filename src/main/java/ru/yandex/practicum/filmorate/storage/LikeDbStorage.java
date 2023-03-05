package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LikeDbStorage {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Метод для получения множества лайков для конкретного фильма
     * @param id
     * @return
     */
    public Set<Long> getLikesForCurrentFilm(Long id) {
        Set<Long> likes = new HashSet<>();
        SqlRowSet likeRows = jdbcTemplate.queryForRowSet("SELECT * FROM likes");
        while (likeRows.next()) {
            if (likeRows.getLong("film_id") == id) {
                likes.add(likeRows.getLong("like_id"));
            }
        }
        return likes;
    }
}
