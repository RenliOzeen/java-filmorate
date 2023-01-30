package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController controller;
    UserStorage userStorage;
    UserService userService;

    @BeforeEach
    public void setup() {
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
        controller = new UserController(userStorage, userService);
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