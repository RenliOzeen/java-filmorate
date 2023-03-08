package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;

    @NotEmpty
    @Email(message = "Incorrect email")
    String email;

    @NotBlank(message = "Login is empty")
    @Pattern(regexp = "\\S*", message = "Login contains spaces")
    String login;

    String name;

    @NotNull
    @PastOrPresent(message = "The user's date of birth is later than the current date")
    LocalDate birthday;

    Set<Long> friends;
}
