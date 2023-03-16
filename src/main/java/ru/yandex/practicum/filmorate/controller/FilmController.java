package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

/**
 * Класс-контроллер для фильмов
 */
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    /**
     * Обработчик GET-запроса на получение списка добавленных фильмов
     *
     * @return Список List с объектами <Film>
     */
    @GetMapping
    public List<Film> findAll() {
        log.info("Получен GET-запрос на получение списка всех фильмов");
        return filmStorage.findAll();
    }

    /**
     * Обработчик POST-запроса на добавление нового фильма
     *
     * @param film
     * @return объект Film, добавленный в список
     */
    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        log.info("Получен POST-запрос на добавление фильма");
        return filmStorage.add(film);
    }

    /**
     * Обработчик PUT-запроса на обновление фильма
     *
     * @param film
     * @return обновленные данные фильма
     */
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен PUT-запрос на обновление фильма");
        return filmStorage.update(film);
    }

    /**
     * Обработчик GET запроса на получение фильма по id
     *
     * @param id
     * @return экземпляр класса Film
     */
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable String id) {
        log.info("Получен GET-запрос на получение фильма");
        return filmStorage.getFilm(Long.parseLong(id));
    }

    /**
     * Обработчик PUT запроса на оценку фильма
     *
     * @param id     фильма, которому ставим лайк
     * @param userId пользователя, который ставит лайк
     */
    @PutMapping("/{id}/like/{userId}")
    public void putALike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен PUT-запрос на постановку лайка фильму");
        filmService.putALike(Long.parseLong(id), Long.parseLong(userId));
    }

    /**
     * Обработчик DELETE запроса на удаление лайка
     *
     * @param id     фильма, с которого снимаем лайк
     * @param userId пользователя, лайк которого снимаем
     */
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен DELETE-запрос на удаление лайка с фильма");
        filmService.deleteLike(Long.parseLong(id), Long.parseLong(userId));
    }

    /**
     * Обработчик GET запроса на получение списка фильмов, отсортированного по количеству лайков
     *
     * @param count размер списка, если не задано, то 10
     * @return отсортированный список фильмов
     */
    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") String count) {
        log.info("Получен GET-запрос на получение топа популярных фильмов");
        return filmService.getRating(Integer.parseInt(count));
    }
}
