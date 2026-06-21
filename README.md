# Тестирование интернет-банка

[![Java CI with Gradle](https://github.com/ваш-username/ibank-test/actions/workflows/gradle.yml/badge.svg)](https://github.com/ваш-username/ibank-test/actions/workflows/gradle.yml)

## Описание
Автоматические тесты для тестирования входа в интернет-банк с использованием API для создания пользователей.

## Технологии
- Java 11
- Gradle
- JUnit 5
- Selenide
- REST Assured
- JavaFaker
- Lombok

## Запуск тестов
1. Запустите приложение в тестовом режиме:
   ```bash
   java -jar artifacts/app-ibank.jar -P:profile=test