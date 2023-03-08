package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public void putALike(Long filmId, Long userId) {
        filmStorage.putALike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId).getLikes().contains(userId)) {
            filmStorage.deleteLike(filmId, userId);
        } else {
            throw new NotFoundException("Указанный пользователь не ставил оценку данному фильму");
        }

    }

    public List<Film> getRating(int count) {
        return filmStorage.getRating(count);
    }
}
