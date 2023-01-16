package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс-контроллер для фильмов
 */
@RestController
@Slf4j
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1985, 12, 28);

    /**
     * Обработчик GET-запроса на получение списка добавленных фильмов
     *
     * @return Список List с объектами <Film>
     */
    @GetMapping("/films")
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    /**
     * Обработчик POST-запроса на добавление нового фильма
     *
     * @param film
     * @return объект Film, добавленный в список
     * @throws ValidationException
     */
    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (filmValidation(film)) {
            films.put(film.getId(), film);
            log.info("New film added: {}", film);
        } else {
            throw new ValidationException("Validation Error");
        }
        return film;
    }

    /**
     * Обработчик PUT-запроса на обновление фильма
     *
     * @param film
     * @return обновленные данные фильма
     * @throws ValidationException
     */
    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (filmValidation(film) && films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("The film is updated: {}", film);
        } else {
            throw new ValidationException("Validation Error");
        }
        return film;
    }

    /**
     * Метод валидации фильмов
     *
     * @param film
     * @return true-при прохождении валидации, false-при непрохождении
     */
    private boolean filmValidation(Film film) {
        if (film.getId() == 0) {
            film.setId(films.size() + 1);
        }
        if (film.getDescription().length() > 200) {
            log.warn("Maximum description length exceeded");
            return false;
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.warn("Release date earlier than the minimum possible");
            return false;
        }
        if (film.getDuration() < 0) {
            log.warn("The duration of the movie is less than zero");
            return false;
        }
        if (film.getName().isBlank()) {
            log.warn("The title of the film is missing");
            return false;
        }
        return true;
    }
}
