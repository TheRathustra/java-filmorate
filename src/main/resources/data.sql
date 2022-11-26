
INSERT INTO MPA (name) VALUES ('G');
INSERT INTO MPA (name) VALUES ('PG');
INSERT INTO MPA (name) VALUES ('PG-13');
INSERT INTO MPA (name) VALUES ('R');
INSERT INTO MPA (name) VALUES ('NC-17');

INSERT INTO genre (name) VALUES ('Комедия');
INSERT INTO genre (name) VALUES ('Драма');
INSERT INTO genre (name) VALUES ('Мультфильм');
INSERT INTO genre (name) VALUES ('Триллер');
INSERT INTO genre (name) VALUES ('Документальный');
INSERT INTO genre (name) VALUES ('Боевик');

INSERT INTO film (name, description, releaseDate, duration, MPA) VALUES ('The Gentlemen', 'description', '2019-01-01', 2, 4);
INSERT INTO film (name, description, releaseDate, duration, MPA) VALUES ('Lock, Stock and Two Smoking Barrels', 'description', '1998-01-01', 2, 4);
INSERT INTO film (name, description, releaseDate, duration, MPA) VALUES ('Snatch', 'description', '2000-01-01', 1, 5);
INSERT INTO film (name, description, releaseDate, duration, MPA) VALUES ('Tais-toi!', 'description', '2003-01-01', 2, 2);
INSERT INTO film (name, description, releaseDate, duration, MPA) VALUES ('Le grand blond avec une chaussure noire', 'description', '1972-01-01', 2, 1);

INSERT INTO users (email, login, name, birthday) VALUES ('user1@yandex.ru', 'user1_login', 'user1', '1995-01-01');
INSERT INTO users (email, login, name, birthday) VALUES ('user2@yandex.ru', 'user2_login', 'user2', '2000-01-01');
INSERT INTO users (email, login, name, birthday) VALUES ('user3@yandex.ru', 'user3_login', 'user3', '2005-01-01');
INSERT INTO users (email, login, name, birthday) VALUES ('user4@yandex.ru', 'user4_login', 'user4', '2010-01-01');
INSERT INTO users (email, login, name, birthday) VALUES ('user5@yandex.ru', 'user5_login', 'user5', '2015-01-01');

/*
Значения для локальных тестов

INSERT INTO film_genre (film_id, genre_id) VALUES (1, 1);
INSERT INTO film_genre (film_id, genre_id) VALUES (1, 4);
INSERT INTO film_genre (film_id, genre_id) VALUES (2, 1);
INSERT INTO film_genre (film_id, genre_id) VALUES (3, 2);
INSERT INTO film_genre (film_id, genre_id) VALUES (4, 1);
INSERT INTO film_genre (film_id, genre_id) VALUES (4, 4);
INSERT INTO film_genre (film_id, genre_id) VALUES (5, 1);
INSERT INTO film_genre (film_id, genre_id) VALUES (5, 4);

INSERT INTO likes (film_id, user_id) VALUES (1, 1);
INSERT INTO likes (film_id, user_id) VALUES (1, 2);
INSERT INTO likes (film_id, user_id) VALUES (1, 3);
INSERT INTO likes (film_id, user_id) VALUES (2, 1);
INSERT INTO likes (film_id, user_id) VALUES (2, 5);
INSERT INTO likes (film_id, user_id) VALUES (3, 4);
INSERT INTO likes (film_id, user_id) VALUES (4, 1);

INSERT INTO friends (user_id, friend_id) VALUES (1, 2);
INSERT INTO friends (user_id, friend_id) VALUES (2, 1);
INSERT INTO friends (user_id, friend_id) VALUES (1, 3);
INSERT INTO friends (user_id, friend_id) VALUES (3, 1);
INSERT INTO friends (user_id, friend_id) VALUES (2, 3);
INSERT INTO friends (user_id, friend_id) VALUES (3, 2);
INSERT INTO friends (user_id, friend_id) VALUES (1, 5);
INSERT INTO friends (user_id, friend_id) VALUES (5, 2);
INSERT INTO friends (user_id, friend_id) VALUES (2, 4);
INSERT INTO friends (user_id, friend_id) VALUES (3, 4);

 */