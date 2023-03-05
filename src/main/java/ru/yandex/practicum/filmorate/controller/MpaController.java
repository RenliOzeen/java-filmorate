package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.film.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaService mpaService;

    /**
     * Обработчик GET - запроса на получение списка всех Mpa-рейтингов
     * @return
     */
    @GetMapping
    public List<Mpa> findAll() {
        return mpaService.findAll();
    }

    /**
     * Обработчик GET-запроса на получение Mpa по его id
     * @param mpaId
     * @return
     */
    @GetMapping("/{id}")
    public Mpa getMpaRating(@PathVariable("id") Long mpaId) {
        return mpaService.getMpaRating(mpaId);
    }
}

