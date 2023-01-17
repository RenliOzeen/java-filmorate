package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
     * Тест на замену имени логином, в случае отсутствия имени
     */
    @Test
    public void shouldSetNameByLogin() {
        User user = User.builder()
                .id(1L)
                .name(null)
                .email("mail@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1994, 8, 17))
                .build();

        controller.create(user);
        assertTrue(controller.findAll().get(0).getName().equals("login"));

    }
}