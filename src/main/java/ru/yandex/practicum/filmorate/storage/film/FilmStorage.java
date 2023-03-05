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

    /**
     * Метод для постановке лайка фильму
     * @param filmId - фильм, которому ставим лайк
     * @param userId - пользователь, который ставит лайк
     * @return
     */
    public Film putALike(Long filmId, Long userId);

    /**
     * Метод для удаления лайка с фильма
     * @param filmId - фильм, с которого удаляем
     * @param userId - чей лайк удаляем
     * @return
     */
    public Film deleteLike(Long filmId, Long userId);

    /**
     * Метод для получения рейтинга фильмов по количеству лайков(топ-count)
     * @param count количество фильмов, которые попадут в рейтинг
     * @return
     */
    public List<Film> getRating(int count);
}
