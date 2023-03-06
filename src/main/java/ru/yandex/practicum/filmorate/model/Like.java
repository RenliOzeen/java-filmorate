package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class Like {

    @NotNull
    private Long id;

    @NotNull
    @Positive
    private Long filmId;

    @NotNull
    @Positive
    private Long userId;
}
