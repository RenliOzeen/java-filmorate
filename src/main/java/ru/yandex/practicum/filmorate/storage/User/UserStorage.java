package ru.yandex.practicum.filmorate.storage.User;

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

    /**
     * Метод для добавление пользователя в друзья
     * @param userId - тот, кто добавляет
     * @param friendId - кого добавляют
     * @return
     */
    public User addFriend(Long userId, Long friendId);

    /**
     * Метод для удаления пользователя из друзей
     * @param userId - у кого удаляем
     * @param friendId - кого удаляем
     * @return
     */
    public User deleteFriend(Long userId, Long friendId);

    /**
     * Метод для получения списка друзей конкретного пользователя
     * @param userId id пользователя
     * @return
     */
    public List<User> getFriends(Long userId);

    /**
     * Метод для получения списка общих друзей двух пользователей по их id
     *
     * @param userId
     * @param otherId
     * @return
     */
    public List<User> getMutualFriends(Long userId, Long otherId);
}
