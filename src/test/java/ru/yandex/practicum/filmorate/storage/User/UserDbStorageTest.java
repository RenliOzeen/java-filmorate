package ru.yandex.practicum.filmorate.storage.User;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private final JdbcTemplate jdbcTemplate;
    User user;
    User friend;
    User mutualFriend;

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("DELETE FROM users");
        jdbcTemplate.update("DELETE FROM friends");
        user = User.builder()
                .email("mail@mail.mail")
                .login("login")
                .birthday(LocalDate.of(1999, 8, 17))
                .build();
        user.setFriends(new HashSet<>());

        friend = User.builder()
                .email("gmail@gmail.gmail")
                .login("nelogin")
                .birthday(LocalDate.of(2001, 6, 19))
                .build();
        friend.setFriends(new HashSet<>());

        mutualFriend = User.builder()
                .email("mutual@mutual.mutual")
                .login("mutual")
                .birthday(LocalDate.of(2001, 1, 11))
                .build();
        mutualFriend.setFriends(new HashSet<>());
    }

    /**
     * Тест на создание, обновление и получение пользователя
     */
    @Test
    void shouldCreateAndUpdateAndGetUser() {
        userDbStorage.create(user);
        assertEquals(user, userDbStorage.getUser(user.getId()));
        assertEquals(user.getLogin(), userDbStorage.getUser(user.getId()).getName());

        user.setEmail("lol@lol.lol");
        userDbStorage.update(user);
        assertEquals(user, userDbStorage.getUser(user.getId()));

        assertTrue(userDbStorage.findAll().size() == 1);
        assertEquals(user, userDbStorage.getUser(user.getId()));
    }

    /**
     * Тест на добавление и удаление друзей
     */
    @Test
    void shouldAddAndDeleteFriends() {
        userDbStorage.create(user);
        userDbStorage.create(friend);
        userDbStorage.addFriend(user.getId(), friend.getId());
        assertTrue(userDbStorage.getFriends(user.getId()).size() == 1);
        assertTrue(userDbStorage.getFriends(friend.getId()).size() == 0);

        userDbStorage.deleteFriend(user.getId(), friend.getId());
        assertTrue(userDbStorage.getFriends(user.getId()).size() == 0);
        assertTrue(userDbStorage.getFriends(friend.getId()).size() == 0);
    }

    /**
     * Тест на получение списка общих друзей двух пользователей
     */
    @Test
    void shouldGetMutualFriends() {
        userDbStorage.create(user);
        userDbStorage.create(friend);
        userDbStorage.create(mutualFriend);
        userDbStorage.addFriend(user.getId(), mutualFriend.getId());
        userDbStorage.addFriend(friend.getId(), mutualFriend.getId());
        assertTrue(userDbStorage.getMutualFriends(user.getId(), friend.getId()).get(0).getId() == mutualFriend.getId());
    }

}