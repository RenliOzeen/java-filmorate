package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController controller;

    @BeforeEach
    public void setup() {
        controller = new FilmController();
    }

    /**
     * Блок тестов проверки валидации
     *
     * @throws ValidationException
     */
    @Test
    public void shouldThrowsValidationException() throws ValidationException {
        Film film = Film.builder()
                .description("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                        "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
                        "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc")
                .name("name")
                .duration(180)
                .releaseDate(LocalDate.of(2022, 11, 22))
                .id(1)
                .build();
        assertThrows(ValidationException.class, () -> {
            controller.addFilm(film);
        });

        film.setDescription("description");
        film.setName("");
        assertThrows(ValidationException.class, () -> {
            controller.addFilm(film);
        });

        film.setName("name");
        film.setDuration(-10);
        assertThrows(ValidationException.class, () -> {
            controller.addFilm(film);
        });

        film.setDuration(90);
        film.setReleaseDate(LocalDate.of(1846, 12, 01));
        assertThrows(ValidationException.class, () -> {
            controller.addFilm(film);
        });

    }

    @Test
    public void shouldThrowsExceptionWithEmptyRequest() throws ValidationException {
        Film film = Film.builder().build();
        assertThrows(NullPointerException.class, () -> {
            controller.addFilm(film);
        });
    }


}