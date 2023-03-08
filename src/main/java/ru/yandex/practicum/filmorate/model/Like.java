package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Like {

    @NotNull
    Long id;

    @NotNull
    @Positive
    Long filmId;

    @NotNull
    @Positive
    Long userId;
}
