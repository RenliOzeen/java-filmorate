package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Long, Film> films = new HashMap<>();

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film add(Film film) {
        film.setId((long) films.size() + 1);
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
        log.info("New film added: {}", film);

        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            film.setLikes(new HashSet<>());
            films.put(film.getId(), film);
            log.info("The film is updated: {}", film);
        } else {
            throw new NotFoundException("No such film was found");
        }
        return film;
    }

    @Override
    public Film getFilm(Long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("The film with this id was not found");
        }
        return films.get(id);
    }
}
