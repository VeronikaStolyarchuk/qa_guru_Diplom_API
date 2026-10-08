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

## Реализованные автотесты

- Успешная регистрация пользователя и валидация ответа.
- Регистрация с некорректными данными (проверка обработки ошибок).
- Получение данных профиля по ID.
- Обновление профиля (PATCH) и проверка изменений.
- Генерация тестовых данных через JavaFaker (имя, email, пароль).

## Особенности реализации

- **Структурированные шаги Allure.** Каждый значимый этап теста оформлен через `Allure.step` — в отчёте видно, какой именно шаг прошёл или упал.
- **Переиспользуемые спецификации.** Для валидации статусов и структуры ответов используются спецификации RestAssured (`responseRegistrationSpec201`, `responseLoginSpec200` и т. д.).
- **Динамические тестовые данные.** Данные для тестов (имя, email, пароль, заметки) генерируются через `JavaFaker` и вынесены в отдельный класс `TestData`, чтобы избежать хардкода и дублирования.
- **Сквозные сценарии.** Некоторые тесты включают последовательность действий (логин → создание заметки → удаление), что позволяет проверять бизнес‑логику целиком.
## Запуск автотестов:

### Локальный запуск:
```
gradle clean test
```
### Удалённый запуск через Jenkins:
```
clean test
-Denvironment=$ENVIRONMENT
```

<a id="сборка-в-jenkins"></a>

## <img width="4%" style="vertical-align:middle" title="Jenkins" src="media/logo/jenkins.svg"> [Сборка в Jenkins](https://jenkins.qa.guru/job/41_VeronikaStolyarchuk_guru-qa_DiplomAPI)

Для запуска сборки необходимо перейти в раздел <code>Собрать с параметрами</code> и нажать кнопку <code>Собрать</code>.
<p align="center">
<img title="Jenkins Build" src="media/screens/jenkins.png">
</p>
После завершения сборки в блоке «История сборок» рядом с номером сборки отображаются значки Allure Report и Allure TestOps. При нажатии на значок Allure Report открывается отчёт по тестам, а при клике на Allure TestOps — страница с тестовой документацией.

____
<a id="allureReport"></a>
## <img width="30" style="vertical-align:middle" title="Allure Report" src="media/logo/allure.svg"> </a> Пример <a target="_blank" href="https://jenkins.qa.guru/job/41_VeronikaStolyarchuk_guru-qa_DiplomAPI/8/allure/"> Allure-отчета </a>
Allure Report используется для отображения результатов выполнения тестов, шагов тестирования и вложений.
<p align="center">
<img title="Allure Overview" src="media/screens/allure-report.png">
<img title="Allure Overview" src="media/screens/allure.png">
</p>

## <img width="30" style="vertical-align:middle" title="Allure TestOps" src="media/logo/allureTO.svg"> Система управления тест-кейсами [TestOps](https://allure.qa.guru/launch/56573)

Система TestOps предназначена для управления тест‑кейсами. Функционал включает отображение тест‑кейсов и информацию о запусках с актуальными статусами.

<p align="center">
<img title="Testops" src="media/screens/AllureTestOps.png"><br>
<img title="Testops-suites" src="media/screens/Allure-Results.png"><br>
<img title="Testops- dashboard" src="media/screens/allure-dashboard.png"><br>
</p>
