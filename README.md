# java-filmorate
Template repository for Filmorate project.
# Схема связей таблиц
![схема](схема.png)

# Пояснение к таблицам
Таблица film содержит все данные об конкретном фильме. Лайки для фильма хранятся в отдельной таблицу likes. Поля в таблице likes указывают на конкретный фильм и # конкретного пользователя, который поставил лайк. Таблица genre хранит все возможные жанры фильмов. Отдельная таблица film_genre хранит данные о жанрах конкретных # фильмов.
Таблица user хранит данные о пользователях. Отдельная таблица friends устанавливает дружбу между пользователями. Отдельное поле status упрощает запрос для # определения взаимности дружбы. 

# Примеры получения данных

### Лайки пользователей по фильмам
``` sql
SELECT u.name, f.name FROM users as u LEFT JOIN likes as l on u.id = l.user_id
LEFT JOIN film as f on f.id = l.film_id
 ```  

### Топ 10 самых популярных фильмов
``` sql
SELECT f.name from film as f where f.id in
(
SELECT l.film_id as like_count from likes as l
group by l.film_id
ORDER BY count(l.user_id) DESC 
LIMIT 10
)
 ```     

### Получить информацию о друзьях
``` sql
SELECT u.name, users.name, f.status from users as u left join friends as f on u.id = f.user_id
left join users on users.id = f.friend_id
 ```     