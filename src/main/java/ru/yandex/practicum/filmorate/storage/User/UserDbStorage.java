package ru.yandex.practicum.filmorate.storage.User;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM users");
        while (userRows.next()) {
            User user = User.builder()
                    .id(userRows.getLong("user_id"))
                    .email(userRows.getString("email"))
                    .login(userRows.getString("login"))
                    .name(userRows.getString("name"))
                    .birthday(userRows.getDate("birthday").toLocalDate())
                    .build();

            users.add(user);
        }
        return users;
    }

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");

        user.setId(simpleJdbcInsert.executeAndReturnKey(toMap(user)).longValue());
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        String sqlQuery = "UPDATE users SET " +
                "email=?, login=?, name=?, birthday=? WHERE user_id=?";
        int rowsCount = jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (rowsCount > 0) {
            return user;
        }
        throw new NotFoundException("This user was not found in the database");
    }

    @Override
    public User getUser(Long id) {
        String sqlQuery = "SELECT * FROM users WHERE user_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (RuntimeException e) {
            throw new NotFoundException("This user was not found in the database");
        }
    }

    @Override
    public List<User> getFriends(Long userId) {
        String sqlQuery = "SELECT * FROM users WHERE user_id IN(SELECT friend_id FROM friends WHERE user_id=?)";
        List<User> friendList = new ArrayList<>();
        friendList.addAll(jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId));
        return friendList;
    }

    @Override
    public List<User> getMutualFriends(Long userId, Long otherId) {
        List<User> mutualFriendsList=new ArrayList<>();
        String sqlQuery = "SELECT * FROM users WHERE user_id IN(" +
                "SELECT friend_id FROM friends WHERE user_id = ?) " +
                "AND user_id IN(SELECT friend_id FROM friends WHERE user_id = ?)";
        mutualFriendsList.addAll(jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId, otherId));
        return mutualFriendsList;
    }

    @Override
    public User addFriend(Long userId, Long friendId) {
        User user = getUser(userId);
        try {
            getUser(friendId);
        } catch(RuntimeException e){
            throw new NotFoundException("This user was not found in the database");
        }
        String sqlQuery = "INSERT INTO friends (user_id, friend_id) VALUES(?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
        return user;
    }

    @Override
    public User deleteFriend(Long userId, Long friendId) {
        User user = getUser(userId);
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
        return user;
    }

    /**
     * Вспомогательные методы преобразования пользователей
     *
     */
    private Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = User.builder()
                .id(resultSet.getLong("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
        user.setFriends(getFriends(user.getId()).stream().map(User::getId).collect(Collectors.toSet()));
        return user;
    }
}
