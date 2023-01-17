package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

/**
 * Класс-контроллер для пользователей
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private Map<Long, User> users = new HashMap<>();

    /**
     * Обработчик GET-запроса на получение списка всех пользователей
     *
     * @return Список List с объектами <User>
     */
    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Обработчик POST-запроса на добавление нового пользователя
     *
     * @param user
     * @return объект User, добавленный в список
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            user.setId((long) users.size() + 1);
        }
        validation(user);
        users.put(user.getId(), user);
        log.info("New user added: {}", user);

        return user;
    }

    /**
     * Обработчик PUT-запроса на обновление данных пользователя
     *
     * @param user
     * @return обновленные данные пользователя
     * @throws ValidationException
     */
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        if (users.containsKey(user.getId())) {
            validation(user);
            users.put(user.getId(), user);
            log.info("User data updated: {}", user);
        } else {
            throw new ValidationException("Validation Error");
        }
        return user;
    }

    /**
     * Метод валидации пользователей
     *
     * @param user
     */
    private void validation(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("There is no name to display, login will be used");
        }
    }
}
