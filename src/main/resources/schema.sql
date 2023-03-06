DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS mpa_type
(
    rating_mpa_id LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR(40)

);

CREATE TABLE IF NOT EXISTS films
(
    film_id       LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   VARCHAR(200) NOT NULL,
    release_date  TIMESTAMP    NOT NULL,
    duration      LONG         NOT NULL,
    rating_mpa_id LONG REFERENCES mpa_type (rating_mpa_id)

);

CREATE TABLE IF NOT EXISTS users
(
    user_id  LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    VARCHAR(50) NOT NULL,
    login    VARCHAR(50) NOT NULL,
    name     VARCHAR(50),
    birthday DATE        NOT NULL

);

CREATE TABLE IF NOT EXISTS likes
(
    like_id LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id LONG NOT NULL REFERENCES films (film_id),
    user_id LONG NOT NULL REFERENCES users (user_id)

);

CREATE TABLE IF NOT EXISTS genre_type
(
    genre_id LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     VARCHAR(40) NOT NULL

);

CREATE TABLE IF NOT EXISTS genre
(
    id       LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id  LONG NOT NULL REFERENCES films (film_id),
    genre_id LONG NOT NULL REFERENCES genre_type (genre_id)

);

CREATE TABLE IF NOT EXISTS friends
(
    relationship_id LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id         LONG NOT NULL REFERENCES users (user_id),
    friend_id       LONG NOT NULL REFERENCES users (user_id)

);