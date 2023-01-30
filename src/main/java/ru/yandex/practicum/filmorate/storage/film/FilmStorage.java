package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    /**
     * Метод для получения списка всех фильмов
     *
     * @return
     */
    public List<Film> findAll();

    /**
     * Метод для добавления нового фильма
     *
     * @param film
     * @return
     */
    public Film add(Film film);

    /**
     * Метод для обновления фильма
     *
     * @param film
     * @return
     */
    public Film update(Film film);

    /**
     * Метод для получения экземпляра фильма по его id
     *
     * @param id
     * @return
     */
    public Film getFilm(Long id);
}
