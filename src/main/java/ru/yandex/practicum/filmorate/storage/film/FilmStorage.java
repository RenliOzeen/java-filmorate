package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    /**
     * Метод для получения списка всех фильмов
     *
     * @return список экземпляров Film
     */
    List<Film> findAll();

    /**
     * Метод для добавления нового фильма
     *
     * @param film
     * @return экземпляр класса Film
     */
    Film add(Film film);

    /**
     * Метод для обновления фильма
     *
     * @param film
     * @return экземпляр класса Film
     */
    Film update(Film film);

    /**
     * Метод для получения экземпляра фильма по его id
     *
     * @param id
     * @return экземпляр класса Film
     */
    Film getFilm(Long id);

    /**
     * Метод для постановке лайка фильму
     *
     * @param filmId - фильм, которому ставим лайк
     * @param userId - пользователь, который ставит лайк
     * @return экземпляр класса Film
     */
    Film putALike(Long filmId, Long userId);

    /**
     * Метод для удаления лайка с фильма
     *
     * @param filmId - фильм, с которого удаляем
     * @param userId - чей лайк удаляем
     * @return экземпляр класса Film
     */
    Film deleteLike(Long filmId, Long userId);

    /**
     * Метод для получения рейтинга фильмов по количеству лайков(топ-count)
     *
     * @param count количество фильмов, которые попадут в рейтинг
     * @return экземпляр класса Film
     */
    List<Film> getRating(int count);
}
