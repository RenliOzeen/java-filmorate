package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private Long id;

    @NotEmpty
    @Email(message = "Incorrect email")
    private String email;

    @NotBlank(message = "Login is empty")
    @Pattern(regexp = "\\S*", message = "Login contains spaces")
    private String login;

    private String name;

    @NotNull
    @PastOrPresent(message = "The user's date of birth is later than the current date")
    private LocalDate birthday;

    private Set<Long> friends;
}
