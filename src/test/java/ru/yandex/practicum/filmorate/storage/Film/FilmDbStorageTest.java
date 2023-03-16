package ru.yandex.practicum.filmorate.storage.Film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.User.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final LikeDbStorage likeDbStorage;
    private final JdbcTemplate jdbcTemplate;
    Film film;
    Film film2;
    User user;
    User user2;


    @BeforeEach
    void setup() {
        jdbcTemplate.update("DELETE FROM likes");
        jdbcTemplate.update("DELETE FROM films");
        film = Film.builder()
                .name("name")
                .description("desc")
                .releaseDate(LocalDate.of(1999, 8, 17))
                .duration(136L)
                .build();
        film.setGenres(new HashSet<>());
        film.setLikes(new HashSet<>());
        film.setMpa(Mpa.builder()
                .id(1L)
                .name("NC-17")
                .build());

        film2 = Film.builder()
                .name("name2")
                .description("desc")
                .releaseDate(LocalDate.of(1999, 8, 17))
                .duration(136L)
                .build();
        film2.setGenres(new HashSet<>());
        film2.setLikes(new HashSet<>());
        film2.setMpa(Mpa.builder()
                .id(1L)
                .name("NC-17")
                .build());

        user = User.builder()
                .email("mail@mail.mail")
                .login("login")
                .birthday(LocalDate.of(1999, 8, 17))
                .build();
        user.setFriends(new HashSet<>());

        user2 = User.builder()
                .email("gmail@gmail.gmail")
                .login("nelogin")
                .birthday(LocalDate.of(2001, 6, 19))
                .build();
        user2.setFriends(new HashSet<>());
    }

    /**
     * Тест на создание, получение и обновление фильма
     */
    @Test
    void shouldCreateAndGetAndUpdateFilm() {
        filmDbStorage.add(film);
        assertEquals(film, filmDbStorage.getFilm(film.getId()));

        film.setName("updateName");
        filmDbStorage.update(film);
        assertEquals("updateName", filmDbStorage.getFilm(film.getId()).getName());

        assertEquals(1, filmDbStorage.findAll().size());
    }

    /**
     * Тест на постановку лайка и получение рейтинга фильмов
     */
    @Test
    void shouldPutALikeAndGetRating() {
        filmDbStorage.add(film);
        userDbStorage.create(user);
        userDbStorage.create(user2);
        filmDbStorage.putALike(1L, 1L);
        assertTrue(likeDbStorage.getLikesForCurrentFilm(1L).size() == 1);

        filmDbStorage.add(film2);
        filmDbStorage.putALike(2L, 1L);
        filmDbStorage.putALike(2L, 2L);
        assertTrue(filmDbStorage.getRating(2).get(0).getId() == 2L);
        assertTrue(filmDbStorage.getRating(2).size() == 2);

        filmDbStorage.deleteLike(1L, 1L);
        assertTrue(likeDbStorage.getLikesForCurrentFilm(1L).isEmpty());
    }

}