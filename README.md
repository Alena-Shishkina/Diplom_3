## Автоматизация тестирования Stellar Burgers

Проект содержит набор UI- и API-автотестов для веб-приложения Stellar Burgers. Тесты проверяют ключевой пользовательский функционал:
— регистрацию и авторизацию,
— работу конструктора бургера,
— навигацию между разделами,
— оформление заказа,
— управление учётной записью.

### Технологии

- Java 11 
- JUnit 4 
- Selenium WebDriver 4 — UI-тесты 
- RestAssured — API-тесты 
- Allure Framework — отчётность 
- Maven — сборка и управление зависимостями 
- WebDriverManager — управление драйверами

### Запуск тестов
Запуск всех тестов:
mvn clean test

Chrome:
mvn clean test -Dbrowser=chrome

Yandex:
mvn clean test -Dbrowser=yandex \
-Dyandex.driver.path=src/test/resources/yandexdriver.exe \
-Dyandex.browser.path="C:/Users/.../browser.exe"

Формирование Allure-отчёта:
mvn allure:serve

### Особенности

Страница конструктора покрыта проверками активного состояния вкладок.
Используется паттерн Page Object.
Реализована конфигурация запуска под разные браузеры (Chrome/Yandex).
Тесты изолированы и независимы друг от друга.

