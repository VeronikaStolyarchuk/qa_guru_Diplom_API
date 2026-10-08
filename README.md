<div align="center">
  <h1>Проект по автоматизации тестирования API</h1>
</div>
  <h2>
    <p align="center">
<a href="https://practice.expandtesting.com/" target="_blank" style="color: #1BA8A8; font-weight: bold;">practice.expandtesting.com</a>
      </p>
    </h2>

## Про проект
В рамках проекта реализуется тестирование API [practice.expandtesting.com](https://practice.expandtesting.com/) для проверки соответствия Swagger‑документации. Реализованы типовые сценарии: регистрация, получение данных, валидация структуры ответов. Тесты на RestAssured + JUnit 5, сборка через Gradle. Структурированные шаги в Allure для понятных отчётов. Генерация тестовых данных через JavaFaker. Покрытие позитивных и негативных кейсов, контроль статусов и полей в JSON‑ответах.

## Технологический стек

<p align="center">
<a href="https://www.java.com/"><img src="media/logo/java.svg" width="50" height="50"  alt="Java"/></a>
<a href="https://www.jetbrains.com/idea/"><img src="media/logo/intellij-idea.svg" width="50" height="50"  alt="IDEA"/></a>
<a href="https://gradle.org/"><img src="media/logo/gradle.svg" width="50" height="50"  alt="Gradle"/></a>
<a href="https://junit.org/junit5/"><img src="media/logo/junit5.svg" width="50" height="50"  alt="JUnit 5"/></a>
<a href="https://github.com/"><img src="media/logo/github.svg" width="50" height="50"  alt="Github"/></a>
<a href="https://selenide.org/"><img src="media/logo/selenide.svg" width="50" height="50"  alt="Selenide"/></a>
<a href="https://aerokube.com/selenoid/"><img src="media/logo/selenoid.svg" width="50" height="50"  alt="Selenoid"/></a>
<a href="https://github.com/allure-framework/allure2"><img src="media/logo/allure.svg" width="50" height="50"  alt="Allure"/></a>
<a href="https://qameta.io/"><img src="media/logo/allureTO.svg" width="50" height="50"  alt="Allure TestOps"/></a>
<a href="https://www.jenkins.io/"><img src="media/logo/jenkins.svg" width="50" height="50"  alt="Jenkins"/></a>
<a href="https://www.atlassian.com/ru/software/jira"><img src="media/logo/jira.svg" width="50" height="50"  alt="Atlassian Jira"/></a>
</p>

## Реализованные проверки

| № | Тип запроса | Эндпоинт | Сценарий | Статус‑коды | Что проверяется |
|---|-------------|----------|----------|-------------|-----------------|
| 1 | POST | `/users/register` | Успешная регистрация пользователя | 201 | Наличие `id`, сообщение «User account created successfully» |
| 2 | POST | `/users/register` | Регистрация с пустыми полями | 400 | Статус `isSuccess = false`, сообщение о длине имени |
| 3 | POST | `/users/register` | Повторная регистрация существующего пользователя | 409 | Статус `isSuccess = false`, сообщение «An account already exists with the same email address» |
| 4 | POST | `/users/login` | Успешная авторизация | 200 | Наличие `id` и токена, сообщение «Login successful» |
| 5 | POST | `/users/login` | Авторизация с пустыми полями | 400 | Статус `isSuccess = false`, сообщение о валидном email |
| 6 | POST | `/users/login` | Авторизация с некорректным паролем | 401 | Статус `isSuccess = false`, сообщение «Incorrect email address or password» |
| 7 | GET | `/users/profile` | Получение профиля по токену | 200 | Совпадение `id` профиля с `id` из логина, наличие email |
| 8 | PATCH | `/users/profile` | Обновление профиля (name, phone, company) | 200 | Сообщение «Profile updated successful», совпадение обновлённых полей |
| 9 | PATCH | `/users/profile` | Обновление с пустыми обязательными полями | 400 | Статус `isSuccess = false`, сообщение о длине имени |
| 10 | PATCH | `/users/profile` | Обновление без токена авторизации | 401 | Статус `isSuccess = false`, сообщение «No authentication token specified in x-auth-token header» |
| 11 | POST | `/notes` | Создание заметки с привязкой к пользователю | 200 | Сообщение «Note successfully created», совпадение `user_id` с текущим пользователем, корректность title |
| 12 | GET | `/notes/{id}` | Получение заметки по несуществующему ID | 400 | Статус `isSuccess = false`, сообщение «Note ID must be a valid ID» |
| 13 | DELETE | `/notes/{id}` | Удаление заметки (с предварительным созданием) | 200 | Статус `isSuccess = true`, сообщение «Note successfully deleted» |

## Особенности реализации

- **Структурированные шаги Allure.** Каждый значимый этап теста оформлен через `Allure.step` — в отчёте видно, какой именно шаг прошёл или упал.
- **Переиспользуемые спецификации.** Для валидации статусов и структуры ответов используются спецификации RestAssured (`responseRegistrationSpec201`, `responseLoginSpec200` и т. д.).
- **Динамические тестовые данные.** Данные для тестов (имя, email, пароль, заметки) генерируются через `JavaFaker` и вынесены в отдельный класс `TestData`, чтобы избежать хардкода и дублирования.
- **Сквозные сценарии.** Некоторые тесты включают последовательность действий (логин → создание заметки → удаление), что позволяет проверять бизнес‑логику целиком.
