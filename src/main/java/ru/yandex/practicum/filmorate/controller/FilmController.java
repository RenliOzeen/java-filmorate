package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

/**
 * Класс-контроллер для фильмов
 */
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private Map<Long, Film> films = new HashMap<>();

    /**
     * Обработчик GET-запроса на получение списка добавленных фильмов
     *
     * @return Список List с объектами <Film>
     */
    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    /**
     * Обработчик POST-запроса на добавление нового фильма
     *
     * @param film
     * @return объект Film, добавленный в список
     */
    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        film.setId((long) films.size() + 1);
        films.put(film.getId(), film);
        log.info("New film added: {}", film);

        return film;
    }

    /**
     * Обработчик PUT-запроса на обновление фильма
     *
     * @param film
     * @return обновленные данные фильма
     */
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("The film is updated: {}", film);
        } else {
            throw new ValidationException("Validation Error");
        }
        return film;
    }
}
