# java-filmorate
Template repository for Filmorate project.


![Схема](https://user-images.githubusercontent.com/103331774/218284663-7e5a7ae4-c1e5-42c3-8e9c-7bc860173fb0.png)

Запрос на получение списка всех пользователей:
SELECT *
FROM user

Запрос на получение общих друзей:
SELECT login
FROM 
(SELECT login
FROM users
WHERE user_id IN 
(SELECT friend_id
FROM friends
WHERE user_id=(long value) AND status=true))
WHERE login IN 
(SELECT login 
FROM user 
WHERE user_id IN
(SELECT friend_id 
FROM friends 
WHERE user_id = (long value) AND status = true))

Запрос на получение списка всех фильмов:
SELECT *
FROM film

Запрос на получение топа популярных фильмов:
SELECT * 
FROM film 
WHERE film_id IN
(SELECT film_id,
        count(user_id) AS likes_count
FROM likes 
GROUP BY film_id 
ORDER BY likes_count DESC 
LIMIT (something value) )
