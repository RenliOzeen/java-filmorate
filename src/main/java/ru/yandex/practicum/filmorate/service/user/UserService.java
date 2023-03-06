package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addFriend(Long userId, Long friendId) {
        userStorage.addFriend(userId, friendId);
        return userStorage.getUser(userId);
    }

    public User deleteFriend(Long userId, Long friendId) {
        userStorage.deleteFriend(userId, friendId);
        return userStorage.getUser(userId);
    }

    public List<User> getUserFriends(Long userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getMutualFriends(Long userId, Long otherId) {
        return userStorage.getMutualFriends(userId, otherId);
    }
}
