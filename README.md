## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Список выполненных задач:

* Розібрався зі структурою проєкту (onboarding).
* Видалив соціальні мережі: vk, yandex.
* Виніс чутливу інформацію (БД, OAuth, пошта) в окремий properties-файл (зчитується зі змінних оточення).
* Переробив тести для використання in-memory БД (H2) замість PostgreSQL, налаштував біни через профілі.
* Написав тести для всіх публічних методів контролера `ProfileRestController` (позитивні та негативні сценарії).
* Зробив рефакторинг методу `FileUtil#upload` під сучасний підхід роботи з файловою системою.
* Додав функціонал додавання тегів до задачі (REST API + рівень сервісу).
* Реалізував підрахунок часу перебування задачі в роботі та на тестуванні (додано відповідні записи в `changelog.sql`).
* Написав `Dockerfile` для основного сервера (multistage збірка).
* Написав `docker-compose.yml` для підняття зв'язки: app, db (PostgreSQL) та nginx.
* Додав локалізацію (i18n) для `index.html` та шаблонів листів (uk, ru, en-default).
* **Перехід на stateless-архітектуру:** Механізм розпізнавання «свій-чужий» повністю переведено з традиційних сесій (`JSESSIONID`) на JWT. У `SecurityConfig` налаштовано `SessionCreationPolicy.STATELESS`.
* **Фільтрація та валідація:** Реалізовано та інтегровано `JwtAuthenticationFilter`, який перевіряє валідність токена при кожному запиті до захищених ендпоінтів.
* **Адаптація фронтенду:** Перероблено логіку аутентифікації на стороні клієнта. Після успішного логіну JWT-токен зберігається на клієнті та використовується для подальшої авторизації запитів.
* **Безпечний Logout:** Налаштовано коректне завершення сеансу через бекенд із примусовим очищенням кук (`jwtToken`, `JSESSIONID`), що унеможливлює використання старих даних.