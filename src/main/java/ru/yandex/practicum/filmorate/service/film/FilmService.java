package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    /**
     * Метод-сервис для постановки лайка фильму
     *
     * @param filmId
     * @param userId
     */
    public void putALike(Long filmId, Long userId) {
        filmStorage.getFilm(filmId).getLikes().add(userId);
    }

    /**
     * Метод-сервис для удаления лайка с фильма
     *
     * @param filmId
     * @param userId
     */
    public void deleteLike(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId).getLikes().contains(userId)) {
            filmStorage.getFilm(filmId).getLikes().remove(userId);
        } else {
            throw new NotFoundException("Указанный пользователь не ставил оценку данному фильму");
        }

    }

    /**
     * Метод-сервис для получения отсортированного по лайкам списка фильмов
     *
     * @param count
     * @return
     */
    public List<Film> getRating(int count) {
        return filmStorage.findAll().stream().sorted((film1, film2) -> {
            int value = film2.getLikes().size() - film1.getLikes().size();
            return value;
        }).limit(count).collect(Collectors.toList());
    }
}
