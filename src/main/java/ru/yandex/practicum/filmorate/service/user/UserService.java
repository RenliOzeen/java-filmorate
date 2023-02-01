package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    /**
     * Метод-сервис для добавления пользователя в друзья
     *
     * @param userId
     * @param friendId
     */
    public void addFriend(Long userId, Long friendId) {
        userStorage.getUser(userId).getFriends().add(friendId);
        userStorage.getUser(friendId).getFriends().add(userId);
    }

    /**
     * Метод-сервис для удаления пользователя из друзей
     *
     * @param userId
     * @param friendId
     */
    public void deleteFriend(Long userId, Long friendId) {
        userStorage.getUser(userId).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(userId);
    }

    /**
     * Метод-сервис для получения списка общих друзей двух пользователей по их id
     *
     * @param userId
     * @param friendId
     * @return
     */
    public List<User> getMutualFriends(Long userId, Long friendId) {
        List<User> mutualFriends = new ArrayList<>();
        for (Long id : userStorage.getUser(userId).getFriends()) {
            if (userStorage.getUser(friendId).getFriends().contains(id)) {
                mutualFriends.add(userStorage.getUser(id));
            }
        }
        return mutualFriends;
    }

    /**
     * Метод-сервис для получения списка друзей конкретного пользователя
     *
     * @param userId
     * @return
     */
    public List<User> getUserFriends(Long userId) {
        List<User> userFriends = new ArrayList<>();
        return userStorage.findAll().stream()
                .filter(user -> user.getFriends().contains(userId))
                .collect(Collectors.toList());

    }
}
