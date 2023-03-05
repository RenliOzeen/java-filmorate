package ru.yandex.practicum.filmorate.model;


import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    private Long id;

    @NotBlank(message = "The title of the film is missing")
    private String name;


    @NotBlank
    @Size(max = 200, message = "Maximum description length exceeded")
    private String description;

    @NotNull
    @ReleaseDate(message = "Release date earlier than the minimum possible")
    private LocalDate releaseDate;

    @Positive(message = "The duration of the movie is less than zero")
    private long duration;

    private Mpa mpa;

    private Set<Genre> genres;

    private Set<Long> likes;


}
