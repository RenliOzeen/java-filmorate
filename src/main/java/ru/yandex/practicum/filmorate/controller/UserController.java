package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.*;

/**
 * Класс-контроллер для пользователей
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    /**
     * Обработчик GET-запроса на получение списка всех пользователей
     *
     * @return Список List с объектами <User>
     */
    @GetMapping
    public List<User> findAll() {
        return userStorage.findAll();
    }

    /**
     * Обработчик POST-запроса на добавление нового пользователя
     *
     * @param user
     * @return объект User, добавленный в список
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userStorage.create(user);
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
        return userStorage.update(user);
    }

    /**
     * Обработчик GET запроса на получение пользователя по его id
     *
     * @param id
     * @return экземпляр класса User
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userStorage.getUser(Long.parseLong(id));
    }

    /**
     * Обработчик PUT запроса на добавление пользователя в друзья
     *
     * @param id       пользователя, кто добавляет
     * @param friendId пользователя, кого добавляем
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.addFriend(Long.parseLong(id), Long.parseLong(friendId));
    }

    /**
     * Обработчик DELETE запроса на удаление из друзей
     *
     * @param id       пользователя, кто удаляет
     * @param friendId пользователя, кого удаляем
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable String id, @PathVariable String friendId) {
        userService.deleteFriend(Long.parseLong(id), Long.parseLong(friendId));
    }

    /**
     * Обработчик GET запроса на получение списка друзей пользователя по его id
     *
     * @param id
     * @return List с объектами типа User
     */
    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable String id) {
        return userService.getUserFriends(Long.parseLong(id));
    }

    /**
     * Обработчик GET запроса на получение списка общих друзей двух пользователей по их id
     *
     * @param id
     * @param otherId
     * @return List с объектами типа User
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getMutualFriends(Long.parseLong(id), Long.parseLong(otherId));
    }
}
