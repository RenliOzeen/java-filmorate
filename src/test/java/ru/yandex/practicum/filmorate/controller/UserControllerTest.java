package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController controller;

    @BeforeEach
    public void setup() {
        controller = new UserController();
    }

    /**
     * Блок тестов проверки валидации
     *
     * @throws ValidationException
     */
    @Test
    public void shouldThrowsValidateException() throws ValidationException {
        User user = User.builder()
                .id(1)
                .name(null)
                .email("mail@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1994, 8, 17))
                .build();

        controller.createUser(user);
        assertTrue(controller.findAll().get(0).getName().equals("login"));

        user.setEmail("ffdsfsfsdadf");
        assertThrows(ValidationException.class, () -> {
            controller.createUser(user);
        });

        user.setEmail("mail@mail.ru");
        user.setLogin("");
        assertThrows(ValidationException.class, () -> {
            controller.createUser(user);
        });

        user.setLogin("login");
        user.setBirthday(LocalDate.of(2024, 12, 11));
        assertThrows(ValidationException.class, () -> {
            controller.createUser(user);
        });
    }

    @Test
    public void shouldThrowsExceptionWithEmptyRequest() throws ValidationException {
        User user = User.builder().build();
        assertThrows(NullPointerException.class, () -> {
            controller.createUser(user);
        });
    }

}