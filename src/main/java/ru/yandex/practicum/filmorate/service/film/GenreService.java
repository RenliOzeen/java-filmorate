package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    public Genre getGenre(Long genreId){
        return genreDbStorage.getGenreForId(genreId);
    }

    public List<Genre> findAll(){
        return genreDbStorage.findAll();
    }
}
