# java-filmorate
Template repository for Filmorate project.
# Схема связей таблиц
![схема](схема.png)

# Пояснение к таблицам
    Таблица film содержит все данные об конкретном фильме. Лайки для фильма хранятся в отдельной таблицу likes. Поля в таблице likes указывают на конкретный фильм и # конкретного пользователя, который поставил лайк. Таблица genre хранит все возможные жанры фильмов. Отдельная таблица film_genre хранит данные о жанрах конкретных # фильмов.
    Таблица user хранит данные о пользователях. Отдельная таблица friends устанавливает дружбу между пользователями. Отдельное поле status упрощает запрос для # определения взаимности дружбы. 