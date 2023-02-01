package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface UserStorage {

    /**
     * Метод для получения списка всех пользователей
     *
     * @return
     */
    public List<User> findAll();

    /**
     * Метод для создания нового пользователя
     *
     * @param user
     * @return
     */
    public User create(User user);

    /**
     * Метод для обновления данных пользователя
     *
     * @param user
     * @return
     */
    public User update(User user);

    /**
     * Метод для получения экземпляра пользователя по его id
     *
     * @param id
     * @return
     */
    public User getUser(Long id);
}
