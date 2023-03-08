package ru.yandex.practicum.filmorate.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    Long id;

    @NotBlank(message = "The title of the film is missing")
    String name;


    @NotBlank
    @Size(max = 200, message = "Maximum description length exceeded")
    String description;

    @NotNull
    @ReleaseDate(message = "Release date earlier than the minimum possible")
    LocalDate releaseDate;

    @Positive(message = "The duration of the movie is less than zero")
    long duration;

    Mpa mpa;

    Set<Genre> genres;

    Set<Long> likes;


}
