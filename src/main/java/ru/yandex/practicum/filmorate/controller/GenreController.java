package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.film.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    /**
     * Обработчик GET запроса на получение списка всех жанров
     * @return список экземпляров Genre
     */
    @GetMapping
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    /**
     * Обработчик GET запроса на получение жанра по его id
     * @param genreId
     * @return экземпляр класса Genre
     */
    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable("id") Long genreId) {
        return genreService.getGenre(genreId);
    }
}
