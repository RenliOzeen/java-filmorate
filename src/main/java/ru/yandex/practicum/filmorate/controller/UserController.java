package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

/**
 * Класс-контроллер для пользователей
 */
@RestController
@Slf4j
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();

    /**
     * Обработчик GET-запроса на получение списка всех пользователей
     *
     * @return Список List с объектами <User>
     */
    @GetMapping("/users")
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Обработчик POST-запроса на добавление нового пользователя
     *
     * @param user
     * @return объект User, добавленный в список
     * @throws ValidationException
     */
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        if (userValidation(user)) {
            users.put(user.getId(), user);
            log.info("New user added: {}", user);
        } else {
            throw new ValidationException("Validation Error");
        }
        return user;
    }

    /**
     * Обработчик PUT-запроса на обновление данных пользователя
     *
     * @param user
     * @return обновленные данные пользователя
     * @throws ValidationException
     */
    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        if (userValidation(user)) {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                log.info("User data updated: {}", user);
            } else {
                throw new ValidationException("Validation Error");
            }
        }
        return user;
    }

    /**
     * Метод валидации пользователей
     *
     * @param user
     * @return true-при прохождении валидации, false-при непрохождении
     */
    private boolean userValidation(User user) {
        if (user.getId() == 0) {
            user.setId(users.size() + 1);
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.info("There is no name to display, login will be used");
        }
        if (user.getEmail().isEmpty() || !(user.getEmail().contains("@"))) {
            log.warn("Email is empty or does not contain the '@' character");
            return false;
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.warn("Login is empty or contains spaces");
            return false;
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("The user's date of birth is later than the current date");
            return false;
        }
        return true;
    }
}
