package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        user.setId((long) users.size() + 1);
        user.setFriends(new HashSet<>());
        validation(user);
        users.put(user.getId(), user);
        log.info("New user added: {}", user);

        return user;
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            user.setFriends(new HashSet<>());
            validation(user);
            users.put(user.getId(), user);
            log.info("User data updated: {}", user);
        } else {
            throw new NotFoundException("No such user was found");
        }
        return user;
    }

    @Override
    public User getUser(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("The user with this id was not found");
        }
        return users.get(id);
    }

    /**
     * Метод валидации пользователя
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
